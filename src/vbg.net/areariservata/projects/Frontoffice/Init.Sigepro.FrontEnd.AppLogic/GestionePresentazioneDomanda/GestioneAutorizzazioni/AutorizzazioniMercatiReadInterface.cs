using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAutorizzazioni
{
	public interface IAutorizzazioniMercatiReadInterface
	{
		EstremiAutorizzazioneMercato Autorizzazione { get; }
	}

	public class AutorizzazioniMercatiReadInterface : IAutorizzazioniMercatiReadInterface
	{
		PresentazioneIstanzaDbV2 _database;

		
		public AutorizzazioniMercatiReadInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;
		}

		#region IAutorizzazioniMercatiReadInterface Members

		public EstremiAutorizzazioneMercato Autorizzazione
		{
			get 
			{
				if (this._database.AutorizzazioniMercati.Count == 0)
					return null;

				return EstremiAutorizzazioneMercato.FromAutorizzazioniMercatiRow(this._database.AutorizzazioniMercati[0]);
			}
		}

		#endregion
	}
}
