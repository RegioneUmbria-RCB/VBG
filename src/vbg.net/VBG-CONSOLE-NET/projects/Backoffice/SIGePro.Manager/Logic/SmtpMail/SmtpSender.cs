#define NEW_EMAIL


using System;
using System.Linq;
using System.ServiceModel;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.WsMailService;
using Init.SIGePro.Utils;
using Init.Utils;
using log4net;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.SmtpMail
{
	public partial class SmtpSender
	{
		ILog _log = LogManager.GetLogger(typeof(SmtpSender));

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
                _log.DebugFormat("Preparazione all'invio del messaggio con idcomune={0} e software={1}", idComune, software);

                var bindingConfigurationName = "MailServiceBinding";

                var paramName = "WSHOSTURL_MAILSERVICE";
                var url = AuthenticationManager.GetApplicationInfoValue(paramName);
                var binding = new BasicHttpBinding(bindingConfigurationName);

                _log.DebugFormat("Url richiamato {0} riletto dal parametro [{1}] del security", url, paramName);

                var endpoint = new EndpointAddress(url);
                _log.DebugFormat("Endopoint utilizzato {0}, letto dal web.config: ", bindingConfigurationName);

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

                    _log.DebugFormat("mailMessage passato {0}", StreamUtils.SerializeClass(mailMessage));

					if (message.Attachments != null)
					{
						mailMessage.attachments = message.Attachments.Select(x => new AttachmentType
						{
							binaryData = x.FileContent,
							fileName = x.FileName,
							mimeType = new OggettiMgr(db).GetContentType(x.FileName)
						}).ToArray();

						_log.DebugFormat("numero di allegati presenti {0}", mailMessage.attachments.Length);
					}
                    

                    _log.Debug("Inizio invio mail");
                    var response = ws.sendMail(new MessageRequest
                    {
                        mailMessage = mailMessage,
                        software = software,
                        token = db.ConnectionDetails.Token
                    });
                    _log.DebugFormat("Fine invio mail con esito {0}",response.esito);
                }
            }
            catch (Exception ex) 
            {
                _log.ErrorFormat("InviaEmail->Errore durante l'invio del messaggio: {0}", ex.ToString());

                try
                {
                    Logger.LogEvent(db, idComune, "SMTP_MAILER", "Errore durante l'invio del messaggio: " + ex.ToString(), "SMTP_MAILER");
                }
                catch (Exception) { }

                throw;
            }
		}

		/// <summary>
		/// Per l'invio tramite PEC con alcuni sistemi il metodo TLS non funziona in quanto è richiesta una connessione SSL 
		/// durante tutto il dialogo con il server. In questo caso non è possibile utilizzare l'oggetto SmtpClient ma va utilizzato 
		/// il layer di compatibilità con il vecchio CDO
		/// </summary>
		/// <param name="config">Configurazione del comune per l'invio mail</param>
		/// <param name="message">Messaggio da inviare</param>
        //private void InviaConCDO( ConfigurazioneSmtp config, SIGeProMailMessage message)
        //{
        //    System.Web.Mail.MailMessage newMail = new System.Web.Mail.MailMessage();

        //    newMail.Fields.Add("http://schemas.microsoft.com/cdo/configuration/smtpserver", config.MailServer);
        //    newMail.Fields.Add("http://schemas.microsoft.com/cdo/configuration/smtpserverport", config.SmtpPort);
        //    newMail.Fields.Add("http://schemas.microsoft.com/cdo/configuration/sendusing", "2");

        //    if (config.UseAuthentication)
        //    {
        //        newMail.Fields.Add("http://schemas.microsoft.com/cdo/configuration/smtpauthenticate", "1");
        //        newMail.Fields.Add("http://schemas.microsoft.com/cdo/configuration/sendusername", config.LoginName);
        //        newMail.Fields.Add("http://schemas.microsoft.com/cdo/configuration/sendpassword", config.LoginPassword);
        //    }

        //    if (config.UseSsl > 0)
        //        newMail.Fields.Add("http://schemas.microsoft.com/cdo/configuration/smtpusessl", config.UseSsl == 2 ? 1 : config.UseSsl );

        //    newMail.From = GetMittente(message, config).Address;

        //    newMail.Subject = message.Oggetto.Replace("\n", " ");
        //    newMail.BodyFormat = System.Web.Mail.MailFormat.Html;
        //    newMail.Body = (message.CorpoMail == null) ? String.Empty : message.CorpoMail;
        //    newMail.Priority = System.Web.Mail.MailPriority.High;

        //    var destinatari = new List<string>();
        //    var destinatariCc = new List<string>();
        //    var destinatariBcc = new List<string>();

        //    message.GetDestinatari().ForEach(delegate(MailAddress addr) { destinatari.Add(addr.Address); });
        //    message.GetDestinatariInCopia().ForEach(delegate(MailAddress addr) { destinatariCc.Add(addr.Address); });
        //    message.GetDestinatariInCopiaNascosta().ForEach(delegate(MailAddress addr) { destinatariBcc.Add(addr.Address); });


        //    newMail.To = String.Join(";", destinatari.ToArray());
        //    newMail.Cc = String.Join(";", destinatariCc.ToArray());
        //    newMail.Bcc = String.Join(";", destinatariBcc.ToArray());

        //    // Aggiungo gli allegati:
        //    // 1. Salvo gli allegati nella cartella temp di sistema
        //    // 2. Assegno gli allegati al messaggio
        //    var messageAttachments = message.GetAttachments();
        //    var basePath = Path.Combine(Path.GetTempPath(), Guid.NewGuid().ToString());
        //    var listaFilesCreati = new List<string>();

        //    try
        //    {
        //        _log.DebugFormat("Creazione della directory di salvataggio dei files temporanei: {0}" , basePath);
        //        Directory.CreateDirectory(basePath);

        //        foreach (var attachment in messageAttachments)
        //        {
        //            var idx = 0;
        //            var fileName = attachment.FileName;
        //            var destFileName = Path.Combine(basePath, fileName);

        //            while (File.Exists(destFileName))
        //            {
        //                idx++;
        //                fileName = Path.GetFileNameWithoutExtension(fileName) + "_" + idx + Path.GetExtension(fileName);

        //                destFileName = Path.Combine(basePath, fileName);
        //            }

        //            _log.DebugFormat("Salvataggio dell'allegato su filesystem: {0}", destFileName);
        //            File.WriteAllBytes(destFileName, attachment.FileContent);
        //            listaFilesCreati.Add(destFileName);
        //        }

        //        foreach (var fileName in listaFilesCreati)
        //        {
        //            _log.DebugFormat("Aggiungo l'allegato {0} al messaggio ", fileName);
        //            newMail.Attachments.Add(new System.Web.Mail.MailAttachment(fileName));
        //        }

        //        System.Web.Mail.SmtpMail.SmtpServer = config.MailServer + ":" + config.SmtpPort;

        //        _log.DebugFormat("Dti del messaggio in uscita: mittente={0}- destinatario:{1}- oggetto={2}- corpo={3}- numero allegati={4}-", newMail.From , 
        //                            newMail.To, newMail.Subject, newMail.Body, newMail.Attachments.Count );


        //        _log.DebugFormat("Invio il messaggio utilizzando il server {0}", System.Web.Mail.SmtpMail.SmtpServer);
        //        System.Web.Mail.SmtpMail.Send(newMail);

        //        _log.Debug("Messaggio inviato");
        //    }
        //    catch (Exception ex)
        //    {
        //        _log.ErrorFormat("InviaConCDO-> errore durante l'invio: {0}", ex.ToString());

        //        throw;
        //    }
        //    finally
        //    {
        //        _log.Debug("Eliminazione dei files temporanei");

        //        try
        //        {
        //            foreach (var fileName in listaFilesCreati)
        //            {
        //                _log.DebugFormat("Eliminazione del file {0}", fileName);
        //                File.Delete(fileName);
        //            }

        //            _log.DebugFormat("Eliminazione della directory temporanea {0}", basePath);
        //            Directory.Delete(basePath);

        //            _log.Debug("Directory eliminata");
        //        }
        //        catch (Exception ex)
        //        {
        //            _log.ErrorFormat("Errore durante la cancellazione dei files temporanei: {0}", ex.ToString());
        //        }
        //    }
        //}
        
		/// <summary>
		/// Effettua l'invio di mail tramite SMTP o TLS
		/// </summary>
		/// <param name="db"></param>
		/// <param name="idComune"></param>
		/// <param name="config"></param>
		/// <param name="message"></param>
        //private void InviaConSMTPoTLS( ConfigurazioneSmtp config, SIGeProMailMessage message, RemoteCertificateValidationCallback validationCallback)
        //{
        //    var client = new SmtpClient
        //    {
        //        Host = config.MailServer,
        //        Port = config.SmtpPort,
        //        EnableSsl = config.UseSsl == 1
        //    };

        //    if (config.UseAuthentication)
        //        client.Credentials = new System.Net.NetworkCredential(config.LoginName, config.LoginPassword);

        //    var messaggioInUscita = new MailMessage
        //    {
        //        From = GetMittente(message, config),
        //        IsBodyHtml = message.InviaComeHtml,
        //        Subject = message.Oggetto.Replace("\n", " "),
        //        Body = (message.CorpoMail == null) ? String.Empty : message.CorpoMail
        //    };

        //    message.GetDestinatari().ForEach(delegate(MailAddress addr) { messaggioInUscita.To.Add(addr); });
        //    message.GetDestinatariInCopia().ForEach(delegate(MailAddress addr) { messaggioInUscita.CC.Add(addr); });
        //    message.GetDestinatariInCopiaNascosta().ForEach(delegate(MailAddress addr) { messaggioInUscita.Bcc.Add(addr); });

        //    message.GetAttachments().ForEach(delegate(BinaryObject obj) { messaggioInUscita.Attachments.Add(obj.ToAttachment()); });

        //    if (config.UseSsl > 0 && config.SslAcceptInvalidCertificates)
        //    {
        //        // Accetta il certificato anche se non è valido
        //        ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls;
        //        ServicePointManager.ServerCertificateValidationCallback = validationCallback;/* delegate(Object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors sslPolicyErrors)
        //        {
        //            if (sslPolicyErrors != SslPolicyErrors.None)
        //                Logger.LogEvent(db, idComune, "SMTP_MAILER", "Errore durante la validazione del certificato.\nTipo errore: " + sslPolicyErrors.ToString() + "\nIl certificato è stato comunque accettato.", "SMTP_MAILER");

        //            return true;
        //        };*/
        //    }

        //    client.Send(messaggioInUscita);
        //}

        /*
		protected static MailAddress GetMittente(SIGeProMailMessage message, ConfigurazioneSmtp config)
		{
			string mittente = String.IsNullOrEmpty(message.Mittente) ? config.Sender : message.Mittente;

			try
			{
				return new MailAddress(mittente);
			}
			catch (FormatException)
			{
				throw new InvalidSenderException(mittente);
			}
		}
        */
	}
}
