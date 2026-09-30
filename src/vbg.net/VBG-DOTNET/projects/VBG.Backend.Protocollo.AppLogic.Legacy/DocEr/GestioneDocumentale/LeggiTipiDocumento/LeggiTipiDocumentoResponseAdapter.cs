using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.LeggiTipiDocumento
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
