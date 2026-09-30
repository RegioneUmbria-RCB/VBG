using ProtocolloDocErGestioneDocumentaleService;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.Classifiche
{
    public class LeggiTitolarioResponseAdapter
    {
        private static class Constants
        {
            public const string KEY_CODICE = "CLASSIFICA";
            public const string KEY_DESCRIZIONE = "DESCRIZIONE";
        }

        SearchItem[] _response;

        public LeggiTitolarioResponseAdapter(SearchItem[] response)
        {
            _response = response;
        }

        public ListaTipiClassificaType Adatta()
        {
            var classifiche = _response.Select(x => x.ToListaClassificaClassifica());
            return new ListaTipiClassificaType { Classifica = classifiche.ToArray() };
        }
    }
}
