using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo.MittentiDestinatari
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
