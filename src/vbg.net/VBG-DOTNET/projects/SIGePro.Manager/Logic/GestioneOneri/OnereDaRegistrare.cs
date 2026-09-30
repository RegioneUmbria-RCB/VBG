using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneOneri
{
    public class OnereDaRegistrare
    {
        public readonly int CodiceIstanza;
        public readonly int IdCausale;
        public readonly double Importo;
        public readonly DateTime? Scadenza;
        public readonly int NumeroRata;


        public OnereDaRegistrare(int codiceIstanza, int idCausale, double importo, DateTime? scadenza = null, int numeroRata = 1)
        {
            this.CodiceIstanza = codiceIstanza;
            this.IdCausale = idCausale;
            this.Importo = importo;
            this.Scadenza = scadenza;
            this.NumeroRata = numeroRata;
        }
    }
}
