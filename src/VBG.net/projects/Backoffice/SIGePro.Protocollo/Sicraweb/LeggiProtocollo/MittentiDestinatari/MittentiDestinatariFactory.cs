using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        public static ILeggiProtoMittentiDestinatari Create(Segnatura.Segnatura response)
        {
            ILeggiProtoMittentiDestinatari rVal = null;

            string flusso = response.Intestazione.Identificatore.Flusso;
            var factoryVersione = new MittentiDestinatariVersioneFactory(response);

            if (flusso == ProtocolloConstants.COD_ARRIVO_DOCAREA)
                rVal = new MittentiDestinatariArrivo(factoryVersione);
            else if (flusso == ProtocolloConstants.COD_INTERNO_DOCAREA)
                rVal = new MittentiDestinatariInterno(factoryVersione);
            else if (flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
                rVal = new MittentiDestinatariPartenza(factoryVersione);
            else
                throw new Exception(String.Format("FLUSSO {0} NON TROVATO", flusso));

            return rVal;
        }
    }
}
