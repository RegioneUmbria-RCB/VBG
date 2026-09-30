using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Protocollazione
{
    public class ProtocollazioneResponseAdapter
    {
        public static DatiProtocolloResponseType Adatta(xapirestTypeInsProtocollo response, string warnings)
        {
            var provider = CultureInfo.InvariantCulture;
            var dt = DateTime.ParseExact(response.insProtocollo_Result.DataProtocollo, "dd-MM-yyyy", provider);

            return new DatiProtocolloResponseType
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
