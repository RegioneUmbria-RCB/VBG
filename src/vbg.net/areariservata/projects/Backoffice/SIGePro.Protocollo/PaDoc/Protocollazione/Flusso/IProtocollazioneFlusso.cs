using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.Flusso
{
    public interface IProtocollazioneFlusso
    {
        string Flusso { get; }
        Mittente GetMittente();
        Destinazione[] GetDestinazioni();
        IndirizzoTelematico IndirizzoTelematicoOrigine { get; }
    }
}
