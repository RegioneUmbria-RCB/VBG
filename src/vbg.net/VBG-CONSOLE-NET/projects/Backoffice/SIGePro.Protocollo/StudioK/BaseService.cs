using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.StudioK
{
    public class BaseServiceWrapper
    {
        protected string Url;
        protected ProtocolloLogs Logs;
        protected ProtocolloSerializer Serializer;
        protected string ConnectionString;

        public BaseServiceWrapper(string url, string connectionString, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            Url = url;
            Logs = logs;
            Serializer = serializer;
            ConnectionString = connectionString;
        }

        protected ProtocolloStudioKStub CreaWebService()
        {
            try
            {
                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_STUDIOK NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new ProtocolloStudioKStub(Url);

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }
    }
}
