using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche
{
    /// <summary>
    /// Dati di una scheda dinamica associata ad un movimento
    /// </summary>
    public class SchedaDinamicaMovimento
    {
        public int IdScheda { get; set; }
        public string NomeScheda { get; set; }

        private bool _compilata = false;
        public bool Compilata { get { return _compilata; } set { _compilata = value; } }

        public List<ValoreSchedaDinamicaMovimento> Valori { get; set; }
        public List<int> IdCampiDinamiciContenuti { get; set; }

        public SchedaDinamicaMovimento()
        {
            this.Valori = new List<ValoreSchedaDinamicaMovimento>();
            this.IdCampiDinamiciContenuti = new List<int>();
        }
    }
}
