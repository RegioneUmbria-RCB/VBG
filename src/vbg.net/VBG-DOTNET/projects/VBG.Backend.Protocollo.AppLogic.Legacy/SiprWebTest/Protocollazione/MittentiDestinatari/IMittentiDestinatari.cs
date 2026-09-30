using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.Protocollazione.MittentiDestinatari
{
    public interface IMittentiDestinatari
    {
        string Mittente { get; }
        string[] Destinatari { get; }
        string[] DestinatariCC { get; }
    }
}
