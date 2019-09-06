using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAutorizzazioni
{
	public interface IAutorizzazioniMercatiWriteInterface
	{
		void SalvaEstremiAutorizzazione(int id,string numero, string data, string codiceEnte, string descrizioneEnte, string numeroPresenzeCalcolato, string numeroPresenzeDichiarato);
	}

	public class AutorizzazioniMercatiWriteInterface : IAutorizzazioniMercatiWriteInterface
	{
		PresentazioneIstanzaDbV2 _database;

		public AutorizzazioniMercatiWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;
		}

		#region IAutorizzazioniMercatiWriteInterface Members

		public void SalvaEstremiAutorizzazione(int id,string numero, string data, string codiceEnte, string descrizioneEnte, string numeroPresenzeCalcolato, string numeroPresenzeDichiarato)
		{
			this._database.AutorizzazioniMercati.Clear();
			this._database.AutorizzazioniMercati.AcceptChanges();
			this._database.AutorizzazioniMercati.AddAutorizzazioniMercatiRow(id,numero, data, codiceEnte, descrizioneEnte, numeroPresenzeDichiarato, numeroPresenzeCalcolato);
		}

		#endregion
	}

}
