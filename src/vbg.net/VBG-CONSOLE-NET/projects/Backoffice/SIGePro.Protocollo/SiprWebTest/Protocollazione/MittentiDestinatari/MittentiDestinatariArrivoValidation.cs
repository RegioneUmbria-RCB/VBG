using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.SiprWebTest.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariArrivoValidation : BaseMittentiDestinatari
    {
        public MittentiDestinatariArrivoValidation(IDatiProtocollo datiProto) : base(datiProto)
        {
            
        }

        public void ValidaMittente()
        {
            try
            {
                if (DatiProto.AnagraficheProtocollo.Count + DatiProto.AmministrazioniProtocollo.Count > 1)
                    throw new Exception("E' POSSIBILE INSERIRE SOLAMENTE UN MITTENTE");

                if (DatiProto.AmministrazioniInterne.Count > 0)
                    throw new Exception("E' STATA INSERITA UN'AMMINISTRAZIONE INTERNA, USARE IL FLUSSO INTERNO");
            }
            catch (Exception ex)
            {
                throw new Exception("VALIDAZIONE DEI DATI RIGUARDANTI IL MITTENTE NON SUPERATA", ex);
            }
        }
    }
}
