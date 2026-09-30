using ProtocolloInsielMercatoService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.LeggiProtocollo
{
    public class MittentiDestinatariPartenza : ILeggiProtoMittentiDestinatari
    {
        protocolDetail _response;

        public MittentiDestinatariPartenza(protocolDetail response)
        {
            _response = response;
        }

        public string InCaricoA
        {
            get { return _response.recordIdentifier.officeCode; }
        }

        public string InCaricoADescrizione
        {
            get { return _response.recordIdentifier.officeCode; }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _response.recipientList.Select(x => x.ToMittDestOutFromDestinatario()).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
