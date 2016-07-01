/*using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Folium.Services;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;

namespace Init.SIGePro.Protocollo.Folium.Allegati
{
    public class FoliumAllegatoOutputPrincipaleAdapter : IAllegatiAdapter
    {
        FoliumProtocollazioneService _wsWrapper;
        long _idProtocollo;

        public FoliumAllegatoOutputPrincipaleAdapter(FoliumProtocollazioneService wsWrapper, long idProtocollo)
        {
            this._wsWrapper = wsWrapper;
            this._idProtocollo = idProtocollo;
        }

        #region IAllegatiAdapter Members

        public IEnumerable<AllOut> Adatta()
        {
            var immagineDocumentale = this._wsWrapper.GetAllegatoPrimarioProtocollo(this._idProtocollo);

            var rVal = new List<AllOut>();

            rVal.Add(new AllOut
            {
                IDBase = immagineDocumentale.id.ToString(),
                Serial = immagineDocumentale.nomeFile,
                Commento = String.Empty/*,
                Image = immagineDocumentale.contenuto*//*
            });

            return rVal;
        }

        #endregion
    }
}*/
