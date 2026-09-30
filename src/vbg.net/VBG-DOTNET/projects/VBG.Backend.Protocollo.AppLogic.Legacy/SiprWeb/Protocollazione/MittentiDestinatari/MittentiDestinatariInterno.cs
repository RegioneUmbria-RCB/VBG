

using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWeb.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariInterno : BaseMittentiDestinatari, IMittentiDestinatari
    {
        public MittentiDestinatariInterno(IDatiProtocollo datiProto) : base(datiProto)
        {

        }

        public string Mittente
        {
            get { return DatiProto.Amministrazione.PROT_UO; }
        }

        public string[] Destinatari
        {
            get { return new string[] { DatiProto.AmministrazioniProtocollo[0].PROT_UO }; }
        }


        public string[] DestinatariCC
        {
            get { return null; }
        }
    }
}
