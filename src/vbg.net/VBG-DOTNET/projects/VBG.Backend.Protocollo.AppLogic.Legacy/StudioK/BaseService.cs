using System;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.StudioK
{
    public class BaseServiceWrapper
    {
        protected string Url;
        protected ProtocolloLogs Logs;
        protected ProtocolloSerializer Serializer;
        protected string ConnectionString;
        protected string ProxyAddress;

        public BaseServiceWrapper(string url, string proxyAddress, string connectionString, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            Url = url;
            Logs = logs;
            Serializer = serializer;
            ConnectionString = connectionString;
            ProxyAddress = proxyAddress;
        }

        protected ProtocolloStudioKStub CreaWebService()
        {
            try
            {
                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_STUDIOK NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new ProtocolloStudioKStub(Url, ProxyAddress);

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }
    }
}
