using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo.SegnaturaResponse;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo
{
    public class MittentiDestinatariPartenza : ILeggiProtoMittentiDestinatari
    {
        Documento _doc;

        public MittentiDestinatariPartenza(Documento doc)
        {
            _doc = doc;
        }

        public string InCaricoA
        {
            get { throw new NotImplementedException(); }
        }

        public string InCaricoADescrizione
        {
            get { throw new NotImplementedException(); }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            throw new NotImplementedException();
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
