using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.EProt.Titolario
{
    public class TitolarioRequest : IProtocollazione
    {
        VerticalizzazioniConfiguration _vert;

        public TitolarioRequest(VerticalizzazioniConfiguration vert)
        {
            _vert = vert;
        }

        public IEnumerable<KeyValuePair<string, string>> GetParametri()
        {
            return null;
        }

        public string Metodo
        {
            get { return String.Format("{0}/{1}", _vert.UrlBase.TrimEnd('/'), "richiesta_titolario"); }
        }


        public void Valida(IEnumerable<KeyValuePair<string, string>> parametri, Logs.ProtocolloLogs log)
        {
            
        }
    }
}
