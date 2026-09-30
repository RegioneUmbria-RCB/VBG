using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo.SegnaturaResponse;

namespace Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo
{
    public class MittentiDestinatariFactory
    {
        public static class Constants
        {
            public const string ENTRATA = "In entrata";
            public const string USCITA = "In uscita";
            public const string INTERNO = "Tra uffici";
        }


        public static ILeggiProtoMittentiDestinatari Create(Documento doc)
        {
            if (doc.TipoReg == Constants.ENTRATA)
                return new MittentiDestinatariArrivo(doc);
            else if (doc.TipoReg == Constants.USCITA)
                return new MittentiDestinatariPartenza(doc);
            else if (doc.TipoReg == Constants.INTERNO)
                return new MittentiDestinatariInterno(doc);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", doc.TipoReg));
        }
    }
}
