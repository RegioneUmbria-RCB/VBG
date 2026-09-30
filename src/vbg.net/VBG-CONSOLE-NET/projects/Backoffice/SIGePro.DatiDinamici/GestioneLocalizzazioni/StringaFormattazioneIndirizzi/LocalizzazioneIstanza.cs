using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi
{
	public class LocalizzazioneIstanza
	{
		public class Coordinata
		{
			public string Latitudine { get; set; }
			public string Longitudine { get; set; }

			public Coordinata(){}
			public Coordinata(string longitudine, string latitudine) { this.Longitudine = longitudine; this.Latitudine = latitudine; }

			public override string ToString()
			{
				return String.Format("lat: {0}, long: {1}", Latitudine, Longitudine);
			}
		}

		public class RiferimentiCatastali
		{
			public string TipoCatasto { get; set; }
			public string Foglio { get; set; }
			public string Particella { get; set; }
			public string Sub { get; set; }

			public RiferimentiCatastali()
			{
			}

			public RiferimentiCatastali(string tipoCatasto, string foglio, string particella, string sub)
			{
				this.TipoCatasto = tipoCatasto;
				this.Foglio = foglio;
				this.Particella = particella;
				this.Sub = sub;
			}

			public override string ToString()
			{
				return String.Format("catasto {0}, f: {1}, p: {2}, s: {3}", TipoCatasto, Foglio, Particella, Sub);
			}
		}

		public string Uuid { get; set; }
		public string Indirizzo { get; set; }
		public string Civico { get; set; }
		public string Esponente { get; set; }
		public string Scala { get; set; }
		public string Piano { get; set; }
		public string Interno { get; set; }
		public string EsponenteInterno { get; set; }
		public string Km { get; set; }
		public string Note { get; set; }
		public string TipoLocalizzazione { get; set; }
		public Coordinata Coordinate{get;set;}
		public RiferimentiCatastali Mappali { get; set; }

		public string ToString(string espressioneFormattazione)
		{
			if (String.IsNullOrEmpty(espressioneFormattazione))
				espressioneFormattazione = "{indirizzo} {civico}";

			var formatProvider = new LocalizzazioneFormatProvider(this);

			return formatProvider.Format(espressioneFormattazione);
		}

	}
}
