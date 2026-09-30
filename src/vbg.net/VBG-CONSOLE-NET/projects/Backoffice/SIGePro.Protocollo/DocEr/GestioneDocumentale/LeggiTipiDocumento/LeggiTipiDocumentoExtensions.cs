using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiTipiDocumento
{
    public static class LeggiTipiDocumentoExtensions
    {
        public static ListaTipiDocumentoDocumento ToListaTipiDocumentoDocumento(this KeyValuePair item)
        {
            return new ListaTipiDocumentoDocumento
            {
                Codice = item.key,
                Descrizione = item.value
            };
        }
    }
}
