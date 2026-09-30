using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati
{
	public class DatiIntervento
	{
		public int Codice { get; private set; }
		public string Descrizione { get; private set; }
		public string DescrizioneBreve { get; private set; }

		public DatiIntervento(int codice, string descrizione)
		{
			this.Codice = codice;
			this.Descrizione = descrizione;

			// Descrizione breve
			var listaParti = descrizione.Split(Environment.NewLine.ToCharArray());
			
			this.DescrizioneBreve = listaParti[listaParti.Length - 1];
		}

		public override string ToString()
		{
			return this.DescrizioneBreve;
		}
	}
}
