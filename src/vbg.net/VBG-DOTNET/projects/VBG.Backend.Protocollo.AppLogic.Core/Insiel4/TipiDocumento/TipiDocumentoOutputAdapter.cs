using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.TipiDocumento
{
    public class TipiDocumentoOutputAdapter
    {
        ProtocolloService _wrapper;

        public TipiDocumentoOutputAdapter(ProtocolloService wrapper)
        {
            _wrapper = wrapper;
        }

        public ListaTipiDocumentoResponseType Adatta()
        {
            var response = _wrapper.GetTipiDocumento(new TipiDocRequest());
            return new ListaTipiDocumentoResponseType
            {
                Documento = response.Tipi.Select(x => new ListaTipiDocumentoDocumentoType
                {
                    Codice = x.Codice,
                    Descrizione = x.Descrizione
                }).ToArray()
            };
        }
    }
}
