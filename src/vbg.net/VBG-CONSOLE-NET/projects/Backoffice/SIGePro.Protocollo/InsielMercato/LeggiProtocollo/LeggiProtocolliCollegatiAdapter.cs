using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService;

namespace Init.SIGePro.Protocollo.InsielMercato.LeggiProtocollo
{
    public class LeggiProtocolliCollegatiAdapter
    {
        public static string Adatta(protocolDetail response)
        {
            if (response.previousList == null || response.previousList.Length == 0)
                return "";

            var valori = response.previousList.Select(x => String.Format("{0}/{1}", x.number, x.year));
            return String.Format("Protocolli Collegati: {0}", String.Join("; ", valori));
        }
    }
}
