using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;

namespace Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo
{
    public class ClassificaResponseAdapter
    {
        public class Classifica
        {
            public string Codice { get; private set; }
            public string Descrizione { get; private set; }

            public Classifica(string codice = "", string descrizione = "")
            {
                Codice = codice;
                Descrizione = descrizione;
            }
        }

        public static Classifica Adatta(protocolDetail response)
        {
            if (response.filingList != null && response.filingList.Length > 0)
                return new Classifica(response.filingList[0].code, String.Format("({0}) {1}", response.filingList[0].code, response.filingList[0].description));

            return new Classifica();
        }
    }
}
