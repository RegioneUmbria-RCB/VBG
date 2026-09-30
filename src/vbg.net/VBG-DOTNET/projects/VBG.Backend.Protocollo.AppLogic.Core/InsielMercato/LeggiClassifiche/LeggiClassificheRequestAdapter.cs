using ProtocolloInsielMercatoService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.LeggiClassifiche
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
