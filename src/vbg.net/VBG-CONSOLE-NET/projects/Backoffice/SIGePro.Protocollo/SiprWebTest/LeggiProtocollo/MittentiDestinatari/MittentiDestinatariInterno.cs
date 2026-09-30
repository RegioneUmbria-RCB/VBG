using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariInterno : BaseMittentiDestinatari, IMittenteDestinatari
    {
        public MittentiDestinatariInterno(string mittente, string[] destinatari) : base(mittente, destinatari)
        {

        }

        public string InCaricoADescrizione
        {
            get { return Mittente; }
        }

        public MittDestOut[] MittentiDestintari
        {
            get { return Destinatari.Select(x => new MittDestOut { CognomeNome = x }).ToArray(); }
        }
    }
}
