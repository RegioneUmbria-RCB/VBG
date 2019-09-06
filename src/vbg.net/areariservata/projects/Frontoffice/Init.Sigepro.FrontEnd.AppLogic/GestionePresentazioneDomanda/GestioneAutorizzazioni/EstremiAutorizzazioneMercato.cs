using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAutorizzazioni
{
	public class EstremiAutorizzazioneMercato
	{
		public class EnteRilascioAutorizzazione
		{
			public readonly string Codice;
			public readonly string Descrizione;

			internal EnteRilascioAutorizzazione(string codice, string descrizione)
			{
				this.Codice = codice;
				this.Descrizione = descrizione;
			}
		}

		public readonly int Id;
		public readonly string Numero;
		public readonly string Data;
		public readonly EnteRilascioAutorizzazione EnteRilascio;
		public readonly string NumeroPresenzeCalcolato;
		public readonly string NumeroPresenzeDichiarato;

		protected EstremiAutorizzazioneMercato (int id,string numero, string data, string codiceEnte, string descrizioneEnte, string numeroPresenzeDichiarato, string numeroPresenzeCalcolato)
		{
			this.Id = id;
			this.Numero = numero;
			this.Data = data;
			this.EnteRilascio = new EnteRilascioAutorizzazione(codiceEnte, descrizioneEnte);
			this.NumeroPresenzeDichiarato = numeroPresenzeDichiarato;
			this.NumeroPresenzeCalcolato = numeroPresenzeCalcolato;
		}

		internal static EstremiAutorizzazioneMercato FromAutorizzazioniMercatiRow(PresentazioneIstanzaDbV2.AutorizzazioniMercatiRow r)
		{
			return new EstremiAutorizzazioneMercato(r.IdAutorizzazione, r.Numero, r.Data, r.CodiceEnte, r.DescrizioneEnte, r.NumeroPresenze, r.NumeroPresenzeCalcolato);
		}
	}
}
