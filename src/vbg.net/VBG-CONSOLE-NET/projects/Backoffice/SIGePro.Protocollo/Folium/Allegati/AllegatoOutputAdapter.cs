using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Folium.ServiceWrapper;

namespace Init.SIGePro.Protocollo.Folium.Allegati
{
    public class AllegatoOutputAdapter
    {
        ProtocollazioneServiceWrapper _wsWrapper;
        long _idAllegato;

        public AllegatoOutputAdapter(ProtocollazioneServiceWrapper wsWrapper, long idAllegato)
        {
            _wsWrapper = wsWrapper;
            _idAllegato = idAllegato;
        }

        public AllOut Adatta()
        {
            var retVal = new List<AllOut>();

            var response =_wsWrapper.GetAllegato(_idAllegato);

            return new AllOut
            {
                IDBase = response.id.Value.ToString(),
                Serial = response.nomeFile,
                Image = response.contenuto
            };
        }
    }
}
