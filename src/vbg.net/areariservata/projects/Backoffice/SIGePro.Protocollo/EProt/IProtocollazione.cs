using Init.SIGePro.Protocollo.Logs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.EProt
{
    public interface IProtocollazione
    {
        IEnumerable<KeyValuePair<string, string>> GetParametri();
        string Metodo { get; }
        void Valida(IEnumerable<KeyValuePair<string, string>> parametri, ProtocolloLogs log);
    }
}
