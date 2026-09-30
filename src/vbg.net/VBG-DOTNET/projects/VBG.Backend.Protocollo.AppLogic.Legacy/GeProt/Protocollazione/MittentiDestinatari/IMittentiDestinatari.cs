using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Protocollazione.MittentiDestinatari
{
    public interface IMittentiDestinatari
    {
        Mittente GetMittente();
        Destinazione[] GetDestinatari();
        RegistroTipo Flusso { get; }
        IndirizzoTelematico GetIndirizzoTelematico();

    }
}
