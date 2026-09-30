using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Microsis.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazioneMicrosis
    {
        ProtocollazioneArrivoRequest _request;

        public ProtocollazioneArrivo(IDatiProtocollo datiProto, List<IAnagraficaAmministrazione> anagrafiche)
        {
            _request = new ProtocollazioneArrivoRequest(datiProto, anagrafiche);
        }

        public DatiProtocolloResponseType Protocolla(ProtocolloServiceWrapper wrapper)
        {
            var response = wrapper.ProtocollaArrivo(_request);

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = response.Protocollo.Anno,
                DataProtocollo = response.Protocollo.Data_Protocollo.ToString("dd/MM/yyyy"),
                NumeroProtocollo = response.Protocollo.Numero_Protocollo
            };
        }
    }
}
