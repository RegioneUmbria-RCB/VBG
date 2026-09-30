using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.LeggiProtocollo.MittentiDestinatari
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

        public MittDestOutType[] MittentiDestintari
        {
            get { return GetDestinatari(); }
        }

        private MittDestOutType[] GetDestinatari()
        {
            var rVal = new List<MittDestOutType>();
            
            var destinatari = Destinatari.Select(x => new MittDestOutType { CognomeNome = x });
            rVal.AddRange(destinatari);

            if (_destinatariCC != null)
            {
                var destinatariCC = _destinatariCC.Select(x => new MittDestOutType { CognomeNome = x });
                rVal.AddRange(destinatariCC);
            }

            return rVal.ToArray();
        }
    }
}
