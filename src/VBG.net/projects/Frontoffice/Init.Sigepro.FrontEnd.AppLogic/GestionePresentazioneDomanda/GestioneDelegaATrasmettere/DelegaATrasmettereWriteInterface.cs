using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere
{
	public class DelegaATrasmettereWriteInterface : IDelegaATrasmettereWriteInterface
	{
		PresentazioneIstanzaDbV2 _database;

		public DelegaATrasmettereWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;
		}

		#region IDelegaATrasmettereWriteInterface Members

		public void Elimina()
		{
			this._database.DelegaATrasmettere.Clear();
		}

		public void Salva(int codiceoggetto, string nomefile, bool isFirmatoDigitalmente)
		{
			Elimina();

			var allegatiRow = this._database.Allegati.AddAllegatiRow(nomefile, codiceoggetto, String.Empty, isFirmatoDigitalmente, String.Empty);

			this._database.DelegaATrasmettere.AddDelegaATrasmettereRow(allegatiRow.Id);
		}

		#endregion
	}
}
