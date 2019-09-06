// -----------------------------------------------------------------------
// <copyright file="ImuBariReadInterface.cs" company="">
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
	using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;
	using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ImuBariReadInterface : IImuBariReadInterface
	{
		private static class Constants
		{
			public const string ChiaveDb = "ImuBari.DatiImmobili";
		}


		private PresentazioneIstanzaDbV2 _db;

		public ImuBariReadInterface(PresentazioneIstanzaDbV2 db)
		{
			this._db = db;
		}


		public DatiContribuenteImuDto DatiImmobili
		{
			get
			{
				var datiXml = this._db.DatiExtra.FindByChiave(Constants.ChiaveDb);

				if (datiXml == null)
					return null;

				datiXml.Valore = datiXml.Valore.Replace(">Ignoto<", ">NonPresenteNellaDomanda<");

				return datiXml.Valore.DeserializeXML<DatiContribuenteImuDto>();
			}
		}
	}
}
