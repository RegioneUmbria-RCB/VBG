using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using MailServiceWs;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;
using VBG.AppLogic.SSU.DataAccess;
using VBG.AppLogic.SSU.GeneratoreRicevute.InvioMail;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.AppLogic.GestioneGenerazioneRicevuta
{
    public class InvioEmailService
    {
        private readonly ILogger<InvioEmailService> _logger;
        private readonly MailServiceClient _mailServiceClient;
        private readonly IOggettiService _oggettiService;
        private readonly SsuCatalogoServiziClient _apiClient;

        public InvioEmailService(ILogger<InvioEmailService> logger, MailServiceClient mailServiceClient, IOggettiService oggettiService, SsuCatalogoServiziClient apiClient)
        {
            this._logger = logger;
            this._mailServiceClient = mailServiceClient;
            this._oggettiService = oggettiService;
            this._apiClient = apiClient;
        }

        public async Task<bool> InviaEmailAUtenteAsync(DomandaOnline domandaAr, FoDomandeSsu domandaSsu)
        {
            try
            {
                var codiceEnte = domandaAr.ReadInterface.Ssu.CodiceEnte;

                var destinatario = domandaAr.ReadInterface.AltriDati.DomicilioElettronico;

                this._logger.LogDebug("Invio email ricevuta per la domanda {0} all'utente {1}", domandaSsu.IdDomandaSsu, destinatario);

                var codiceComune = domandaAr.ReadInterface.AltriDati.CodiceComune;

                var templateEmail = await this._apiClient.GetRicevutaDomandaEmailAsync(codiceEnte);

                var datiRicevuta = await this._oggettiService.GetByIdAsync(domandaSsu.CodiceoggettoRicevuta.Value);

                var allegatoRicevuta = new AttachmentType[]
                {
                    new AttachmentType
                    {
                        id = domandaSsu.CodiceoggettoRicevuta.ToString(),
                        descrizione = $"Ricevuta della domanda {domandaSsu.IdDomandaSsu}",
                        fileName = datiRicevuta.FileName,
                        mimeType = datiRicevuta.MimeType,
                        binaryData = datiRicevuta.FileContent
                    }
                };

                return await this._mailServiceClient.InviaEmailAsync(destinatario, codiceComune, templateEmail.OggettoXsl!, templateEmail.CorpoXsl!, allegatoRicevuta);
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore durante l'invio: {0}", ex);
                throw;
            }

        }
    }
}
