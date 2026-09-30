using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo
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

        public MittDestOut[] GetMittenteDestinatario()
        {
            return _response.recipientList.Select(x => x.ToMittDestOutFromDestinatario()).ToArray();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
