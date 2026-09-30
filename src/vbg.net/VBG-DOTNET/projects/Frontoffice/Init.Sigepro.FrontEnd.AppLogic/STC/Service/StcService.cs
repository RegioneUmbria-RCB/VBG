// -----------------------------------------------------------------------
// <copyright file="StcService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using Init.Utils;
using log4net;
using System;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.STC.Service
{
    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class StcServiceImpl : IStcService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(StcServiceImpl));
        private readonly IPathMapper _pathMapper;
        private readonly IStcServiceCreator _stcServiceCreator;
        private readonly IAliasResolver _aliasResolver;

        public StcServiceImpl(IPathMapper pathMapper, IStcServiceCreator stcServiceCreator, IAliasResolver aliasResolver)
        {
            this._pathMapper = pathMapper;
            this._stcServiceCreator = stcServiceCreator;
            this._aliasResolver = aliasResolver;
        }

        #region IStcService Members

        public NotificaAttivitaResponse NotificaAttivita(NotificaAttivitaRequest request, Action<SportelloType>? modificaSportelloDestinatario = null)
        {
            using (var stc = this._stcServiceCreator.CreateClient())
            {
                try
                {
                    request.token = stc.Token;
                    request.sportelloMittente = this._stcServiceCreator.ConfigurazioneStc.NodoMittente.AsSportelloType();
                    request.sportelloDestinatario = this._stcServiceCreator.ConfigurazioneStc.NodoDestinatario.AsSportelloType();

                    modificaSportelloDestinatario?.Invoke(request.sportelloDestinatario);

                    this._log.DebugFormat("Inizio invocazione di STC::NotificaAttivita con i parametri: {0}", StreamUtils.SerializeClass(request));

                    this.SalvaMovimentoInUscita(request);

                    var response = stc.Service.NotificaAttivita(request);

                    foreach (var item in response.Items)
                    {
                        if (item is ErroreType)
                        {
                            var datiErrore = item as ErroreType;
                            throw new Exception($"{datiErrore?.descrizione ?? "Errore sconosciuto"} [{datiErrore?.numeroErrore}]");
                        }
                    }

                    this._log.DebugFormat("STC::NotificaAttivita invocato con successo, risultato: {0}", StreamUtils.SerializeClass(response));

                    return response;
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore durante l'invocazione di STC::NotificaAttivita: {0}", ex.ToString());

                    stc.Service.Abort();

                    throw;
                }
            }
        }

        private void ImpostaSportelloMittenteEDestinatario(InserimentoPraticaRequest request, string pecSportello, SportelloType? sportelloDestinatario, string token)
        {
            var configurazioneStc = this._stcServiceCreator.ConfigurazioneStc;

            request.sportelloDestinatario = new SportelloType
            {
                idEnte = sportelloDestinatario?.idEnte ?? configurazioneStc.NodoDestinatario.Ente,
                idSportello = sportelloDestinatario?.idSportello ?? configurazioneStc.NodoDestinatario.Sportello,
                idNodo = sportelloDestinatario?.idNodo ?? configurazioneStc.NodoDestinatario.Id,
                pecSportello = pecSportello
            };

            request.sportelloMittente = new SportelloType
            {
                idEnte = configurazioneStc.NodoMittente.Ente,
                idSportello = configurazioneStc.NodoMittente.Sportello,
                idNodo = configurazioneStc.NodoMittente.Id
            };

            request.token = token;
        }

        public InserimentoPraticaResponse InserimentoPratica(InserimentoPraticaRequest request, string pecSportello, SportelloType? sportelloDestinatario = null)
        {
            using (var service = this._stcServiceCreator.CreateClient())
            {
                using (var timeLogger = new STCElapsedTimeLogger(nameof(InserimentoPratica), this._aliasResolver))
                {
                    try
                    {
                        this.ImpostaSportelloMittenteEDestinatario(request, pecSportello, sportelloDestinatario, service.Token);

                        this.SalvaXmlPratica(request);

                        if (this._log.IsDebugEnabled)
                            this._log.DebugFormat("Inizio invocazione di STC::InserimentoPratica con i parametri: {0}", StreamUtils.SerializeClass(request));

                        var response = service.Service.InserimentoPratica(request);

                        this.LogResultAndThrowExceptions(response);

                        return response;
                    }
                    catch (Exception ex)
                    {
                        this._log.ErrorFormat("Errore durante l'invocazione di STC::InserimentoPratica: {0}", ex.ToString());

                        service.Service.Abort();

                        throw;
                    }
                    finally
                    {
                        timeLogger.Stop();
                    }
                }
            }
        }

        public async Task<InserimentoPraticaResponse> InserimentoPraticaAsync(InserimentoPraticaRequest request, string pecSportello, SportelloType? sportelloDestinatario = null)
        {
            using (var service = this._stcServiceCreator.CreateClient())
            {
                using (var timeLogger = new STCElapsedTimeLogger(nameof(InserimentoPratica), this._aliasResolver))
                {
                    try
                    {
                        this.ImpostaSportelloMittenteEDestinatario(request, pecSportello, sportelloDestinatario, service.Token);

                        this.SalvaXmlPratica(request);

                        if (this._log.IsDebugEnabled)
                            this._log.DebugFormat("Inizio invocazione di STC::InserimentoPratica con i parametri: {0}", StreamUtils.SerializeClass(request));

                        var response = await service.Service.InserimentoPraticaAsync(request);

                        this.LogResultAndThrowExceptions(response.InserimentoPraticaResponse);

                        return response.InserimentoPraticaResponse;
                    }
                    catch (Exception ex)
                    {
                        this._log.ErrorFormat("Errore durante l'invocazione di STC::InserimentoPratica: {0}", ex.ToString());

                        service.Service.Abort();

                        throw;
                    }
                    finally
                    {
                        timeLogger.Stop();
                    }
                }
            }
        }

        private void LogResultAndThrowExceptions(InserimentoPraticaResponse response)
        {
            if (response.Items == null || response.Items.Length == 0)
            {
                var msg = "L'invio della domanda STC ha restituito una struttura InserimentoPraticaResponse vuota";
                this._log.Error(msg);

                throw new Exception(msg);
            }

            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("STC::InserimentoPratica invocato con successo. Risposta: {0}", StreamUtils.SerializeClass(response));
        }

        private void SalvaXmlPratica(InserimentoPraticaRequest request)
        {
            try
            {
                if (this._pathMapper.IsPathMappingSupported)
                {
                    var pathSalvataggioPratiche = this.GetRootSalvataggio();
                    var pathFileDomanda = Path.Combine(pathSalvataggioPratiche, request.dettaglioPratica.idPratica + ".xml");

                    this._log.DebugFormat("Xml della domanda in uscita salvato nel percorso {0}", pathFileDomanda);

                    using (var fs = File.Open(pathFileDomanda, FileMode.Create))
                    {
                        var xs = new XmlSerializer(request.GetType());
                        xs.Serialize(fs, request);
                    }
                }
            }
            catch (Exception ex)
            {
                this._log.WarnFormat("Non è stato possibile salvare l'xml della domanda in uscita per la seguente ragione: {0}", ex.ToString());
            }
        }

        public bool PraticaEsisteNelBackend(string idPratica, SportelloType? sportelloDestinatario)
        {
            try
            {
                var res = this.RichiestaPratica(idPratica, sportelloDestinatario);

                return res?.dettaglioPratica != null;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public RichiestaPraticheListaResponse RichiestaPraticheLista(RichiestaPraticheListaRequest richiesta)
        {
            using (var client = this._stcServiceCreator.CreateClient())
            {
                try
                {
                    var stcConfig = this._stcServiceCreator.ConfigurazioneStc;

                    richiesta.sportelloMittente = stcConfig.NodoMittente.AsSportelloType();

                    richiesta.token = client.Token;

                    richiesta.sportelloDestinatario = stcConfig.NodoDestinatario.AsSportelloType();

                    if (this._log.IsDebugEnabled)
                        this._log.DebugFormat("Inizio invocazione di STC::RichiestaPraticheLista, parametri: {0} ", StreamUtils.SerializeClass(richiesta));

                    var risposta = client.Service.RichiestaPraticheLista(richiesta);

                    if (risposta.dettaglioErrore != null && risposta.dettaglioErrore.Length > 0)
                    {
                        var errMsg = new StringBuilder();

                        errMsg.AppendFormat("Errore durante l'invocazione di STC::RichiestaPraticheLista con i parametri (seguono i dettagli dell'errore) \r\n{0}", StreamUtils.SerializeClass(richiesta));

                        for (var i = 0; i < risposta.dettaglioErrore.Length; i++)
                        {
                            var errore = risposta.dettaglioErrore[i];
                            errMsg.AppendFormat("Dettagli dell'errore:r\nCodice:{0}\r\nDescrizione:{1} ", errore.numeroErrore, errore.descrizione);
                        }

                        throw new VisuraException(errMsg.ToString());
                    }

                    if (this._log.IsDebugEnabled)
                        this._log.DebugFormat("Invocazione di STC::RichiestaPraticheLista terminata con successo, risposta: {0} ", StreamUtils.SerializeClass(risposta));

                    return risposta;
                }
                catch (Exception ex)
                {
                    client.Service.Abort();

                    this._log.ErrorFormat("Errore durante l'invocazione di STC::RichiestaPraticheLista, dettagli errore: {0}", ex.ToString());

                    throw;
                }
            }
        }


        public RichiestaPraticaResponse RichiestaPratica(string idPratica, SportelloType? sportelloDestinatario = null)
        {
            var stcConfig = this._stcServiceCreator.ConfigurazioneStc;

            using (var service = this._stcServiceCreator.CreateClient())
            {
                var richiestaPratica = new RichiestaPraticaRequest
                {
                    sportelloMittente = stcConfig.NodoMittente.AsSportelloType(),
                    sportelloDestinatario = sportelloDestinatario ?? stcConfig.NodoDestinatario.AsSportelloType(),
                    token = service.Token,
                    rifPratica = new RiferimentiPraticaType
                    {
                        idPratica = idPratica
                    }
                };

                try
                {
                    if (this._log.IsDebugEnabled)
                        this._log.DebugFormat("inizio invocazione di STC::RichiestaPratica con i parametri: {0}", StreamUtils.SerializeClass(richiestaPratica));

                    var result = service.Service.RichiestaPratica(richiestaPratica);

                    if (this._log.IsDebugEnabled)
                        this._log.DebugFormat("Invocazione di STC::RichiestaPratica terminata con successo. Risposta: {0}", StreamUtils.SerializeClass(result));

                    return result;
                }
                catch (Exception ex)
                {
                    service.Service.Abort();

                    this._log.ErrorFormat("Errore durante l'invocazione di STC::RichiestaPratica: {0}. \r\nDettagli Richiesta: {1}", ex.ToString(), StreamUtils.SerializeClass(richiestaPratica));

                    throw;
                }

            }
        }

        public AllegatoBinarioResponse AllegatoBinario(string codiceOggetto)
        {
            var stcConfig = this._stcServiceCreator.ConfigurazioneStc;

            using (var client = this._stcServiceCreator.CreateClient())
            {

                var configurazioneStc = this._stcServiceCreator.ConfigurazioneStc;

                var req = new AllegatoBinarioRequest
                {
                    token = client.Token,
                    riferimentiAllegato = new RiferimentiAllegatoType
                    {
                        idAllegato = codiceOggetto,
                        idAttivita = string.Empty,
                        idDocumento = string.Empty,
                        idPratica = string.Empty
                    },
                    sportelloMittente = configurazioneStc.NodoMittente.AsSportelloType(),
                    sportelloDestinatario = configurazioneStc.NodoDestinatario.AsSportelloType(),
                };

                try
                {
                    var ret = client.Service.AllegatoBinario(req);

                    return ret;
                }
                catch (Exception ex)
                {
                    client.Service.Abort();

                    this._log.ErrorFormat("Errore durante l'invocazione di STC::AllegatoBinario: {0}\r\n\r\nDettagli della richiesta: {1}", ex.ToString(), StreamUtils.SerializeClass(req));

                    throw;
                }
            }
        }

        #endregion


        private void SalvaMovimentoInUscita(NotificaAttivitaRequest request)
        {
            // Salvo la domanda in uscita nella cartella PraticheInUscita
            try
            {
                if (this._pathMapper.IsPathMappingSupported)
                {
                    var nomeFile = string.Concat("movimento", request.datiAttivita.idAttivita, "_", DateTime.Now.ToString("yyyyMMddHHmmss"), ".xml");
                    var pathSalvataggioPratiche = this.GetRootSalvataggio();
                    var pathFileDomanda = Path.Combine(pathSalvataggioPratiche, nomeFile);

                    this._log.DebugFormat("Xml della domanda in uscita salvato nel percorso {0}", pathFileDomanda);

                    using (var fs = File.Open(pathFileDomanda, FileMode.Create))
                    {
                        var xs = new XmlSerializer(request.GetType());
                        xs.Serialize(fs, request);
                    }
                }
            }
            catch (Exception ex)
            {
                this._log.WarnFormat("Non è stato possibile salvare l'xml della domanda in uscita per la seguente ragione: {0}", ex.ToString());
            }
        }

        private string GetRootSalvataggio()
        {
            var root = this._pathMapper.MapPath("~/PraticheInUscita");
            var today = DateTime.Now.ToString("yyyyMMdd");
            var combined = Path.Combine(root, today);

            if (!Directory.Exists(combined))
            {
                Directory.CreateDirectory(combined);
            }

            return combined;
        }

    }
}
