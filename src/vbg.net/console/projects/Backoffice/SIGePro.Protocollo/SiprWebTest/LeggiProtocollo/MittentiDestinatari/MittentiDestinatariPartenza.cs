using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariPartenza : BaseMittentiDestinatari, IMittenteDestinatari
    {
        string[] _destinatariCC;

        public MittentiDestinatariPartenza(string mittente, string[] destinatari, string[] destinatariCC) : base(mittente, destinatari)
        {
            _destinatariCC = destinatariCC;
        }

        public string InCaricoADescrizione
        {
            get { return Mittente; }
        }

        public MittDestOut[] MittentiDestintari
        {
            get { return GetDestinatari(); }
        }

        private MittDestOut[] GetDestinatari()
        {
            var rVal = new List<MittDestOut>();
            
            var destinatari = Destinatari.Select(x => new MittDestOut { CognomeNome = x });
            rVal.AddRange(destinatari);

            if (_destinatariCC != null)
            {
                var destinatariCC = _destinatariCC.Select(x => new MittDestOut { CognomeNome = x });
                rVal.AddRange(destinatariCC);
            }

            return rVal.ToArray();
        }
    }
}
