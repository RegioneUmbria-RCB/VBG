// -----------------------------------------------------------------------
// <copyright file="BandiUmbriaReadInterface.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBandiUmbria
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class BandiUmbriaReadInterface : IBandiUmbriaReadInterface
	{
		private PresentazioneIstanzaDbV2 _db;

		public BandiUmbriaReadInterface(PresentazioneIstanzaDbV2 db)
		{
			this._db = db;
		}


		public DomandaBando DatiDomanda
		{
			get
			{
				var datiXml = this._db.DatiExtra.FindByChiave(Constants.ChiaveDb);

				if (datiXml == null)
					return null;

				return datiXml.Valore.DeserializeXML<DomandaBando>();
			}
		}
	}
}
