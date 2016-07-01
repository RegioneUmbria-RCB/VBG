using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni
{
	public class LocalizzazioniReadInterface : ILocalizzazioniReadInterface
	{
		PresentazioneIstanzaDbV2 _database;
		IEnumerable<IndirizzoStradario> _indirizzi;

		public LocalizzazioniReadInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;

			PreparaIndirizzi();
		}

		private void PreparaIndirizzi()
		{
			_indirizzi = this._database.ISTANZESTRADARIO.Select(x => IndirizzoStradario.FromStradarioRow(x, this._database.DATICATASTALI));
		}
		

		#region ILocalizzazioniReadInterface Members

		public IEnumerable<IndirizzoStradario> Indirizzi
		{
			get { return this._indirizzi; }
		}

		#endregion


		public bool ContieneRiferimentiCatastali
		{
			get { return this.Indirizzi.SelectMany(x => x.RiferimentiCatastali).Count() > 0; }
		}
	}
}
