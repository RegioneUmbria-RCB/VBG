using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.DocEr.Fascicolazione;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Fascicolazione
{
    public class GestioneDocumentaleCercaFascicoliResponseAdapter
    {
        SearchItem[] _response;
        public GestioneDocumentaleCercaFascicoliResponseAdapter(SearchItem[] response)
        {
            _response = response;
        }

        public ListaFascicoli Adatta()
        {
            if (_response == null)
                return new ListaFascicoli { Fascicolo = new DatiFasc[] { } };

            var fascicoli = _response.Select(x => x.metadata.ToDatiFascicolo());
            return new ListaFascicoli { Fascicolo = fascicoli.ToArray() };
            
        }
    }
}
