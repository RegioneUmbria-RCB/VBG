using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.RicercaPratiche
{


    public class AnagraficaIstanzaTrovata
    {
        public class TipoSoggettoAnagraficaTrovata
        {
            public int Codice { get; set; } = -1;
            public string Descrizione { get; set; } = "";
        }

        public class ValoreDatoAnagrafica
        {
            public string Chiave { get; set; }
            public string Valore { get; set; }

            public bool IsDateTime { get; set; }
        }

        public List<ValoreDatoAnagrafica> Proprieta { get; set; } = new List<ValoreDatoAnagrafica>();
        public TipoSoggettoAnagraficaTrovata TipoSoggetto { get; set; }
    }
}
