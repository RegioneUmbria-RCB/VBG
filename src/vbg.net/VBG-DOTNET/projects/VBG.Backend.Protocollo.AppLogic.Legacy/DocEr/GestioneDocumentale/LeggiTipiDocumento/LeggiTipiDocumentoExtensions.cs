using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.LeggiTipiDocumento
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
