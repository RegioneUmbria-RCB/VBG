// -----------------------------------------------------------------------
// <copyright file="TasiBariWriteInterface.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneTasi
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class TasiBariWriteInterface : ITasiBariWriteInterface
	{
		private static class Constants
		{
			public const string ChiaveDb = "TasiBari.DatiImmobili";
		}

		private PresentazioneIstanzaDbV2 _db;

		public TasiBariWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._db = database;
		}

		public void ImpostaDatiImmobili(Bari.TASI.DTOs.DatiContribuenteTasiDto datiImmobili)
		{
			var chiaveEsistente = this._db.DatiExtra.FindByChiave(Constants.ChiaveDb);

			if (chiaveEsistente != null)
			{
				chiaveEsistente.Delete();
				this._db.DatiExtra.AcceptChanges();
			}

			this._db.DatiExtra.AddDatiExtraRow(Constants.ChiaveDb, datiImmobili.ToXmlString());

			this._db.DatiExtra.AcceptChanges();
		}
	}
}
