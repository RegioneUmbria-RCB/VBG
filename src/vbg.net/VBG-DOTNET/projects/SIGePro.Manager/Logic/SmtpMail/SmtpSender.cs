#define NEW_EMAIL


using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.WsMailService;
using Init.Utils;
using log4net;
using PersonalLib2.Data;
using System;
using System.Linq;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.SmtpMail
{
    public partial class SmtpSender
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SmtpSender));
        private readonly IBindingFactory _bindingFactory;
        private readonly IConfigurazioneGenerale _configurazioneGenerale;

        public SmtpSender(IBindingFactory bindingFactory, IConfigurazioneGenerale configurazioneGenerale)
        {
            this._bindingFactory = bindingFactory;
            this._configurazioneGenerale = configurazioneGenerale;
        }

        /// <summary>
        /// Invia una mail in base ai dati contenuti nel parametro "message".
        /// La configurazione utilizzata è quella contenuta nella tabella Mail_Config in base all'idComune e al 
        /// software specificati
        /// </summary>
        /// <param name="db">Oggetto database utilizzato per accedere ai dati</param>
        /// <param name="idComune">filtro idComune/alias</param>
        /// <param name="software">Software</param>
        /// <param name="message">Messaggio da inviare</param>
        public void InviaEmail(DataBase db, string idComune, string software, SIGeProMailMessage message)
        {
            try
            {
                this._log.DebugFormat("Preparazione all'invio del messaggio con idcomune={0} e software={1}", idComune, software);

                var bindingConfigurationName = "MailServiceBinding";

                var paramName = "WSHOSTURL_MAILSERVICE";
                var url = this._configurazioneGenerale.GetApplicationInfoValue(paramName);
                var binding = this._bindingFactory.CreateAndConfigure(bindingConfigurationName);

                this._log.DebugFormat("Url richiamato {0} riletto dal parametro [{1}] del security", url, paramName);

                var endpoint = new EndpointAddress(url);
                this._log.DebugFormat("Endopoint utilizzato {0}, letto dal web.config: ", bindingConfigurationName);

                using (var ws = new MailServicePortTypeClient(binding, endpoint))
                {

                    var mailMessage = new MailMessageType
                    {
                        corpoMail = message.CorpoMail,
                        destinatari = message.Destinatari,
                        destinatariInCopia = message.DestinatariInCopia,
                        destinatariInCopiaNascosta = message.DestinatariInCopiaNascosta,
                        inviaComeHtml = message.InviaComeHtml,
                        inviaComeHtmlSpecified = true,
                        mittente = message.Mittente,
                        oggetto = message.Oggetto
                    };

                    this._log.DebugFormat("mailMessage passato {0}", StreamUtils.SerializeClass(mailMessage));

                    if (message.Attachments != null)
                    {
                        mailMessage.attachments = message.Attachments.Select(x => new AttachmentType
                        {
                            binaryData = x.FileContent,
                            fileName = x.FileName,
                            mimeType = new OggettiMgr(db).GetContentType(x.FileName)
                        }).ToArray();

                        this._log.DebugFormat("numero di allegati presenti {0}", mailMessage.attachments.Length);
                    }


                    this._log.Debug("Inizio invio mail");

                    var response = ws.sendMail2(new MessageRequest2
                    {
                        mailMessage = mailMessage,
                        software = software,
                        token = db.ConnectionDetails.Token,
                    });


                    this._log.DebugFormat("Fine invio mail con esito {0}", response.esito);
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("InviaEmail->Errore durante l'invio del messaggio: {0}", ex.ToString());

                throw;
            }
        }


    }
}
