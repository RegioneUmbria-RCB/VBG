using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Folium.Services;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Folium.Allegati
{
    public class FoliumAllegatoOutputAdapter : IAllegatiAdapter
    {
        FoliumProtocollazioneService _wsWrapper;
        long _idAllegato;

        public FoliumAllegatoOutputAdapter(FoliumProtocollazioneService wsWrapper, long idAllegato)
        {
            _wsWrapper = wsWrapper;
            _idAllegato = idAllegato;
        }

        #region IAllegatiAdapter Members

        public IEnumerable<AllOut> Adatta()
        {
            var retVal = new List<AllOut>();

            var response =_wsWrapper.GetAllegato(_idAllegato);

            retVal.Add(new AllOut
            {
                IDBase = response.id.Value.ToString(),
                Serial = response.nomeFile,
                Image = response.contenuto
            });

            return retVal;

        }

        #endregion
    }
}
