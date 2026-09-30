using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.ConfigurazioneBackoffice;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.DatiDinamici
{
    public class OnceOnlyDatiDinamiciService : IOnceOnlyDatiDinamiciService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IDatiDinamiciOnceOnlyRepository _datiDinamiciOnceOnlyRepository;
        private readonly DatiDinamiciOnceOnlyClient _datiDinamiciOnceOnlyClient;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly IConfigurazione<ParametriOnceOnly> _configurazione;

        public bool IsOnceOnlyAttivo => this._configurazione.Parametri.AttivaCompilazioneOnceOnly;


        public OnceOnlyDatiDinamiciService(
            ISalvataggioDomandaStrategy salvataggioDomandaStrategy,
            IDatiDinamiciOnceOnlyRepository datiDinamiciOnceOnlyRepository,
            DatiDinamiciOnceOnlyClient onceOnlyClient,
            IAuthenticationDataResolver authenticationDataResolver,
            IConfigurazione<ParametriOnceOnly> configurazione
            )
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy ?? throw new ArgumentNullException(nameof(salvataggioDomandaStrategy));
            this._datiDinamiciOnceOnlyRepository = datiDinamiciOnceOnlyRepository ?? throw new ArgumentNullException(nameof(datiDinamiciOnceOnlyRepository));
            this._datiDinamiciOnceOnlyClient = onceOnlyClient ?? throw new ArgumentNullException(nameof(onceOnlyClient));
            this._authenticationDataResolver = authenticationDataResolver;
            this._configurazione = configurazione;
        }

        public async Task<ValoriPrecompilabiliOnceOnly> GetValoriCampiDinamiciPrecompilabiliAsync(int idDomanda)
        {
            if (!this._configurazione.Parametri.AttivaCompilazioneOnceOnly)
            {
                return ValoriPrecompilabiliOnceOnly.Vuoto();
            }

            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            // Se l'utente loggato non è uno dei richiedenti allora non posso precompilare i campi
            if (!this.VerificaUtenteLoggatoRichiedente(domanda))
            {
                return ValoriPrecompilabiliOnceOnly.Vuoto();
            }

            // Recupero dalla configurazione i campi dinamici che permettono una precompilazione
            var idIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;
            var endoSelezionati = domanda.ReadInterface.Endoprocedimenti.NonAcquisiti.Select(x => x.Codice).ToList();

            var identificativiDatiOnceOnly = await this._datiDinamiciOnceOnlyRepository.GetIdentificativiOnceOnlyByIdInterventoEndoAsync(idIntervento, endoSelezionati);

            // Se nessun campo dinamico prevede la precompilazione mi fermo qui
            if (!identificativiDatiOnceOnly.Any())
            {
                return ValoriPrecompilabiliOnceOnly.Vuoto();
            }

            // Se la domanda ha almeno una scheda dinamica compilata allora non posso precompilare i campi
            if (domanda.ReadInterface.DatiDinamici.Modelli.Where(x => x.Compilato).Any())
            {
                return ValoriPrecompilabiliOnceOnly.VuotoMaSupportaPrecompilazione(identificativiDatiOnceOnly);
            }

            // Leggo se nel servizio once only sono stati salvati dati per i campi dinamici che prevedono la precompilazione
            var valoriDatiOnceOnly = await this._datiDinamiciOnceOnlyClient.LeggiDatiDinamiciOnceOnlyAsync(identificativiDatiOnceOnly.ToPostDatiOnceOnlyRequest(this._authenticationDataResolver.DatiAutenticazione?.DatiUtente?.Codicefiscale ?? ""));

            if (!(valoriDatiOnceOnly?.Campi?.Any() ?? false))
            {
                return ValoriPrecompilabiliOnceOnly.VuotoMaSupportaPrecompilazione(identificativiDatiOnceOnly);
            }

            return new ValoriPrecompilabiliOnceOnly(identificativiDatiOnceOnly, valoriDatiOnceOnly);
        }

        private bool VerificaUtenteLoggatoRichiedente(DomandaOnline domanda)
        {
            return domanda.ReadInterface
                        .Anagrafiche
                        .GetRichiedenti()
                        .Where(x => x.Codicefiscale == (this._authenticationDataResolver.DatiAutenticazione?.DatiUtente?.Codicefiscale ?? ""))
                        .Any();
        }

        public async Task PrecompilaCampiDinamiciAsync(int idDomanda, ValoriPrecompilabiliOnceOnly valoriPrecompilabiliOnceOnly)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            var valoriOnceOnly = valoriPrecompilabiliOnceOnly.GetValoriOnceOnly();

            foreach (var campoOnceOnly in valoriOnceOnly)
            {
                domanda.WriteInterface.DatiDinamici.EliminaValoreDaIdcampo(campoOnceOnly.IdCampo);

                foreach (var valore in campoOnceOnly.Valori)
                {
                    domanda.WriteInterface.DatiDinamici.AggiornaOCrea(campoOnceOnly.IdCampo, valore.Indice, valore.IndiceMolteplicita, valore.Valore, valore.ValoreDecodificato, campoOnceOnly.NomeCampo);
                }
            }
        }

        public async Task SalvaCampiDinamiciAsync(int idDomanda, ListaIdentificativiOnceOnly identificativiOnceOnly)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            var datiDaSalvare = new DatiDinamiciOnceOnlyClient.PutDatoOnceOnlyRequest
            {
                CFUtente = this._authenticationDataResolver.DatiAutenticazione?.DatiUtente?.Codicefiscale ?? ""
            };

            foreach (var identificativo in identificativiOnceOnly.Identificativi)
            {
                var listaValori = domanda.ReadInterface.DatiDinamici.DatiDinamici.Where(x => x.IdCampo == identificativo.IdCampo);

                datiDaSalvare.Dati.Add(new DatiDinamiciOnceOnlyClient.PutDatoOnceOnlyDatiRequest
                {
                    FonteInterna = identificativo.FonteInterna,
                    IsUpload = identificativo.IsUpload,
                    Valori = listaValori.Select(x => new DatiDinamiciOnceOnlyClient.PutDatoOnceOnlyValoriRequest
                    {
                        Indice = x.IndiceScheda,
                        IndiceMolteplicita = x.IndiceMolteplicita,
                        Valore = x.Valore,
                        ValoreDecodificato = x.ValoreDecodificato
                    }).ToList()
                });
            }

            await this._datiDinamiciOnceOnlyClient.SalvaDatiDinamiciOnceOnlyAsync(datiDaSalvare);
        }


    }
}
