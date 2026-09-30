using ProtocolloInsielMercatoService;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.LeggiProtocollo
{
    public class MittentiDestinatariArrivo : ILeggiProtoMittentiDestinatari
    {
        protocolDetail _response;

        public MittentiDestinatariArrivo(protocolDetail response)
        {
            _response = response;
        }

        public string InCaricoA
        {
            get { return _response.recipientList != null && _response.recipientList.Count() > 0 ? _response.recipientList.First().code : _response.recordIdentifier.officeCode; }
        }

        public string InCaricoADescrizione
        {
            get { return _response.recipientList != null && _response.recipientList.Count() > 0 ? _response.recipientList.First().description : _response.recordIdentifier.officeCode; }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return _response.senderList.Select(x => x.ToMittDestOutFromMittente()).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
