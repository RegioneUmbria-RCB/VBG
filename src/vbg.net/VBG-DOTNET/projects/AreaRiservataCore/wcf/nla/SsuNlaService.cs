using Init.Sigepro.FrontEnd.WebServices.Nla;
using VBG.AppLogic.SSU.GestioneCorrezioni;
using VBG.AppLogic.SSU.GestioneIstanzaRifiutata;
using VBG.AppLogic.SSU.GestioneRichiesteIntegrazioni;

namespace AreaRiservataCore.wcf.nla
{
    public class SsuNlaService
    {
        private static class Constants
        {
            public const string ElementoAttivitaContenenteCorrezioniSsu = "SSU_CORREZIONI_RICHIESTE";
            public const string ElementoAttivitaContenenteIntegrazioniSsu = "SSU_INTEGRAZIONI_RICHIESTE";
        }


        private readonly IGestioneIstanzaRifiutataSsuService _istanzeRifiutateService;
        private readonly IGestioneCorrezioniSsuService _gestioneCorrezioniSsuService;
        private readonly IGestioneRichiestaIntegrazioniService _gestioneRichiestaIntegrazioniService;
        private readonly ILogger<SsuNlaService> _logger;

        public SsuNlaService(IGestioneIstanzaRifiutataSsuService istanzeRifiutateService, IGestioneCorrezioniSsuService gestioneCorrezioniSsuService, IGestioneRichiestaIntegrazioniService gestioneRichiestaIntegrazioniService, ILogger<SsuNlaService> logger)
        {
            this._istanzeRifiutateService = istanzeRifiutateService;
            this._gestioneCorrezioniSsuService = gestioneCorrezioniSsuService;
            this._gestioneRichiestaIntegrazioniService = gestioneRichiestaIntegrazioniService;
            this._logger = logger;
        }

        public InserimentoAttivitaNLAResponse1 CreaRichiestaIntegrazioneSsu(string identificativoDomanda, InserimentoAttivitaNLARequest1 request)
        {
            // Ho appurato che si tratta di una richiesta di integrazione SSU, vado a creare le classi che permettono di salvare i dati della pratica su db
            // 1. Verifico se nella sezione altriDati c'è un elemento con chiave "SSU_INTEGRAZIONI_RICHIESTE"
            // 2. Se c'è, lo deserializzo in un oggetto di tipo SsuRichiestaIntegrazione
            // 3. Chiamo il servizio di integrazione SSU passando l'oggetto deserializzato
            var dati = request.InserimentoAttivitaNLARequest
                                        .datiAttivita
                                        .altriDati?
                                        .Where(x => x.nome == Constants.ElementoAttivitaContenenteIntegrazioniSsu)?
                                        .FirstOrDefault()?
                                        .valore?
                                        .FirstOrDefault()?
                                        .descrizione;

            if (dati is null)
            {
                this._logger.LogWarning("Dati di integrazione SSU non trovati nella richiesta per la pratica {IdPratica}", request.InserimentoAttivitaNLARequest.datiAttivita.idPratica);
                return ErroriInserimentoAttivita.DettagliIntegrazioniSsuNonTrovati(Constants.ElementoAttivitaContenenteCorrezioniSsu);
            }

            try
            {
                var integrazioni = SsuRichiestaIntegrazione.FromBase64Json(dati);

                this._gestioneRichiestaIntegrazioniService.InserisciRichiestaIntegrazioneDaIdentificativoDomanda(identificativoDomanda, integrazioni);

                return InserimentoAttivitaResult.Success(identificativoDomanda, request.InserimentoAttivitaNLARequest.datiAttivita.idAttivita);
            }
            catch (Exception ex)
            {
                this._logger.LogError(ex, "Errore durante l'inserimento delle integrazioni SSU per la pratica {IdentificativoDomanda}", request.InserimentoAttivitaNLARequest.datiAttivita.idPratica);

                return ErroriInserimentoAttivita.ErroreGenerico($"Errore durante l'inserimento delle integrazioni SSU: {ex}");
            }
        }

        public InserimentoAttivitaNLAResponse1 CreaCorrezioneSsu(string identificativoDomanda, InserimentoAttivitaNLARequest1 request)
        {
            // Ho appurato che si tratta di una richiesta di correzione SSU, vado a creare le classi che permettono di salvare i dati della pratica su db
            // 1. Verifico se nella sezione altriDati c'è un elemento con chiave "SSU_CORREZIONI_RICHIESTE"
            // 2. Se c'è, lo deserializzo in un oggetto di tipo SsuProcedimentoCorrezione
            // 3. Chiamo il servizio di correzione SSU passando l'oggetto deserializzato
            var datiCorrezioni = request.InserimentoAttivitaNLARequest
                                        .datiAttivita
                                        .altriDati?
                                        .Where(x => x.nome == Constants.ElementoAttivitaContenenteCorrezioniSsu)?
                                        .FirstOrDefault()?
                                        .valore?
                                        .FirstOrDefault()?
                                        .descrizione;

            if (datiCorrezioni is null)
            {
                this._logger.LogWarning("Dati di correzione SSU non trovati nella richiesta per la pratica {IdPratica}", request.InserimentoAttivitaNLARequest.datiAttivita.idPratica);
                return ErroriInserimentoAttivita.DettagliCorrezioniSsuNonTrovati(Constants.ElementoAttivitaContenenteCorrezioniSsu);
            }

            try
            {
                var correzioniSsu = SsuProcedimentoCorrezione.FromBase64Json(datiCorrezioni);

                this._gestioneCorrezioniSsuService.InserisciCorrezioniDaIdentificativoDomanda(identificativoDomanda, correzioniSsu);

                return InserimentoAttivitaResult.Success(identificativoDomanda, request.InserimentoAttivitaNLARequest.datiAttivita.idAttivita);
            }
            catch (Exception ex)
            {
                this._logger.LogError(ex, "Errore durante l'inserimento delle correzioni SSU per la pratica {IdentificativoDomanda}", request.InserimentoAttivitaNLARequest.datiAttivita.idPratica);

                return ErroriInserimentoAttivita.ErroreGenerico($"Errore durante l'inserimento delle correzioni SSU: {ex}");
            }
        }

        public InserimentoAttivitaNLAResponse1 RifiutaIstanzaSsu(string identificativoDomanda, InserimentoAttivitaNLARequest1 request)
        {
            try
            {
                this._istanzeRifiutateService.RifiutaIstanzaDaIdentificativoDomanda(identificativoDomanda);

                return InserimentoAttivitaResult.Success(identificativoDomanda, request.InserimentoAttivitaNLARequest.datiAttivita.idAttivita);
            }
            catch (Exception ex)
            {
                this._logger.LogError(ex, "Errore durante il rifiuto dell'istanza SSU per la pratica {IdentificativoDomanda}", identificativoDomanda);

                throw;
            }
        }
    }
}
