// -----------------------------------------------------------------------
// <copyright file="ImuBariWriteInterface.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneImu
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
	using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ImuBariWriteInterface : IImuBariWriteInterface
	{
		private static class Constants
		{
			public const string ChiaveDb = "ImuBari.DatiImmobili";
		}

		private PresentazioneIstanzaDbV2 _db;

		public ImuBariWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._db = database;
		}

		public void ImpostaDatiImmobili(DatiContribuenteImuDto datiImmobili)
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
