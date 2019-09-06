using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Classifiche
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

        public ListaTipiClassifica Adatta()
        {
            var classifiche = _response.Select(x => x.ToListaClassificaClassifica());
            return new ListaTipiClassifica { Classifica = classifiche.ToArray() };
        }
    }
}
