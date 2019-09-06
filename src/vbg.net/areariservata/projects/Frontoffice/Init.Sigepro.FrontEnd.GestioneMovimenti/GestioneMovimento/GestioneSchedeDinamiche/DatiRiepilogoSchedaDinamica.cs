using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche
{
    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class DatiRiepilogoSchedaDinamica
    {
        public int IdScheda { get; set; }
        public string NomeFile { get; set; }
        public int? IdAllegato { get; set; }
        public bool FirmatoDigitalmente { get; set; }

        public DatiRiepilogoSchedaDinamica()
        {
            FirmatoDigitalmente = true;
        }
    }
}
