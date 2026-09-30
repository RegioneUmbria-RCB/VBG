using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari.Errori;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari
{
    public class ValidazioneSoggettiRiepilogoDomandaService
    {
        private readonly IConfigurazione<ParametriPresentazioneDomanda> _config;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly SoggettiFirmatariServiceCreator _serviceCreator;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly IVerificaFirmaDigitaleService _verificaFirmaDigitaleService;

        public ValidazioneSoggettiRiepilogoDomandaService(IConfigurazione<ParametriPresentazioneDomanda> config, ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAuthenticationDataResolver authenticationDataResolver, SoggettiFirmatariServiceCreator serviceCreator, IVerificaFirmaDigitaleService verificaFirmaDigitaleService)
        {
            this._config = config;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._serviceCreator = serviceCreator;
            this._authenticationDataResolver = authenticationDataResolver;
            this._verificaFirmaDigitaleService = verificaFirmaDigitaleService;
        }

        public ValidazioneSoggettiRiepilogoDomandaResult ValidaRiepilogoDomanda(int idDomanda, BinaryFile riepilogoDomandaFirmato) 
        {
            // se la verifica dei soggetti firmatari è disabilitata, rsestituisce true
            if (!this._config.Parametri.VerificaFirmaSoggettiRiepilogo) 
            {
                return new ValidazioneSoggettiRiepilogoDomandaResult 
                {
                    Result = true, 
                    ErroriValidazione = new List<string>() 
                };
            }

            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var idRiepilogo = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda().IdRiferimentoBackoffice;

            if (idRiepilogo == null) 
            { 
                throw new NullReferenceException($"Riepilogo non trovato per la domanda {idDomanda}");
            }

            var listaCfSoggetti = new List<string>();

            // Recupera i tipi di soggetto da verificare per il riepilogo della domanda
            var tipiSoggettoDaVerificare = this._serviceCreator.Call((ws) => ws.Service.GetSoggettiFirmatariRiepilogoDomanda(ws.Token, idRiepilogo.Value));

            foreach (var tipoSoggettoDaCercare in tipiSoggettoDaVerificare.SoggettiFirmatari)
            {
                var soggetti = domanda.ReadInterface.Anagrafiche.Anagrafiche.Where(x => x.TipoSoggetto.Id == tipoSoggettoDaCercare.Id);

                if (soggetti.Any()) 
                {
                    listaCfSoggetti.AddRange(soggetti.Select(x => x.Codicefiscale));
                }
            }

            // Se è richiesta la verifica della firma dell'utente loggato, aggiunge il suo codice fiscale alla lista dei soggetti da verificare
            if (tipiSoggettoDaVerificare.VerificaFirmaUtenteLoggato)
            {
                var cfUtenteLoggato = this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale;

                if (!listaCfSoggetti.Contains(cfUtenteLoggato))
                {
                    listaCfSoggetti.Add(cfUtenteLoggato);
                }
            }

            var validazioneSoggettiResult = this._verificaFirmaDigitaleService.VerificaPresenzaSoggetti(riepilogoDomandaFirmato, listaCfSoggetti);

            // Se non ci sono soggetti assenti restituisce true, altrimenti costruisce la lista dei messaggi di errore e restituisce false
            if (!validazioneSoggettiResult.CfAssenti.Any())
            {
                return new ValidazioneSoggettiRiepilogoDomandaResult
                {
                    Result = true,
                    ErroriValidazione = new List<string>()
                };
            }
            else 
            {
                var listaErrori = new List<string>();

                foreach (var cfAssente in validazioneSoggettiResult.CfAssenti)
                {
                    var soggetto = domanda.ReadInterface.Anagrafiche.Anagrafiche.FirstOrDefault(x => x.Codicefiscale == cfAssente);

                    if (soggetto is not null) 
                    {
                        listaErrori.Add(MessaggioSoggettoFirmatarioNonTrovato.BuildMessage(soggetto.Nome, soggetto.Nominativo, soggetto.Codicefiscale, soggetto.TipoSoggetto.Descrizione));
                    }
                }

                return new ValidazioneSoggettiRiepilogoDomandaResult
                {
                    Result = false,
                    ErroriValidazione = listaErrori
                };
            }
            
        }
    }
}
