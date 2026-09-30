using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo.SegnaturaResponse;

namespace Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo
{
    public class LeggiProtocolloResponseValidation
    {
        public LeggiProtocolloResponseValidation()
        {
            
        }

        public static void Validate(RisultatoRicerca response)
        {
            try
            {
                if (response == null)
                    throw new Exception("LA RISPOSTA NON E' STATA VALORIZZATA (VALORE NULL)");

                if (response.Documento == null || response.Documento.Length == 0)
                    throw new Exception("LA RICERCA NON HA INDIVIDUATO NESSUN PROTOCOLLO");

                if (response.Documento.Length > 1)
                    throw new Exception("LA RICERCA HA TROVATO PIU' DI UN PROTOCOLLO CON I PARAMETRI INDICATI");
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA VALIDAZIONE DEI DATI DI RISPOSTA, {0}", ex.Message), ex);
            }
        }
    }
}
