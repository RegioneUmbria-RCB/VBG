using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.EProt.TipiDocumento
{
    public class TipiDocumentoRequest : IProtocollazioneEprot    
    {
        VerticalizzazioniConfiguration _vert;

        public TipiDocumentoRequest(VerticalizzazioniConfiguration vert)
        {
            _vert = vert;
        }

        public IEnumerable<KeyValuePair<string, string>> GetParametri()
        {
            return null;
        }

        public string Metodo
        {
            get { return String.Format("{0}/{1}", _vert.UrlBase.TrimEnd('/'), "richiesta_tipi_documento"); }
        }


        public void Valida(IEnumerable<KeyValuePair<string, string>> parametri, Shared.Logs.ProtocolloLogs log)
        {
            
        }
    }
}
