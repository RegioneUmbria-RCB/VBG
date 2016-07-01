using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs
{
	public class IndirizzoDenunciaTares
	{
		public string CodiceIstatComune { get; set; }
		public string Comune { get; set; }
		public string CodiceVia { get; set; }
		public string Via { get; set; }
		public string Civico { get; set; }
		public string Esponente { get; set; }
		public string Piano { get; set; }
		public string Interno { get; set; }
		public string Frazione { get; set; }
		public string Scala { get; set; }
		public string Cap { get; set; }
		public string Km { get; set; }
		public string Suffisso { get; set; }

		public static IndirizzoDenunciaTares Vuoto()
		{
			return new IndirizzoDenunciaTares(String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, string.Empty);
		}

		[Obsolete("Utilizzare solo per la serializzazione")]
		public IndirizzoDenunciaTares()
		{
		}

		public IndirizzoDenunciaTares(string codiceComune, string comune, string cap, string codiceVia, string via, string civico, string esponente, string fazione, string scala, string piano, string interno, string suffisso, string km)
		{
			this.CodiceIstatComune = codiceComune;
			this.Comune = comune;
			this.Via = via;
			this.CodiceVia = codiceVia;
			this.Civico = civico;
			this.Esponente = esponente;
			this.Frazione = fazione;
			this.Scala = scala;
			this.Piano = piano;
			this.Interno = interno;
			this.Cap = cap;
			this.Suffisso = suffisso;
			this.Km = km;
		}

		public override string ToString()
		{
			var sb = new StringBuilder();

			sb.Append(this.Via);

            if (!String.IsNullOrEmpty(this.Civico) && !String.IsNullOrEmpty(this.Civico.Trim()))
			{
				sb.AppendFormat(" n.{0}", this.Civico);

                if (!String.IsNullOrEmpty(this.Esponente) && !String.IsNullOrEmpty(this.Esponente.Trim()))
				{
                    sb.AppendFormat("/{0}", this.Esponente.Trim());
				}
			}

			if (!String.IsNullOrEmpty(this.Frazione) && !String.IsNullOrEmpty(this.Frazione.Trim()))
			{
				sb.AppendFormat(" fraz {0}", this.Frazione.Trim());
			}

            if (!String.IsNullOrEmpty(this.Scala) && !String.IsNullOrEmpty(this.Scala.Trim()))
			{
                sb.AppendFormat(" scala {0}", this.Scala.Trim());
			}

            if (!String.IsNullOrEmpty(this.Piano) && !String.IsNullOrEmpty(this.Piano.Trim()))
			{
                sb.AppendFormat(" piano {0}", this.Piano.Trim());
			}

            if (!String.IsNullOrEmpty(this.Interno) && !String.IsNullOrEmpty(this.Interno.Trim()))
			{
                sb.AppendFormat(" interno {0}", this.Interno.Trim());
			}

			if (!String.IsNullOrEmpty(this.Km) && !String.IsNullOrEmpty(this.Km.Trim()))
			{
				sb.AppendFormat(" km {0}", this.Interno.Trim());
			}

			return sb.ToString();
		}
	}
}
