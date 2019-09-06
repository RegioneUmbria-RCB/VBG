using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Folium.ServiceWrapper;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Folium.Allegati
{
    public class AltriAllegatiOutputAdapter : IAllegatiAdapter
    {
        ProtocollazioneServiceWrapper _wsWrapper;
        long _idProtocollo;

        public AltriAllegatiOutputAdapter(ProtocollazioneServiceWrapper wsWrapper, long idProtocollo)
        {
            this._wsWrapper = wsWrapper;
            this._idProtocollo = idProtocollo;
        }

        #region IAllegatiAdapter Members

        public IEnumerable<AllOut> Adatta()
        {
            var allegati = this._wsWrapper.LeggiAllegati(this._idProtocollo);

            if (allegati == null)
            {
                return Enumerable.Empty<AllOut>();
            }

            return allegati.Select(x => new AllOut
            {
                IDBase = x.id.ToString(),
                Serial = x.nomeFile,
                Commento = $"{x.descrizione} ({x.nomeFile})",
            });
        }

        #endregion
    }
}
