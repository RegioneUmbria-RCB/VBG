// -----------------------------------------------------------------------
// <copyright file="DenunceTaresBariWriteInterface.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDenunceTares
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;
	using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DenunceTaresBariWriteInterface : IDenunceTaresBariWriteInterface
	{
		private PresentazioneIstanzaDbV2 _db;

		public DenunceTaresBariWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._db = database;
		}

		public void ImpostaDatiImmobili(Bari.TASI.DTOs.DatiContribuenteTasiDto datiImmobili)
		{

		}

		public void ImpostaDatiContribuente(DatiAnagraficiContribuenteDenunciaTares datiContribuente)
		{
			var chiaveEsistente = this._db.DatiExtra.FindByChiave(DenunceTaresBariConstants.ChiaveDb);

			if (chiaveEsistente != null)
			{
				chiaveEsistente.Delete();
				this._db.DatiExtra.AcceptChanges();
			}

			this._db.DatiExtra.AddDatiExtraRow(DenunceTaresBariConstants.ChiaveDb, datiContribuente.ToXmlString());

			this._db.DatiExtra.AcceptChanges();
		}
	}
}
