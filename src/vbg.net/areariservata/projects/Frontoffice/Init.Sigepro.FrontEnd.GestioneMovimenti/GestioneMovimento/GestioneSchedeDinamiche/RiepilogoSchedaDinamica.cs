using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche
{
    public class RiepilogoSchedaDinamica
    {
        public int IdScheda { get; set; }
        public string NomeScheda { get; set; }
        public string NomeFile { get; set; }
        public int? CodiceOggetto { get; set; }
        public bool FirmatoDigitalmente { get; set; }

        public RiepilogoSchedaDinamica(int idScheda, string nomeScheda, DatiRiepilogoSchedaDinamica datiRiepilogo)
        {
            this.IdScheda = idScheda;
            this.NomeScheda = nomeScheda;

            if (datiRiepilogo != null)
            {
                this.NomeFile = datiRiepilogo.NomeFile;
                this.CodiceOggetto = datiRiepilogo.IdAllegato;
                this.FirmatoDigitalmente = datiRiepilogo.FirmatoDigitalmente;
            }
            else
            {
                this.NomeFile = String.Empty;
                this.CodiceOggetto = (int?)null;
            }
        }
    }
}
