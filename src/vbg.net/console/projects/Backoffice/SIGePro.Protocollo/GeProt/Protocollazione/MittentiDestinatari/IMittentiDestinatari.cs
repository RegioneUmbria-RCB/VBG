using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione.MittentiDestinatari
{
    public interface IMittentiDestinatari
    {
        Mittente GetMittente();
        Destinazione[] GetDestinatari();
        RegistroTipo Flusso { get; }
        IndirizzoTelematico GetIndirizzoTelematico();

    }
}
