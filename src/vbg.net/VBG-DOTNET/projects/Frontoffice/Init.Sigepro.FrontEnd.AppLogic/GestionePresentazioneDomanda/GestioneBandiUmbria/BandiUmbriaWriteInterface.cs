// -----------------------------------------------------------------------
// <copyright file="BandiUmbriaWriteInterface.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBandiUmbria
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;
	using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class BandiUmbriaWriteInterface : IBandiUmbriaWriteInterface
	{

		private PresentazioneIstanzaDbV2 _db;

		public BandiUmbriaWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._db = database;
		}

		public void ImpostaDatiDomanda(DomandaBando domanda)
		{
			var chiaveEsistente = this._db.DatiExtra.FindByChiave(Constants.ChiaveDb);

			if (chiaveEsistente != null)
			{
				chiaveEsistente.Delete();
				this._db.DatiExtra.AcceptChanges();
			}

			this._db.DatiExtra.AddDatiExtraRow(Constants.ChiaveDb, domanda.ToXmlString());

			this._db.DatiExtra.AcceptChanges();
		}
	}
}
