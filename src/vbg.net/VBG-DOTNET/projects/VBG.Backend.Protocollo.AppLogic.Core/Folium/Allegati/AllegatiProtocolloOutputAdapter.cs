using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Folium.Allegati
{
    public class AllegatiProtocolloOutputAdapter : IAllegatiAdapter
    {
        IAllegatiAdapter[] _adapters;

        public AllegatiProtocolloOutputAdapter(IAllegatiAdapter[] adapters)
        {
            this._adapters = adapters;
        }

        #region IAllegatiAdapter Members

        public IEnumerable<AllegatoResponseType> Adatta()
        {
            return this._adapters.SelectMany(x => x.Adatta().Where(y => y != null));
            /*
            var rVal = new List<AllOut>();

            foreach (var adapter in this._adapters)
            {
                var rangeAllegati = adapter.Adatta();
                if (rangeAllegati != null)
                {
                    rVal.AddRange(adapter.Adatta());
                }
            }
            return rVal;
            */
        }

        #endregion
    }
}
