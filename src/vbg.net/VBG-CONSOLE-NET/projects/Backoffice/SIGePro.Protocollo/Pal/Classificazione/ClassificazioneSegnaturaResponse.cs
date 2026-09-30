using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Classificazione
{
    public class Versione
    {
        public string modelKey { get; set; }
        public int id { get; set; }
        public string descrizione { get; set; }
    }

    public class Result
    {
        public string aliasVersione { get; set; }
        public string dataCessazione { get; set; }
        public string descrizione { get; set; }
        public bool disabilitato { get; set; }
        public int id { get; set; }
        public int livello1 { get; set; }
        public int livello2 { get; set; }
        public int livello3 { get; set; }
        public int livello4 { get; set; }
        public int progressivoPadre { get; set; }
        public Versione versione { get; set; }
    }

    public class RootObject
    {
        public int count { get; set; }
        public string modelKey { get; set; }
        public List<Result> results { get; set; }
    }
}
