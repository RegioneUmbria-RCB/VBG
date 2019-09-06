using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.SiprWebTest.TipiDocumento
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

        public ListaTipiDocumento Adatta()
        {
            var response = _wrapper.GetTipiDocumento(new TipoDocDocumentoRequest { Chiave = _filtro });
            return new ListaTipiDocumento
            {
                Documento = response.Documento.Select(x => new ListaTipiDocumentoDocumento
                {
                    Codice = x.CodiceTipoDocumento,
                    Descrizione = x.TipoDocumento
                }).ToArray()
            };
        }
    }
}
