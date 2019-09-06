using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Organigramma
{
    public class Modello
    {
        public string modelKey { get; set; }
        public int id { get; set; }
        public string descrizione { get; set; }
    }

    public class Ente : Modello
    {
        public string codiceComune { get; set; }
    }

    public class Result
    {
        public string alias { get; set; }
        public Modello aoo { get; set; }
        public string dataFine { get; set; }
        public string dataInizio { get; set; }
        public string descrizione { get; set; }
        public Ente ente { get; set; }
        public string id { get; set; }
        public string livello1 { get; set; }
        public string livello2 { get; set; }
        public string livello3 { get; set; }
        public string livello4 { get; set; }
        public Modello mockResponsabile { get; set; }
        public Modello modelloOrganizzativo { get; set; }
    }

    public class RootObjectOrganigramma
    {
        public int count { get; set; }
        public string modelKey { get; set; }
        public List<Result> results { get; set; }
    }
}
