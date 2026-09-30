using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Microsis.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazione
    {
        ProtocollazioneArrivoRequest _request;

        public ProtocollazioneArrivo(IDatiProtocollo datiProto, List<IAnagraficaAmministrazione> anagrafiche)
        {
            _request = new ProtocollazioneArrivoRequest(datiProto, anagrafiche);
        }

        public DatiProtocolloRes Protocolla(ProtocolloServiceWrapper wrapper)
        {
            var response = wrapper.ProtocollaArrivo(_request);

            return new DatiProtocolloRes
            {
                AnnoProtocollo = response.Protocollo.Anno,
                DataProtocollo = response.Protocollo.Data_Protocollo.ToString("dd/MM/yyyy"),
                NumeroProtocollo = response.Protocollo.Numero_Protocollo
            };
        }
    }
}
