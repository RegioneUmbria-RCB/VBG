using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Folium.ServiceWrapper;

namespace Init.SIGePro.Protocollo.Folium.Classifiche
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
