using Init.Sigepro.FrontEnd.AppLogic.ConnectedServices.SIC;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC
{
    public class BodyItem
    {
        public string Chiave { get; set; }
        public string Valore { get; set; }
    }

    public class GeneraURLMappaResponse
    {
        public string Url { get; set; }
        public bool UsaPost {  get; set; }

        public IEnumerable<BodyItem> Body { get; set; }

        public string Errore { get; set; }

        internal static GeneraURLMappaResponse FromKO(string errore)
        {
            return new GeneraURLMappaResponse
            {
                Url = String.Empty,
                Errore = errore
            };
        }

        internal static GeneraURLMappaResponse FromOK(InnescoResponse response)
        {
            return new GeneraURLMappaResponse
            {
                Url = response.Url,
                UsaPost = response.Method == InnescoResponseMethod.POST,
                Body = response.Body?.Select(x => new BodyItem { Chiave = x.Chiave, Valore = x.Valore } ),
                Errore = String.Empty
            };
        }
    }
}
