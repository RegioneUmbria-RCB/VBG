using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.EProt.Titolario
{
    public class TitolarioResponseAdapter
    {
        public static ListaTipiClassifica Adatta(TitolarioListType response)
        {
            var tipiDoc = response.titolo.OrderBy(y => y.codice).Select(x => new ListaTipiClassificaClassifica
            {
                Codice = x.id,
                Descrizione = String.Format("{0} - {1}", x.codice, x.descrizione)
            });

            return new ListaTipiClassifica { Classifica = tipiDoc.ToArray() };
        }
    }
}
