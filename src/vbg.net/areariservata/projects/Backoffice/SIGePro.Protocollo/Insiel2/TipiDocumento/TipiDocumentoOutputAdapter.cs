using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Insiel2.Services;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Insiel2.TipiDocumento
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
            var response = _wrapper.GetTipiDocumento(new getTipiDocRequest
            {
                Utente = new Utente
                {
                    codice = codiceUtente,
                    password = password
                }
            });

            return new ListaTipiDocumento
            {
                Documento = response.Items.Select(x => new ListaTipiDocumentoDocumento
                {
                    Codice = ((ProtocolloInsielService2.TipiDocumento)x).codice,
                    Descrizione = ((ProtocolloInsielService2.TipiDocumento)x).descrizione
                }).ToArray()
            };
        }
    }
}
