using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService;
using Init.SIGePro.Protocollo.InsielMercato.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.InsielMercato.LeggiClassifiche
{
    public class LeggiClassificheRequestAdapter
    {
        public static filingRequest Adatta()
        {
            return new filingRequest
            {
                filing = new filing
                {
                    disabled = false,
                    remove = false
                }
            };
        }
    }
}
