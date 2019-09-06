using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.LeggiProtocollo
{
    public class LeggiProtocolloResponseInterno : ILeggiProtoMittentiDestinatari
    {
        public LeggiProtocolloResponseInterno()
        {

        }

        public string InCaricoA => throw new NotImplementedException();

        public string InCaricoADescrizione => throw new NotImplementedException();

        public string Flusso => throw new NotImplementedException();

        public MittDestOut[] GetMittenteDestinatario()
        {
            throw new NotImplementedException();
        }
    }
}
