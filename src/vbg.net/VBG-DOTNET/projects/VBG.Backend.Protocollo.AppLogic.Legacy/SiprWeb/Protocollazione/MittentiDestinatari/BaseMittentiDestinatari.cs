

using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWeb.Protocollazione.MittentiDestinatari
{
    public class BaseMittentiDestinatari
    {
        protected IDatiProtocollo DatiProto { get; private set; }

        public BaseMittentiDestinatari(IDatiProtocollo datiProto)
        {
            DatiProto = datiProto;
        }
    }
}
