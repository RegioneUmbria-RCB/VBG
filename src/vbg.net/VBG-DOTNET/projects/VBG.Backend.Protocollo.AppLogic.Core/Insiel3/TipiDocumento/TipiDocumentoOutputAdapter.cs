using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.TipiDocumento
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
                Documento = response.Items.Select(x => new ListaTipiDocumentoDocumentoType
                {
                    Codice = ((ProtocolloInsielService3.TipoDocumento)x).codice,
                    Descrizione = ((ProtocolloInsielService3.TipoDocumento)x).descrizione
                }).ToArray()
            };
        }
    }
}
