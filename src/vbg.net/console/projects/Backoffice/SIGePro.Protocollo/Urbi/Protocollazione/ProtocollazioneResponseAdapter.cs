using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.Protocollazione
{
    public class ProtocollazioneResponseAdapter
    {
        public static DatiProtocolloRes Adatta(xapirestTypeInsProtocollo response, string warnings)
        {
            var provider = CultureInfo.InvariantCulture;
            var dt = DateTime.ParseExact(response.insProtocollo_Result.DataProtocollo, "dd-MM-yyyy", provider);

            return new DatiProtocolloRes
            {
                AnnoProtocollo = response.insProtocollo_Result.Anno,
                DataProtocollo = dt.ToString("dd/MM/yyyy"),
                IdProtocollo = response.insProtocollo_Result.IdProto,
                NumeroProtocollo = response.insProtocollo_Result.Numero,
                Warning = warnings
            };
        }
    }
}
