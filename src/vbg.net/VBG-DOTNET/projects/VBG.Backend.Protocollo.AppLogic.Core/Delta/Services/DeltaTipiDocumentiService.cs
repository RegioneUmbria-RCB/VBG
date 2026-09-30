using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloDeltaService;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Delta.Services
{
    public class DeltaTipiDocumentiService
    {
        private readonly ProtocolloLogs _logs;
        private readonly string _username;
        private readonly string _password;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public DeltaTipiDocumentiService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string username, string password, string proxy)
        {
            this._logs = logs;
            this._username = username;
            this._password = password;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(this._logs, bindingFactory, url, proxy);
        }

        internal TipoLettera[] GetTipidocumenti()
        { 
            using(var ws = this._protocolloClientServiceCreator.CreateClient())
            {
                try
                {
                    _logs.InfoFormat("Chiamata a getTipiLettera (tipidocumento), username: {0}, password: {1}", _username, _password);
                    var tipiDocumento = ws.Service.getTipiLettera(_username, _password);
                    _logs.InfoFormat("Chiamata a getTipiLettera avvenuta con successo numero record restituiti: {0}", tipiDocumento.Length);

                    return tipiDocumento;
                }
                catch (Exception ex)
                {
                    throw new Exception("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE getTipiLettera", ex);
                }
            }
        }
    }
}
