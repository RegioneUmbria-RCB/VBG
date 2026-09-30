using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using KeyValuePair = ProtocolloDocErGestioneDocumentaleService.KeyValuePair;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiTipiDocumento
{
    public class LeggiTipiDocumentoResponseAdapter
    {
        KeyValuePair[] _response;

        public LeggiTipiDocumentoResponseAdapter(KeyValuePair[] response)
        {
            _response = response;
        }

        public ListaTipiDocumentoResponseType Adatta()
        {
            return new ListaTipiDocumentoResponseType
            {
                Documento = _response.Select(x => new ListaTipiDocumentoDocumentoType
                {
                    Codice = x.key,
                    Descrizione = x.value
                }).ToArray()
            };
        }
    }
}
