using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.EGrammata2.Verticalizzazioni;
using System.IO;
using System.Net;

namespace Init.SIGePro.Protocollo.EGrammata2
{
    public class BaseService
    {
        protected readonly ProtocolloLogs Logs;
        protected readonly ProtocolloSerializer Serializer;

        protected readonly VerticalizzazioniConfiguration Parametri;
        //protected readonly string Url;
        protected readonly string BindingName;
        //protected readonly string CodiceEnte;
        //protected readonly string Username;
        //protected readonly string Password;
        protected string IndirizzoIp { get { return Dns.GetHostAddresses(Dns.GetHostName()).Where(x => x.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork).First().ToString(); } }
        //protected readonly string UserApp;
        //protected readonly string PasswordApp;
        //protected readonly string UrlLeggiProto;
        //protected readonly string Postazione;

        public BaseService(ProtocolloLogs logs, ProtocolloSerializer serializer,  VerticalizzazioniConfiguration parametri)
        {
            Logs = logs;
            Serializer = serializer;
            Parametri = parametri;
            /*CodiceEnte = parametri.CodiceEnte;
            Username = parametri.Username;
            Password = parametri.Password;
            UserApp = parametri.UserApp;
            PasswordApp = parametri.PasswordApp;
            Url = parametri.UrlProtocolla;*/

        }

        /*protected string ConvertXmlToBase64(object oXml, string nomeFile)
        {
            string xml = Serializer.Serialize(nomeFile, oXml, Validation.ProtocolloValidation.TipiValidazione.DTD_EGRAMMATA2);
            
            var buffer = File.ReadAllBytes(Path.Combine(Logs.Folder, nomeFile));
            return Convert.ToBase64String(buffer);
        }

        protected string ConvertFromBase64ToXml(string base64string)
        {
            var bufferRes = Convert.FromBase64String(base64string);
            return Encoding.UTF8.GetString(bufferRes);
        }*/
    }
}
