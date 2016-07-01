using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Folium.Services;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Folium.Allegati
{
    public class FoliumAltriAllegatiOutputAdapter : IAllegatiAdapter
    {
        FoliumProtocollazioneService _wsWrapper;
        long _idProtocollo;

        public FoliumAltriAllegatiOutputAdapter(FoliumProtocollazioneService wsWrapper, long idProtocollo)
        {
            this._wsWrapper = wsWrapper;
            this._idProtocollo = idProtocollo;
        }

        #region IAllegatiAdapter Members

        public IEnumerable<AllOut> Adatta()
        {
            return this._wsWrapper.LeggiAllegati(this._idProtocollo).Select(x => new AllOut
            {
                IDBase = x.id.ToString(),
                Serial = x.nomeFile,
                Commento = x.descrizione,
                /*Image = x.contenuto*/
            });
        }

        #endregion
    }
}
