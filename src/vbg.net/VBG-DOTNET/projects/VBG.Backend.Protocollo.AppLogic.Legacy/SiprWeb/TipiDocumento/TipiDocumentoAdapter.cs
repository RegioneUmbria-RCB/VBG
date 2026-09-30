using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWeb.TipiDocumento
{
    public class TipiDocumentoAdapter
    {
        TipiDocumentoService _wrapper;
        string _filtro;

        public TipiDocumentoAdapter(TipiDocumentoService wrapper, string filtro)
        {
            _wrapper = wrapper;
            _filtro = filtro;
        }

        public ListaTipiDocumentoResponseType Adatta()
        {
            var response = _wrapper.GetTipiDocumento(new TipoDocDocumentoRequest { Chiave = _filtro });
            return new ListaTipiDocumentoResponseType
            {
                Documento = response.Documento.Select(x => new ListaTipiDocumentoDocumentoType
                {
                    Codice = x.CodiceTipoDocumento,
                    Descrizione = x.TipoDocumento
                }).ToArray()
            };
        }
    }
}
