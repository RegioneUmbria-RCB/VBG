using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento
{
    /// <summary>
    /// Allegato di un movimento
    /// </summary>
    public class DatiAllegatoMovimento
    {
        public int IdAllegato { get; set; }
        public string Descrizione { get; set; }
        public string Note { get; set; }
        public bool FirmatoDigitalmente { get; set; }
    }
}
