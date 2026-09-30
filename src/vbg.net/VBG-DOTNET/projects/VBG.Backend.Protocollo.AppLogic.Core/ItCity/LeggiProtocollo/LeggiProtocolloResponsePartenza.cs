
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.LeggiProtocollo
{
    public class LeggiProtocolloResponsePartenza : ILeggiProtoMittentiDestinatari
    {
        public LeggiProtocolloResponsePartenza()
        {

        }

        public string InCaricoA => throw new NotImplementedException();

        public string InCaricoADescrizione => throw new NotImplementedException();

        public string Flusso => throw new NotImplementedException();

        public MittDestOutType[] GetMittenteDestinatario()
        {
            throw new NotImplementedException();
        }
    }
}
