using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.EProt.TipiDocumento
{
    public class TipiDocumentoResponseAdapter
    {
        public static ListaTipiDocumentoResponseType Adatta(TipiDocumentoListType response)
        {
            var tipiDoc = response.tipoDocumento.Select(x => new ListaTipiDocumentoDocumentoType
            {
                Codice = x.id,
                Descrizione = x.descrizione
            });

            return new ListaTipiDocumentoResponseType { Documento = tipiDoc.ToArray() };
        }
    }
}
