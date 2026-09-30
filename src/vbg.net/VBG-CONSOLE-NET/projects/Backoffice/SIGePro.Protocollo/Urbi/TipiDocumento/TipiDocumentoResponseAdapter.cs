using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.TipiDocumento
{
    public class TipiDocumentoResponseAdapter
    {
        public static ListaTipiDocumento Adatta(xapirestTypeTipiDocumento response)
        {
            var retVal = new ListaTipiDocumento
            {
                Documento = response.getElencoTipiDocumento_Result.SEQ_Documento.Select(x => new ListaTipiDocumentoDocumento
                {
                    Codice = x.Descrizione,
                    Descrizione = x.Descrizione
                }).ToArray()
            };

            return retVal;
        }
    }
}
