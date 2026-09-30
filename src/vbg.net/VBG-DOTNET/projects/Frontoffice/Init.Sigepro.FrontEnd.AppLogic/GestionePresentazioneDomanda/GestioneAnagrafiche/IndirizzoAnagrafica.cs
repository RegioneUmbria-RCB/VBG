using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using System;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    public class IndirizzoAnagraficaDomanda
    {
        public string Via { get; private set; }
        public string Citta { get; private set; }
        public string Cap { get; private set; }
        public string SiglaProvincia { get; private set; }
        public string CodiceComune { get; private set; }

        public IndirizzoAnagraficaDomanda(string via, string citta, string cap, string siglaProvincia, string codiceComune)
        {
            this.Via = via;
            this.Citta = citta;
            this.Cap = cap;
            this.SiglaProvincia = siglaProvincia;
            this.CodiceComune = codiceComune;
        }

        //TODO: implementare le logiche di confronto uguaglianza

        public override string ToString()
        {
            return this.ToString(null);
        }

        public string ToString(IComuniService comuniService)
        {
            var str = new StringBuilder(this.Via);

            if (!String.IsNullOrEmpty(this.Via) && !String.IsNullOrEmpty(this.Cap))
            {
                str.Append(" - ");
            }

            if (!String.IsNullOrEmpty(this.Cap))
            {
                str.Append(this.Cap);
                str.Append(", ");
            }

            if (!String.IsNullOrEmpty(this.Citta))
            {
                str.Append(this.Citta).Append(" ");
            }

            if (comuniService != null && !String.IsNullOrEmpty(this.CodiceComune))
            {
                var comune = comuniService.GetByCodiceComune(this.CodiceComune);

                str.Append($"{comune.Comune} ({comune.SiglaProvincia})");
            }

            return str.ToString();
        }
    }
}
