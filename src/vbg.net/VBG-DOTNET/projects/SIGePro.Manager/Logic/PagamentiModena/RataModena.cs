using System;

namespace Init.SIGePro.Manager.Logic.PagamentiModena
{
    public class RataModena
    {
        public readonly double Importo;
        public readonly DateTime DataScadenza;
        public readonly int NumeroRata;

        public RataModena(int numeroRata, double importo, DateTime dataScadenza)
        {
            this.NumeroRata = numeroRata;
            Importo = importo;
            DataScadenza = dataScadenza;
        }
    }
}