using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using ProtocolloInsielService2;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.TipiDocumento
{
    public class TipiDocumentoOutputAdapter
    {
        ProtocolloService _wrapper;

        public TipiDocumentoOutputAdapter(ProtocolloService wrapper)
        {
            _wrapper = wrapper;
        }

        public ListaTipiDocumentoResponseType Adatta(string codiceUtente, string password)
        {
            var response = _wrapper.GetTipiDocumento(new getTipiDocRequest
            {
                Utente = new Utente
                {
                    codice = codiceUtente,
                    password = password
                }
            });

            return new ListaTipiDocumentoResponseType
            {
                Documento = response.Items.Select(x => new ListaTipiDocumentoDocumentoType
                {
                    Codice = ((ProtocolloInsielService2.TipiDocumento)x).codice,
                    Descrizione = ((ProtocolloInsielService2.TipiDocumento)x).descrizione
                }).ToArray()
            };
        }
    }
}
