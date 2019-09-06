using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Classifiche
{
    public static class LeggiTitolarioResponseExtensions
    {
        private static class Constants
        {
            public const string KEY_CODICE = "CLASSIFICA";
            public const string KEY_DESCRIZIONE = "DES_TITOLARIO";
        }

        public static ListaTipiClassificaClassifica ToListaClassificaClassifica(this SearchItem item)
        {
            var dic = item.metadata.ToDictionary(x => x.key, y => y.value);

            return new ListaTipiClassificaClassifica
            {
                Codice = dic[Constants.KEY_CODICE],
                Descrizione = String.Format("{0} - {1}", dic[Constants.KEY_CODICE], dic[Constants.KEY_DESCRIZIONE])
            };
        }
    }
}
