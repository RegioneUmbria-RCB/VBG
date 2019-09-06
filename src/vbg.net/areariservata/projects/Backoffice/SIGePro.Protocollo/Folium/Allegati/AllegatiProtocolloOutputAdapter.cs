using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Folium.Allegati
{
    public class AllegatiProtocolloOutputAdapter : IAllegatiAdapter
    {
        IAllegatiAdapter[] _adapters;

        public AllegatiProtocolloOutputAdapter(IAllegatiAdapter[] adapters)
        {
            this._adapters = adapters;
        }

        #region IAllegatiAdapter Members

        public IEnumerable<AllOut> Adatta()
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
