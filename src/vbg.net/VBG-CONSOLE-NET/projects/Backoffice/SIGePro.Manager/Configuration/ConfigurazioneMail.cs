//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;
//using Init.SIGePro.Manager.WsSigeproSecurity;

//namespace Init.SIGePro.Manager.Configuration
//{
//    public class ConfigurazioneMail : HandlerConfigurazione
//    {
//        public string Sender { get; protected set; }
//        public string LoginName { get; protected set; }
//        public string Password { get; protected set; }
//        public string MailServer { get; protected set; }
//        public bool UseAuthentication { get; protected set; }
//        public bool UseSsl { get; protected set; }
//        public bool AcceptInvalidCertificates { get; protected set; }
//        public int SmtpPort{ get; protected set; }

//        internal ConfigurazioneMail(ApplicationInfoType[] parametri):base( parametri )
//        {
//            Sender = GetParam("MAIL.SENDER");
//            LoginName = GetParam("MAIL.LOGINNAME");
//            Password = GetParam("MAIL.PASSWORD");
//            MailServer = GetParam("MAIL.MAILSERVER");
//            UseAuthentication = GetParam("MAIL.USE_AUTHENTICATION","0") == "1";
//            UseSsl = GetParam("MAIL.USE_SSL","0") == "1";
//            AcceptInvalidCertificates = GetParam("MAIL.ACCEPT_INVALID_CERTIFICATES","0") == "1";
//            SmtpPort = Convert.ToInt32(GetParam("MAIL.SMTP_PORT","25"));
//        }


//    }
//}
