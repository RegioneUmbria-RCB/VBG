using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC.Service;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using VBG.AppLogic.SSU.Configurazione;
using VBG.AppLogic.SSU.DataAccess;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.AppLogic.GestioneGenerazioneRicevuta
{
    public class NotificaStcService
    {
        private static class Constants
        {
            public const string InvioRicevuta = "INVIO_RICEVUTA";
        }

        private readonly IStcService _stcService;
        private readonly ILogger<NotificaStcService> _logger;

        private readonly IConfigurazione<ParametriSsu> _configurazione;

        public NotificaStcService(IStcService stcService, ILogger<NotificaStcService> logger, IConfigurazione<ParametriSsu> configurazione)
        {
            this._stcService = stcService;
            this._logger = logger;
            this._configurazione = configurazione;
        }

        public bool NotificaAttivitaAStc(DomandaOnline domandaAr, FoDomandeSsu domandaSsu)
        {
            var request = new NotificaAttivitaRequest();
            request.datiAttivita = new DettaglioAttivitaType
            {
                idPratica = domandaAr.DataKey.ToSerializationCode(),
                idAttivita = Guid.NewGuid().ToString(), // Constants.InvioRicevuta,
                tipoAttivita = new TipoAttivitaType
                {
                    codice = Constants.InvioRicevuta,
                    descrizione = Constants.InvioRicevuta,
                },
                esito = true,
                documenti =
                [
                    new DocumentiType
                    {
                        id = domandaSsu.CodiceoggettoRicevuta.ToString(),
                        tipoDocumento = "Ricevuta",
                        documento = $"Ricevuta della domanda {domandaSsu.IdDomandaSsu}",
                        allegati = new AllegatiType
                        {
                            file = null,
                            allegato = $"ricevuta.{domandaSsu.IdDomandaSsu}.pdf",
                            id = domandaSsu.CodiceoggettoRicevuta.ToString()
                        }
                    }
                ]
            };

            var response = this._stcService.NotificaAttivita(request, (sportello) =>
            {
                var sportelloDestinatario = this._configurazione.Parametri.GetSportelloDestinatario();
                sportello.idNodo = sportelloDestinatario!.idNodo;
                sportello.idSportello = sportelloDestinatario.idSportello;
                sportello.idEnte = sportelloDestinatario.idEnte;
            });

            if (response.Items.Any(x => x is ErroreType))
            {
                foreach (var errore in response.Items.OfType<ErroreType>())
                {
                    this._logger.LogError("La chiamata a NotificaAttivita ha restituito il seguente errore: numero errore {0}, descrizione: {1}", errore.numeroErrore, errore.descrizione);
                }
                return false;
            }

            return true;
        }
    }
}
