using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.LeggiProtocollo.MittentiDestinatari
{
    public class BaseMittentiDestinatari
    {
        protected readonly string Mittente;
        protected readonly string[] Destinatari;

        public BaseMittentiDestinatari(string mittente, string[] destinatari)
        {
            Mittente = mittente;
            Destinatari = destinatari;
        }
    }
}
