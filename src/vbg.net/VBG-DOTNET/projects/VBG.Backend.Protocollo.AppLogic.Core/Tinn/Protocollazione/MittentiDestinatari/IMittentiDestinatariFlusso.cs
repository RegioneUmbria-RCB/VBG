using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Segnatura;

namespace VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.MittentiDestinatari
{
    public interface IMittentiDestinatariFlusso
    {
        Mittente[] GetMittenti();
        Destinatario[] GetDestinatari();
        string Flusso { get; }
    }
}
