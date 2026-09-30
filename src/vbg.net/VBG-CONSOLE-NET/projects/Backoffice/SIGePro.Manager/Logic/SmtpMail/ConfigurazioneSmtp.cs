//using System;
//using System.Collections.Generic;
//using System.Text;
//using PersonalLib2.Data;
//using Init.SIGePro.Data;
//using System.Configuration;
//using Init.SIGePro.Manager.Configuration;

//namespace Init.SIGePro.Manager.Logic.SmtpMail
//{
//    public partial class ConfigurazioneSmtp
//    {
//        public bool SslAcceptInvalidCertificates { get; set; }

//        public string Sender { get; set; }

//        public string LoginName { get; set; }

//        public string LoginPassword { get; set; }

//        public bool UseAuthentication { get; set; }

//        public int UseSsl{get;set;}

//        public int SmtpPort { get; set; }

//        public string MailServer { get; set; }

//        protected ConfigurazioneSmtp()
//        {

//        }


//        public static ConfigurazioneSmtp LeggiConfigurazione( DataBase db, string idComune , string software)
//        {
//            var dbCfg = new MailConfigMgr(db).GetById(idComune, software);

//            var rVal = new ConfigurazioneSmtp();

//            try
//            {
//                // Leggo la configurazione dal web.config
//                if (dbCfg == null || String.IsNullOrEmpty(dbCfg.Mailserver))
//                {
//                    rVal.Sender = ParametriConfigurazione.Get.Mail.Sender;
//                    rVal.LoginName = ParametriConfigurazione.Get.Mail.LoginName;
//                    rVal.LoginPassword = ParametriConfigurazione.Get.Mail.Password;
//                    rVal.UseAuthentication = ParametriConfigurazione.Get.Mail.UseAuthentication;
//                    rVal.UseSsl = ParametriConfigurazione.Get.Mail.UseSsl ? 1 : 0;
//                    rVal.SslAcceptInvalidCertificates = ParametriConfigurazione.Get.Mail.AcceptInvalidCertificates;
//                    rVal.SmtpPort = ParametriConfigurazione.Get.Mail.SmtpPort;
//                    rVal.MailServer = ParametriConfigurazione.Get.Mail.MailServer;

//                    if (dbCfg != null && !String.IsNullOrEmpty(dbCfg.Senderaddress))
//                        rVal.Sender = dbCfg.Senderaddress;
//                }
//                else // leggo la configurazione dal db
//                {
//                    rVal.Sender = dbCfg.Senderaddress;
//                    rVal.LoginName = dbCfg.Loginname;
//                    rVal.LoginPassword = dbCfg.Loginpass;
//                    rVal.UseAuthentication = dbCfg.Useauthentication.GetValueOrDefault(0) == 1;
//                    rVal.UseSsl = dbCfg.Usessl.GetValueOrDefault(0) ;
//                    rVal.SslAcceptInvalidCertificates = dbCfg.SslAcceptinvalidcertificates.GetValueOrDefault(0) == 1;
//                    rVal.SmtpPort = dbCfg.Port.GetValueOrDefault( 25 );
//                    rVal.MailServer = dbCfg.Mailserver;
//                }

//                return rVal;
//            }
//            catch (Exception ex)
//            {
//                throw new ConfigurationErrorsException(ex.ToString());
//            }
//        }
//    }
//}
