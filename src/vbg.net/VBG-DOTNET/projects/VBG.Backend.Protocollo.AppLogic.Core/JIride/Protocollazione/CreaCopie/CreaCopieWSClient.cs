using VBG.Backend.Protocollo.AppLogic.Core.JIride.Client;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.CreaCopie
{
    public class CreaCopieWSClient
    {
        private readonly ProtocolloLogs _protocolloLogs;
        private readonly ProtocolloSerializer _protocolloSerializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public CreaCopieWSClient(ProtocolloLogs protocolloLogs, ProtocolloSerializer protocolloSerializer, string url, IBindingFactory bindingFactory)
        {
            this._protocolloLogs = protocolloLogs;
            this._protocolloSerializer = protocolloSerializer;

            if (String.IsNullOrEmpty(url))
            {
                throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_JIRIDE NON È STATO VALORIZZATO.");
            }

            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(protocolloLogs, bindingFactory, url);
        }

        public CreaCopieOutXml CreaCopieString(string creaCopieRequestXml, string codiceAmministrazione, string codiceAOO)
        {
            return this._protocolloClientServiceCreator.Call(ws =>
            {
                var creaCopieOutXml = ws.CreaCopieString(creaCopieRequestXml, codiceAmministrazione, codiceAOO);
                this._protocolloLogs.InfoFormat("RISPOSTA DA CREA COPIE, RESPONSE XML: {0}", creaCopieOutXml);
                return this._protocolloSerializer.Deserialize<CreaCopieOutXml>(creaCopieOutXml);
            });
        }
    }
}
