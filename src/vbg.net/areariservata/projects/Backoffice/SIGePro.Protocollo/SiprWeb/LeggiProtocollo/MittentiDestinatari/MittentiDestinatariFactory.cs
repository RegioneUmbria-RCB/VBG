using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.SiprWeb.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        public static IMittenteDestinatari Create(leggiDocumentoResponse response)
        { 
            IMittenteDestinatari rVal = null;
            string flusso = response.Registro.ToString();

            if (flusso == ProtocolloConstants.COD_ARRIVO)
                rVal = new MittentiDestinatariArrivo(response.Mittente, response.Destinatari);
            else if (flusso == ProtocolloConstants.COD_PARTENZA)
                rVal = new MittentiDestinatariPartenza(response.Mittente, response.Destinatari, response.DestinatariCC);
            else if (flusso == ProtocolloConstants.COD_INTERNO)
                rVal = new MittentiDestinatariInterno(response.Mittente, response.Destinatari);
            else
                throw new Exception(String.Format("FLUSSO {0} NON GESTITO", flusso));

            return rVal;
        }
    }
}
