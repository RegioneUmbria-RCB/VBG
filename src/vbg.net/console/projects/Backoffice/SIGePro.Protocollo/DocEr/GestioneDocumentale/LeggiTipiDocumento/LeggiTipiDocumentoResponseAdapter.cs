using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiTipiDocumento
{
    public class LeggiTipiDocumentoResponseAdapter
    {
        KeyValuePair[] _response;

        public LeggiTipiDocumentoResponseAdapter(KeyValuePair[] response)
        {
            _response = response;
        }

        public ListaTipiDocumento Adatta()
        {
            return new ListaTipiDocumento
            {
                Documento = _response.Select(x => new ListaTipiDocumentoDocumento
                {
                    Codice = x.key,
                    Descrizione = x.value
                }).ToArray()
            };
        }
    }
}
