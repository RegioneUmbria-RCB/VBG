// -----------------------------------------------------------------------
// <copyright file="TasiBariReadInterface.cs" company="">
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
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;
	using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class TasiBariReadInterface : ITasiBariReadInterface
	{
		private static class Constants
		{
			public const string ChiaveDb = "TasiBari.DatiImmobili";
		}


		private PresentazioneIstanzaDbV2 _db;

		public TasiBariReadInterface(PresentazioneIstanzaDbV2 db)
		{
			this._db = db;
		}


		public DatiContribuenteTasiDto DatiImmobili
		{
			get
			{
				var datiXml = this._db.DatiExtra.FindByChiave(Constants.ChiaveDb);

				if (datiXml == null)
					return null;

				datiXml.Valore = datiXml.Valore.Replace(">Ignoto<", ">NonPresenteNellaDomanda<");

				return datiXml.Valore.DeserializeXML<DatiContribuenteTasiDto>();
			}
		}
	}
}
