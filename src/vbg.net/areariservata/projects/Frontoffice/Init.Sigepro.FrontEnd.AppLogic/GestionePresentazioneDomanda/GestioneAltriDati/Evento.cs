using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati
{
	public class Evento
	{
		public string Codice { get; private set; }
		public string Descrizione { get; private set; }
		public DateTime Data { get; private set; }

		public Evento(string codice, string descrizione, DateTime data)
		{
			this.Codice = codice;
			this.Descrizione = descrizione;
			this.Data = data;
		}
	}
}
