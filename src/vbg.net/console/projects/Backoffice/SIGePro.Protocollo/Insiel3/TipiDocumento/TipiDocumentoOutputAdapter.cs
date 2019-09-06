using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Insiel3.TipiDocumento
{
    public class TipiDocumentoOutputAdapter
    {
        ProtocolloService _wrapper;

        public TipiDocumentoOutputAdapter(ProtocolloService wrapper)
        {
            _wrapper = wrapper;
        }

        public ListaTipiDocumento Adatta(string codiceUtente, string password)
        {
            var response = _wrapper.GetTipiDocumento(new TipiDocRequest());
            return new ListaTipiDocumento
            {
                Documento = response.Items.Select(x => new ListaTipiDocumentoDocumento
                {
                    Codice = ((ProtocolloInsielService3.TipoDocumento)x).codice,
                    Descrizione = ((ProtocolloInsielService3.TipoDocumento)x).descrizione
                }).ToArray()
            };
        }
    }
}
