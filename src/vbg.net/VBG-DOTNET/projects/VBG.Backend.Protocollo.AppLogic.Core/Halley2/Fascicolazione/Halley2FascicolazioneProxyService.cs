using HalleyFascicolaService;
using HalleyUtilityService;
using VBG.Backend.Protocollo.AppLogic.Core.Halley2.Client;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione
{
    public class Halley2FascicolazioneProxyService
    {
        private IProtocolloSerializer _serializer;
        private IFascicolazioneResolver _fascicolazioneResolver;
        private FascicolaClient _fascicolaClient;
        private UtilityClient _utilityClient;

        public Halley2FascicolazioneProxyService(IProtocolloSerializer serializer, IFascicolazioneResolver fascicolazioneResolver)
        {
            this._serializer = serializer;
            this._fascicolazioneResolver = fascicolazioneResolver;
            this._fascicolaClient = new FascicolaClient(fascicolazioneResolver.UrlFascicolaProtocollo);
            this._utilityClient = new UtilityClient(fascicolazioneResolver.UrlServiziAggiuntivi);
        }

        public inserisciFascicoloResponse InserisciFascicolo(string classifica, string descrizioneFascicolo)
        {
            try
            {
                using (var ws = this._utilityClient.CreaWebService())
                {
                    HalleyUtilityService.Errore? errorOut = null;
                    int idFascicoloOut = 0;
                    string? annoOut = null;
                    string? numeroOut = null;

                    var done = ws.inserisciFascicolo(
                        new HalleyUtilityService.datiOperatore()
                        {
                            username = this._fascicolazioneResolver.Username,
                            password = this._fascicolazioneResolver.Password
                        },
                        classifica,
                        descrizioneFascicolo,
                        out errorOut,
                        out idFascicoloOut,
                        out annoOut,
                        out numeroOut);

                    return new inserisciFascicoloResponse()
                    {
                        esito = done,
                        errore = errorOut,
                        anno = annoOut,
                        idFascicolo = idFascicoloOut,
                        numero = numeroOut
                    };
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public ConsultaFascicoloResponse ConsultaFascicoli(ConsultaFascicoloRequest request)
        {
            try
            {
                using (var ws = this._utilityClient.CreaWebService())
                {
                    object outItem = null;

                    var done = ws.consultaFascicoli(
                        new HalleyUtilityService.datiOperatore() 
                        {
                            username = this._fascicolazioneResolver.Username, 
                            password = this._fascicolazioneResolver.Password
                        },
                        request.IdFascicolo,
                        request.Progressivo,
                        string.IsNullOrEmpty(request.Anno) ? null : request.Anno,
                        request.Categoria,
                        request.Classe,
                        request.SottoClasse,
                        out outItem);

                    var response = new ConsultaFascicoloResponse()
                    {
                        Esito = done
                    };

                    if (response.Esito)
                    {
                        var fascicoli = outItem as consultaFascicoliResponseFascicoli;

                        if (fascicoli != null)
                        {
                            response.fascicoli = fascicoli.fascicolo;
                        }
                    }
                    else
                    {
                        var errore = outItem as Errore;

                        if (errore != null)
                        {
                            response.Errore = errore;
                        }
                    }

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public fascicolaProtocolloResponse FascicolaProtocollo(fascicolaProtocolloRequest request)
        {
            try
            {
                using (var ws = this._fascicolaClient.CreaWebService())
                {
                    int code = 0;
                    string desc = string.Empty;

                    var done = ws.fascicolaProtocollo(
                        new HalleyFascicolaService.datiOperatore()
                        {
                            username = this._fascicolazioneResolver.Username,
                            password = this._fascicolazioneResolver.Password
                        },
                        request.idFascicolo,
                        request.idProtocollo,
                        out code,
                        out desc);

                    var response = new fascicolaProtocolloResponse()
                    {
                        esito = done,
                        codice = code,
                        descrizione = desc
                    };

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
    }
}
