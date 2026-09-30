using VBG.Backend.Protocollo.AppLogic.Core.Folium.ServiceWrapper;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Folium.Classifiche
{
    public class ClassificheOutputAdapter : IClassificheAdapter
    {
        ProtocollazioneServiceWrapper _wsWrapper;

        public ClassificheOutputAdapter(ProtocollazioneServiceWrapper wsWrapper)
        {
            _wsWrapper = wsWrapper;
        }

        #region IClassificheAdapter Members

        public IEnumerable<ListaTipiClassificaClassifica> Adatta()
        {
            return _wsWrapper.GetClassifiche().Select(x => new ListaTipiClassificaClassifica
            {
                Codice = x.codice,
                Descrizione = x.descrizione
            });
        }

        #endregion
    }
}
