using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    public class MittentiDestinatariOutFactory
    {
        private static class Constants
        {
            public const string FlussoArrivo = "ARR";
            public const string FlussoPartenza = "PAR";
            public const string FlussoInterno = "INT";
        }

        public static ILeggiProtoMittentiDestinatari Create(string flusso, IEnumerable<RapportoOutXml> rapporto, IEnumerable<SmistamentoOutXml> smistamenti, LeggiPecOutXML responsePec)
        {
            if (flusso == Constants.FlussoArrivo)
            {
                return new MittentiDestinatariOutArrivo(smistamenti, rapporto);
            }
            else if (flusso == Constants.FlussoPartenza)
            {
                return new MittentiDestinatariOutPartenza(smistamenti, rapporto, responsePec);
            }
            else if (flusso == Constants.FlussoInterno)
            {
                return new MittentiDestinatariOutInterno(smistamenti);
            }
            else
            {
                throw new Exception($"FLUSSO {flusso} NON GESTITO");
            }
        }
    }
}
