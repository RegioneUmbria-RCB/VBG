using ProtocolloInsielMercatoService2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.LeggiClassifiche
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
