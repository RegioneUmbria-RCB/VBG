using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Bari.TARES.DTOs
{
	public class DatiIndirizzoDto
	{
		public string Cap {get;set;}
		public string Comune {get;set;}
		public string Esponente {get;set;}
		public string Frazione {get;set;}
		public string Interno {get;set;}
		public string Km {get;set;}
		public string NumeroCivico {get;set;}
		public string Piano {get;set;}
		public string Scala {get;set;}
		public string Suffisso {get;set;}
		public string Via { get; set; }


		public string NomeViaConCivico
		{
			get
			{
				var indirizzo = new StringBuilder();

				indirizzo.AppendFormat("{0} {1}",Via,NumeroCivico);

				if (!String.IsNullOrEmpty(Esponente))
					indirizzo.AppendFormat("/{0}",Esponente);

				return indirizzo.ToString();
			}
		}

		public override string ToString()
		{
			var indirizzo = new StringBuilder(NomeViaConCivico);			

			if (!String.IsNullOrEmpty(Cap))
				indirizzo.AppendFormat(" - {0}", Cap);
	
			if (!String.IsNullOrEmpty(Comune))
				indirizzo.AppendFormat(" - {0}", Comune);

			return indirizzo.ToString();
		}

		internal static DatiIndirizzoDto Vuoto()
		{
			return new DatiIndirizzoDto
			{
				Cap = String.Empty,
				Comune = String.Empty,
				Esponente = String.Empty,
				Frazione = String.Empty, 
				Interno = String.Empty, 
				Km = String.Empty, 
				NumeroCivico = String.Empty, 
				Piano = String.Empty, 
				Scala = String.Empty, 
				Suffisso = String.Empty, 
				Via = String.Empty
			};
		}
	}
}
