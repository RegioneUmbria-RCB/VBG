using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using log4net;
using PersonalLib2.Data.Providers;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro
{
    public class WsDettaglioPraticaRepository// : IDettaglioPraticaRepository
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsDettaglioPraticaRepository));
        private readonly IConfigurazioneVbgRepository _configurazioneVbgRepository;
        private readonly IstanzeServiceCreator _istanzeServiceCreator;

        public WsDettaglioPraticaRepository(IConfigurazioneVbgRepository configurazioneVbgRepository, IstanzeServiceCreator istanzeServiceCreator)
        {
            this._configurazioneVbgRepository = configurazioneVbgRepository;
            this._istanzeServiceCreator = istanzeServiceCreator;
        }

        internal string GetUUIDDaCodiceIstanza(int codiceIstanza)
        {
            using (var ws = _istanzeServiceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetUuidDaCodiceIstanza(ws.Token, codiceIstanza);
                }
                catch (Exception)
                {
                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public Istanze GetByUuid(string uuid, bool effettuaSubVisuraMovimenti)
        {
            using (var ws = _istanzeServiceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetDettaglioPraticaByUuid(ws.Token, uuid, effettuaSubVisuraMovimenti);
                }
                catch (Exception ex)
                {
                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public RisultatoVisuraPraticaV3 GetListaPratiche(RichiestaListaPraticheV3 richiesta)
        {
            using (var ws = _istanzeServiceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetListaPraticheV3(ws.Token, richiesta);
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore durente la chiamata a GetListaPratiche: {ex.ToString()}");

                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public async Task<RisultatoVisuraPraticaV3> GetListaPratichePaginataAsync(RichiestaListaPraticheV3 richiesta, QueryPaginationRequest paginationRequest)
        {
            using (var ws = _istanzeServiceCreator.CreateClient())
            {
                try
                {
                    return await ws.Service.GetListaPraticheV3PaginatoAsync(ws.Token, richiesta, paginationRequest);
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore durente la chiamata a GetListaPratiche: {ex.ToString()}");

                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public Istanze GetById(int idPratica, bool leggiDatiConfigurazione)
        {
            _log.Debug("Invocazione del web service di visura, lettura dettaglio");

            using (var ws = _istanzeServiceCreator.CreateClient())
            {
                try
                {
                    _log.DebugFormat("Url del web service di visura: {0}", ws.Service.Endpoint.Address);
                    _log.DebugFormat("Id pratica = {0}, leggiDatiConfigurazione = {1}", idPratica, leggiDatiConfigurazione);

                    var istanza = ws.Service.GetDettaglioPratica(ws.Token, idPratica);

                    if (istanza == null)
                        return null;

                    if (istanza.Movimenti != null && istanza.Movimenti.Length > 0)
                    {
                        // Rimuovo dalla lista i movimenti che non hanno una data

                        var movimentiDaTenere = new List<Movimenti>();

                        foreach (var movimento in istanza.Movimenti)
                        {
                            if (movimento.DATA.HasValue)
                                movimentiDaTenere.Add(movimento);
                        }

                        movimentiDaTenere.Sort((a, b) =>
                        {
                            if (!a.DATA.HasValue && !b.DATA.HasValue)
                                return 0;

                            if (a.DATA.HasValue && !b.DATA.HasValue)
                                return -1;

                            if (!a.DATA.HasValue && b.DATA.HasValue)
                                return 1;

                            var id1 = a.DATA.Value.ToString("yyyyMMdd") + "-" + a.CODICEMOVIMENTO.PadLeft(10, '0');
                            var id2 = b.DATA.Value.ToString("yyyyMMdd") + "-" + b.CODICEMOVIMENTO.PadLeft(10, '0');

                            return id1.CompareTo(id2);
                        });

                        istanza.Movimenti = movimentiDaTenere.ToArray();
                    }

                    if (leggiDatiConfigurazione)
                        istanza.ConfigurazioneComune = _configurazioneVbgRepository.LeggiConfigurazioneComune(istanza.SOFTWARE);

                    return istanza;
                }
                catch (Exception ex)
                {
                    _log.ErrorFormat("WsDettaglioPraticaRepository.getById: Errore durante l'invocazione del web service: {0}, id pratica = {1} , leggiDatiConfigurazione = {2}", ex, idPratica, leggiDatiConfigurazione);

                    ws.Service.Abort();

                    throw;
                }
            }

        }
    }
}
