using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariArrivo : BaseMittentiDestinatari, IMittenteDestinatari
    {
        public MittentiDestinatariArrivo(string mittente, string[] destinatari) : base(mittente, destinatari)
        {

        }

        public string InCaricoADescrizione
        {
            get { return Destinatari[0]; }
        }

        public MittDestOut[] MittentiDestintari
        {
            get 
            { 
                var listMittenti = new List<MittDestOut>();
                listMittenti.Add(new MittDestOut { CognomeNome = Mittente});
                return listMittenti.ToArray();
            }
        }
    }
}
