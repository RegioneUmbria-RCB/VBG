using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.Fascicolazione
{
    public class GestioneDocumentaleCercaFascicoliResponseAdapter
    {
        SearchItem[] _response;
        public GestioneDocumentaleCercaFascicoliResponseAdapter(SearchItem[] response)
        {
            _response = response;
        }

        public ListaFascicoliResponseType Adatta()
        {
            if (_response == null)
                return new ListaFascicoliResponseType { Fascicolo = new DatiFascType[] { } };

            var fascicoli = _response.Select(x => x.metadata.ToDatiFascicolo());
            return new ListaFascicoliResponseType { Fascicolo = fascicoli.ToArray() };
            
        }
    }
}
