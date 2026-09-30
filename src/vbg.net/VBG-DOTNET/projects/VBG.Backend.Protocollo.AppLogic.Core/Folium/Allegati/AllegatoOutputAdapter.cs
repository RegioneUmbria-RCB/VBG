using VBG.Backend.Protocollo.AppLogic.Core.Folium.ServiceWrapper;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Folium.Allegati
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

        public AllegatoResponseType Adatta()
        {
            var retVal = new List<AllegatoResponseType>();

            var response =_wsWrapper.GetAllegato(_idAllegato);

            return new AllegatoResponseType
            {
                IDBase = response.id.Value.ToString(),
                Serial = response.nomeFile,
                Image = response.contenuto
            };
        }
    }
}
