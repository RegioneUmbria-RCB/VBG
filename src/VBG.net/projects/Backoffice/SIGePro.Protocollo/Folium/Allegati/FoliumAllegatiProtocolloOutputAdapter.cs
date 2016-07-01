using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Folium.Allegati
{
    public class FoliumAllegatiProtocolloOutputAdapter : IAllegatiAdapter
    {
        IAllegatiAdapter[] _adapters;

        public FoliumAllegatiProtocolloOutputAdapter(IAllegatiAdapter[] adapters)
        {
            this._adapters = adapters;
        }

        #region IAllegatiAdapter Members

        public IEnumerable<AllOut> Adatta()
        {
            var rVal = new List<AllOut>();

            foreach (var adapter in this._adapters)
                rVal.AddRange(adapter.Adatta());

            return rVal;
        }

        #endregion
    }
}
