using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Allegati
{
    public class DownloadDocumentoResponseAdapter
    {
        GestioneDocumentaleService _wrapper;
        string _idUnitaDocumentale;

        public DownloadDocumentoResponseAdapter(GestioneDocumentaleService wrapper, string idUnitaDocumentale)
        {
            _wrapper = wrapper;
            _idUnitaDocumentale = idUnitaDocumentale;
        }

        public AllOut Adatta()
        {
            var download = _wrapper.DownloadDocument(_idUnitaDocumentale);
            var res = _idUnitaDocumentale.ToAllOut(_wrapper);
            res.Image = download.handler;

            return res;
        }
    }
}
