using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWeb.LeggiProtocollo.MittentiDestinatari
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

        public MittDestOutType[] MittentiDestintari
        {
            get 
            { 
                var listMittenti = new List<MittDestOutType>();
                listMittenti.Add(new MittDestOutType { CognomeNome = Mittente});
                return listMittenti.ToArray();
            }

        }
    }
}
