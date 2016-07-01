using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloDeltaService;

namespace Init.SIGePro.Protocollo.Delta.Services
{
    public class DeltaTipiDocumentiService : BaseDeltaService
    {
        public DeltaTipiDocumentiService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string proxy)
            : base(url, logs, serializer, username, password, proxy)
        {

        }

        internal TipoLettera[] GetTipidocumenti()
        { 
            using(var ws = CreaWebService())
            {
                try
                {
                    _logs.InfoFormat("Chiamata a getTipiLettera (tipidocumento), username: {0}, password: {1}", _username, _password);
                    var tipiDocumento = ws.getTipiLettera(_username, _password);
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
