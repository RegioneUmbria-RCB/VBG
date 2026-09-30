using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneOneri;
using Init.SIGePro.Manager.Logic.GestionePosizioniDebitorie;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.PagamentiModena
{
    public class PagamentiModenaService
    {
        private readonly IResolveToken _resolveToken;
        private readonly IOneriService _oneriService;
        private readonly IPosizioniDebitorieService _posizioniDebitorieService;
        private readonly ILog _log = LogManager.GetLogger(typeof(PagamentiModenaService));

        public PagamentiModenaService(AuthenticationInfo authInfo)
        {
            this._resolveToken = new ConstantToken(authInfo.Token);
            this._oneriService = new OneriService(authInfo);
            this._posizioniDebitorieService = new PosizioniDebitorieService(this._resolveToken);
        }

        public AttivaPosizioniDebitorieResult AttivaPosizioniDebitorie(AttivaPosizioneDebitoriaModenaRequest request)
        {
            // Creo gli oneri a partire dagli importi e relative date scadenza. L'operazione viene effettuata tramite il ws Java.
            // e quindi non può essere transazionale
            var idOneri = this.CreaOneri(request);

            var result = (idOneri.Count() == 1) ?
                            this.AttivaPosizioneDebitoriaSingola(idOneri.First()) :
                            this.AttivaPosizioneDebitoriaRateizzata(idOneri);

            if (!result.Esito)
            {
                // Ho creato gli oneri ma non sono riuscito a creare le posizioni debitorie
                // Devo concellare gli oneri a mano perché non posso eseguire l'operazione di creazione sotto transazione
                // Se avessi usato una transazione giava non avrebbe potuto vedere i record inseriti... :(
                try
                {
                    this._oneriService.Elimina(idOneri);
                }
                catch (Exception ex)
                {
                    // Se sono qui la situazione potrebbe essere problematica: ho degli oneri inseriti che non hanno generato posizioni
                    // debitorie ma per qualche ragione non riesco ad eliminarli                    
                    var erroreBase = $"Non è stato possibile eliminare gli oneri con id {String.Format(",", idOneri)}. "
                        + $"<b>Gli oneri potrebbero essere in uno stato non valido, procedere manualmente all'eliminazione</b>";

                    this._log.Error($"{erroreBase}. Dettagli dell'errore: {ex} ");

                    return new AttivaPosizioneDebitoriaFallito(idOneri, new[] { erroreBase });
                }
            }

            return result;
        }

        private IEnumerable<int> CreaOneri(AttivaPosizioneDebitoriaModenaRequest request)
        {
            try
            {
                int codiceIstanza = request.CodiceIstanza;
                int causaleId = request.CausaleId;

                return request.Rate.Select(rata => this._oneriService.Inserisci(codiceIstanza, causaleId, rata.Importo, rata.DataScadenza, rata.NumeroRata)).ToList();
            }
            catch (Exception ex)
            {
                this._log.Error($"Non è stato possibile creare oneri per la richiesta {request.ToDebugString()}: {ex}");

                throw;
            }
        }

        private AttivaPosizioniDebitorieResult AttivaPosizioneDebitoriaRateizzata(IEnumerable<int> listaIdOneri)
        {
            try
            {
                var esitoRateizzato = this._posizioniDebitorieService.InserisciPosizioneDebitoriaRateizzata(listaIdOneri.Select(x => new InserisciPosizioneDebitoriaDaOnere(x)));

                if (!esitoRateizzato.InserimentoRiuscito)
                {
                    return new AttivaPosizioneDebitoriaFallito(listaIdOneri, esitoRateizzato.Errori);
                }

                return new AttivaPosizioneDebitoriaRiuscito(
                    esitoRateizzato.PosizioniDebitorie.Select(x => x.OnereId),
                    esitoRateizzato.PosizioniDebitorie.Select(x => x.PosizioneDebitoriaId)
                );
            }
            catch (Exception ex)
            {
                this._log.Error($"Non è stato possibile attivare una posizione debitoria rateizzata per gli oneri {String.Format(", ", listaIdOneri)}: {ex}");

                return new AttivaPosizioneDebitoriaFallito(listaIdOneri, new[] { ex.Message });
            }
        }

        private AttivaPosizioniDebitorieResult AttivaPosizioneDebitoriaSingola(int onereId)
        {
            try
            {
                var esitoSingolo = this._posizioniDebitorieService.InserisciPosizioneDebitoria(new InserisciPosizioneDebitoriaDaOnere(onereId));

                if (!esitoSingolo.InserimentoRiuscito)
                {
                    return new AttivaPosizioneDebitoriaFallito(new[] { onereId }, esitoSingolo.Errori);
                }

                return new AttivaPosizioneDebitoriaRiuscito(new[] { onereId }, new[] { esitoSingolo.IdPosizioneDebitoria.Value });
            }
            catch (Exception ex)
            {
                this._log.Error($"Non è stato possibile attivare una posizione debitoria singola per l'onere {onereId}: {ex}");

                return new AttivaPosizioneDebitoriaFallito(new[] { onereId }, new[] { ex.Message });
            }
        }
    }
}
