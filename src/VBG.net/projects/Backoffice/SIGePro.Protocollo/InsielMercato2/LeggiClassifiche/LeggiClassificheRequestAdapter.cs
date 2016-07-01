using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Protocollo.InsielMercato2.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.InsielMercato2.LeggiClassifiche
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
