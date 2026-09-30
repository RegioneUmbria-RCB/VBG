using Init.Sigepro.FrontEnd.AppLogic.Common;
using MailServiceWs;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.InvioMail
{
    public class MailServiceClient
    {
        private readonly MailServiceServiceCreator _serviceCreator;
        private readonly ISoftwareResolver _softwareResolver;
        private readonly ILogger<MailServiceClient> _logger;

        public MailServiceClient(MailServiceServiceCreator serviceCreator, ISoftwareResolver softwareResolver, ILogger<MailServiceClient> logger)
        {
            this._serviceCreator = serviceCreator;
            this._softwareResolver = softwareResolver;
            this._logger = logger;
        }

        public async Task<bool> InviaEmailAsync(string destinatario, string codiceComune, string oggetto, string corpo, AttachmentType[] allegati)
        {
            try
            {
                var result = await this._serviceCreator.CallAsync(async client =>
                {
                    var richiesta = new MessageRequest2
                    {
                        token = client.Token,
                        software = this._softwareResolver.Software,
                        codicecomune = codiceComune,
                        mailMessage = new MailMessageType
                        {
                            oggetto = oggetto,
                            corpoMail = corpo,
                            inviaComeHtml = true,
                            destinatari = destinatario,
                            attachments = allegati
                        }
                    };
                    return await client.Service.sendMail2Async(richiesta);
                });

                this._logger.LogDebug("Chiamata a sendMail2Async terminata con il seguente esito: {0}", result.MessageResponse2.esito);

                return result.MessageResponse2.esito == "OK";
            }
            catch (Exception ex)
            {
                this._logger.LogError(ex, "Errore durante l'esecuzione del metodo InviaEmailAsync: {0}", ex);
                throw;
            }
            
        }
    }
}
