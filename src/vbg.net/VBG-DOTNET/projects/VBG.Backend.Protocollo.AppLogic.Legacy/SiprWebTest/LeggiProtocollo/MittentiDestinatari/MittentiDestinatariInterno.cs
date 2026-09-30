using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.LeggiProtocollo.MittentiDestinatari
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

        public MittDestOutType[] MittentiDestintari
        {
            get { return Destinatari.Select(x => new MittDestOutType { CognomeNome = x }).ToArray(); }
        }
    }
}
