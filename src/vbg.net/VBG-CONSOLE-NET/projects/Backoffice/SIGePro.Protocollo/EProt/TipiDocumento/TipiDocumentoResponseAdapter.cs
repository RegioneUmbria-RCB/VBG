using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.EProt.TipiDocumento
{
    public class TipiDocumentoResponseAdapter
    {
        public static ListaTipiDocumento Adatta(TipiDocumentoListType response)
        {
            var tipiDoc = response.tipoDocumento.Select(x => new ListaTipiDocumentoDocumento
            {
                Codice = x.id,
                Descrizione = x.descrizione
            });

            return new ListaTipiDocumento { Documento = tipiDoc.ToArray() };
        }
    }
}
