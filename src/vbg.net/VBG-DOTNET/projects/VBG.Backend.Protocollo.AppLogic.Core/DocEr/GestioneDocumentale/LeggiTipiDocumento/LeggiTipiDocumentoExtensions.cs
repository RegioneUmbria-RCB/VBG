using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using KeyValuePair = ProtocolloDocErGestioneDocumentaleService.KeyValuePair;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiTipiDocumento
{
    public static class LeggiTipiDocumentoExtensions
    {
        public static ListaTipiDocumentoDocumentoType ToListaTipiDocumentoDocumento(this KeyValuePair item)
        {
            return new ListaTipiDocumentoDocumentoType
            {
                Codice = item.key,
                Descrizione = item.value
            };
        }
    }
}
