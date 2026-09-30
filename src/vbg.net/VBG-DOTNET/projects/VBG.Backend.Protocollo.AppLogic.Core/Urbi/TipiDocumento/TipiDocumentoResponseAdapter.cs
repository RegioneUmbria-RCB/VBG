using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.TipiDocumento
{
    public class TipiDocumentoResponseAdapter
    {
        public static ListaTipiDocumentoResponseType Adatta(xapirestTypeTipiDocumento response)
        {
            var retVal = new ListaTipiDocumentoResponseType
            {
                Documento = response.getElencoTipiDocumento_Result.SEQ_Documento.Select(x => new ListaTipiDocumentoDocumentoType
                {
                    Codice = x.Descrizione,
                    Descrizione = x.Descrizione
                }).ToArray()
            };

            return retVal;
        }
    }
}
