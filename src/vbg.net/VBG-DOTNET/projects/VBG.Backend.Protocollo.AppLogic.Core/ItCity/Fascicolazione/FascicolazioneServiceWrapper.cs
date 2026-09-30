using VBG.Shared.Infrastructure.ServiceModel;
using ItCityService;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Fascicolazione
{
    public class FascicolazioneServiceWrapper
    {
        protected string _url;
        protected LoginWsInfo _loginInfo;
        protected ProtocolloLogs _logs;
        protected ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public FascicolazioneServiceWrapper(string url, LoginWsInfo loginInfo, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            this._url = url;
            this._loginInfo = loginInfo;
            this._logs = logs;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
        }

        public BaseOutput FascicolaProtocollo(int idDocumento, int idFascicolo, int numeroSottoFascicolo, int idIndice)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    this._logs.Info($"RICHIESTA DI FASCICOLAZIONE, METODO Completamento, ID DOCUMENTO: {idDocumento}, INDICE: {idIndice}, ID FASCICOLO: {idFascicolo}, NUMERO SOTTO-FASCICOLO: {numeroSottoFascicolo}");
                    var response = ws.Service.Completamento(this._loginInfo.Username, this._loginInfo.Password, this._loginInfo.Identificativo, idDocumento, (int?)null, idIndice, idFascicolo, numeroSottoFascicolo, (int?)null, "");

                    if (response.Exitcode != 0)
                    {
                        throw new Exception(response.ExitMessage);
                    }

                    this._logs.Info($"RICHIESTA DI FASCICOLAZIONE, METODO Completamento, ID DOCUMENTO: {idDocumento}, ID FASCICOLO: {idFascicolo}, NUMERO SOTTO FASCICOLO: {numeroSottoFascicolo}, AVVENUTA CON SUSCCESSO");
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"FASCICOLAZIONE FALLITA: {ex.Message}", ex);
            }
        }

        public Fascicolo CreaSottoFascicolo(FascicoloDiRiferimento fascicolo, CreazioneSottoFascicoloRequestInfo request, int IdUnitaOperativa)
        {
            try
            {

                var assegnatario = new IdentificativoUtente
                {
                    //ExtensionData = null,
                    IdUnitaOperativa = IdUnitaOperativa,
                    IdUtente = 0
                };

                if (fascicolo == null || fascicolo.Numero <= 0 ) {
                    throw new Exception($"NON E' POSSIBILE CREARE UN SOTTO FASCICOLO SENZA PASSARE I RIFERIMENTI DEL FASCICOLO");
                }

                if( fascicolo.NumeroSottofascicolo > 0 )
                {
                    throw new Exception($"E' STATA CHIAMATA LA CREAZIONE DI UN NUOVO SOTTOFASCICOLO PER IL FASCICOLO {fascicolo.Numero} MA E' STATO PASSATO ANCHE IL SOTTO FASCICOLO {fascicolo.NumeroSottofascicolo}");
                }

                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    this._logs.Info($"RICHIESTA DI CREAZIONE SOTTO FASCICOLO, METODO CreaFascicoloOSottofascicolo");
                    var response = ws.Service.CreaFascicoloOSottofascicolo(this._loginInfo.Username, this._loginInfo.Password, this._loginInfo.Identificativo, request.Coordinate, assegnatario, fascicolo);

                    if (response.ExitCode != 0)
                    {
                        throw new Exception(response.ExitMessage);
                    }

                    if (response.Fascicoli.Length > 1)
                    {
                        throw new Exception("IL RISULTATO DELLA CREAZIONE DEL SOTTO FASCICOLO HA PRODOTTO PIU' DI UN RISULTATO");
                    }

                    this._logs.Info($"RICHIESTA DI CREAZIONE SOTTO FASCICOLO AVVENUTA CON SUCCESSO");
                    return response.Fascicoli[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"CREAZIONE DEL SOTTO FASCICOLO FALLITA: {ex.Message}", ex);
            }
        }

        public Fascicolo CreaFascicolo(CreazioneFascicoloRequestInfo request, int IdUnitaOperativa)
        {
            try
            {
                var assegnatario = new IdentificativoUtente
                {
                    IdUnitaOperativa = IdUnitaOperativa,
                    IdUtente = 0
                };

                var fascicoloDiRiferimento = new FascicoloDiRiferimento
                {
                    Anno = 0,
                    Classe = 0,
                    IdFascicolo = 0,
                    Numero = 0,
                    NumeroSottofascicolo = 0,
                    SottoClasse = 0,
                    TitoloNumerico = 0
                };

                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    this._logs.Info($"RICHIESTA DI CREAZIONE FASCICOLO/SOTTO FASCICOLO, METODO CreaFascicoloOSottofascicolo");
                    var response = ws.Service.CreaFascicoloOSottofascicolo(this._loginInfo.Username, this._loginInfo.Password, this._loginInfo.Identificativo, request.Coordinate, assegnatario, fascicoloDiRiferimento);

                    if (response.ExitCode != 0)
                    {
                        throw new Exception(response.ExitMessage);
                    }

                    if (response.Fascicoli.Length > 1)
                    {
                        throw new Exception("IL RISULTATO DELLA CREAZIONE DEL FASCICOLO HA PRODOTTO PIU' DI UN RISULTATO");
                    }

                    this._logs.Info($"RICHIESTA DI CREAZIONE FASCICOLO/SOTTO FASCICOLO AVVENUTA CON SUCCESSO");
                    return response.Fascicoli[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"FASCICOLAZIONE FALLITA: {ex.Message}", ex);
            }
        }

        public Fascicolo CercaFascicolo(CercaFascicoloInfo request)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    this._logs.Info($"CHIAMATA A RicercaFascicolo, Numero Fascicolo: {request.NumeroFascicolo}, Anno: {request.AnnoFascicolo}, Titolo: {request.Titolo}, Classe: {request.Classe}, Sotto Classe: {request.SottoClasse}");
                    var response = ws.Service.RicercaFascicolo(this._loginInfo.Username, this._loginInfo.Password, this._loginInfo.Identificativo, request.Titolo, request.Classe, request.SottoClasse, request.AnnoFascicolo, request.NumeroFascicolo, request.NumeroSottoFascicolo, true);

                    if (response.ExitCode != 0)
                    {
                        throw new Exception(response.ExitMessage);
                    }

                    if (response.Fascicoli.Length > 1)
                    {
                        throw new Exception("LA RICERCA DEL FASCICOLO HA PRODOTTO PIU' DI UN RISULTATO");
                    }

                    this._logs.Info($"RICHIESTA DI RICERCA DEL FASCICOLO AVVENUTA CON SUCCESSO");
                    return response.Fascicoli[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"RICERCA FASCICOLO FALLITA: {ex.Message}", ex);
            }
        }

        public IEnumerable<Fascicolo> CercaFascicoli(CercaFascicoliInfo request)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    this._logs.Info($"CHIAMATA A RicercaFascicoli");

                    var ricFascicoloIn = new RicercaFascicoloIn
                    {
                        Titolo = request.Titolo,
                        Classe = request.Classe,
                        Sottoclasse = request.SottoClasse,
                        Anno = request.Anno,
                        Numero = request.NumeroFascicolo,
                        NumeroSottofascicolo = (int?)null,
                        Oggetto = !String.IsNullOrEmpty(request.Oggetto) ? '%' + request.Oggetto + '%' : "",
                        FlagStato = FiltroStatoFascicolo.A
                    };

                    //var response = ws.RicercaFascicolo(base.LoginInfo.Username, base.LoginInfo.Password, base.LoginInfo.Identificativo, request.Titolo, request.Classe, request.SottoClasse, request.Anno, request.NumeroFascicolo, (int?)null, true);
                    var response = ws.Service.RicercaFascicoloFull(this._loginInfo.Username, this._loginInfo.Password, this._loginInfo.Identificativo, ricFascicoloIn);

                    if (response.ExitCode != 0)
                    {
                        throw new Exception(response.ExitMessage);
                    }

                    this._logs.Info($"RICHIESTA DI RICERCA DEL FASCICOLO AVVENUTA CON SUCCESSO");
                    return response.Fascicoli;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"RICERCA FASCICOLI FALLITA: {ex.Message}", ex);
            }
        }

    }
}
