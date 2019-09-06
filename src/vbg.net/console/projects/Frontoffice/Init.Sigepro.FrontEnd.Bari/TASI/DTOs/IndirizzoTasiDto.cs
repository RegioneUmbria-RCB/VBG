using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Bari.TASI.DTOs
{
	public class IndirizzoTasiDto
	{
		public string CodiceVia { get; set; }
		public string Via { get; set; }
		public string Civico { get; set; }
		public string Esponente { get; set; }
		public string Palazzina { get; set; }
		public string Scala { get; set; }
		public string Piano { get; set; }
		public string Interno { get; set; }
		public string Cap { get; set; }
		public string Suffisso { get; set; }

		public static IndirizzoTasiDto Vuoto()
		{
			return new IndirizzoTasiDto(String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty);
		}

		[Obsolete("Utilizzare solo per la serializzazione")]
		public IndirizzoTasiDto()
		{
		}

		public IndirizzoTasiDto(string cap, string codiceVia, string via, string civico, string esponente, string palazzina, string scala, string piano, string interno, string suffisso)
		{
			this.Via = via;
			this.CodiceVia = codiceVia;
			this.Civico = civico;
			this.Esponente = esponente;
			this.Palazzina = palazzina;
			this.Scala = scala;
			this.Piano = piano;
			this.Interno = interno;
			this.Cap = cap;
			this.Suffisso = suffisso;
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

            if (!String.IsNullOrEmpty(this.Palazzina) && !String.IsNullOrEmpty(this.Palazzina.Trim()))
			{
                sb.AppendFormat(" pal {0}", this.Palazzina.Trim());
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

			return sb.ToString();
		}
	}
}
