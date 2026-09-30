using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneStradario
{
    public class DatiLocalizzazione
    {
        public string Indirizzo { get; set; }
        public string Civico { get; set; }
        public string Esponente { get; set; }
        public string Colore { get; set; }
        public string Km { get; set; }
        public string TipoCatasto { get; set; }
        public string Sezione { get; set; }
        public string Foglio { get; set; }
        public string Particella { get; set; }
        public string Sub { get; set; }

        public bool HaDatiCatastali => !String.IsNullOrEmpty(Sezione) || !String.IsNullOrEmpty(Foglio) ||
                !String.IsNullOrEmpty(Particella) || !String.IsNullOrEmpty(Sub);
    }

    public class StradarioFormatter
    {
        public string GeneraStringaIndirizzo(DatiLocalizzazione loc)
        {
            var sb = new StringBuilder();

            sb.Append(loc.Indirizzo);

            if (!String.IsNullOrEmpty(loc.Civico))
            {
                sb.Append(" ").Append(loc.Civico);
            }
            if (!String.IsNullOrEmpty(loc.Esponente))
            {
                sb.Append("/").Append(loc.Esponente);
            }
            if (!String.IsNullOrEmpty(loc.Colore))
            {
                sb.Append("/").Append(loc.Colore);
            }
            if (!String.IsNullOrEmpty(loc.Km))
            {
                sb.Append(" Km.").Append(loc.Km);
            }
            if (loc.HaDatiCatastali)
            {
                sb.Append(" (");
                var separatore = "";

                if (!String.IsNullOrEmpty(loc.TipoCatasto))
                {
                    sb.Append(loc.TipoCatasto).Append(" ");
                }

                if (!String.IsNullOrEmpty(loc.Sezione))
                {
                    sb.Append("Sez.").Append(loc.Sezione);
                    separatore = ", ";
                }

                if (!String.IsNullOrEmpty(loc.Foglio))
                {
                    sb.Append(separatore).Append("F.").Append(loc.Foglio);
                    separatore = ", ";
                }

                if (!String.IsNullOrEmpty(loc.Particella))
                {
                    sb.Append(separatore).Append("P.").Append(loc.Particella);
                    separatore = ", ";
                }

                if (!String.IsNullOrEmpty(loc.Sub))
                {
                    sb.Append(separatore).Append("S.").Append(loc.Sub);
                }

                sb.Append(")");
            }

            return sb.ToString();
        }

    }
}
