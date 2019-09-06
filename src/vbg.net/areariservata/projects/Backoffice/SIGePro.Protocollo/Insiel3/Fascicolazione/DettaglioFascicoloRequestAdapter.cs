using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Fascicolazione
{
    public class DettaglioFascicoloRequestAdapter
    {
        public DettaglioFascicoloRequestAdapter()
        {

        }

        public DettagliPraticaRequest Adatta(long progDoc, string progMovi)
        {
            return new DettagliPraticaRequest
            {
                pratica = new PraticaRequest
                {
                    Item = new IdProtocollo
                    {
                        progDoc = progDoc,
                        progMovi = progMovi
                    }
                }
            };
        }
    }
}
