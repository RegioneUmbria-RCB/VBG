// -----------------------------------------------------------------------
// <copyright file="DenunceTaresBariReadInterface.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDenunceTares
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DenunceTaresBariReadInterface : IDenunceTaresBariReadInterface
	{
		private PresentazioneIstanzaDbV2 _db;

		public DenunceTaresBariReadInterface(PresentazioneIstanzaDbV2 db)
		{
			this._db = db;
		}

		public DatiAnagraficiContribuenteDenunciaTares DatiContribuente
		{
			get
			{
				var datiXml = this._db.DatiExtra.FindByChiave(DenunceTaresBariConstants.ChiaveDb);

				if (datiXml == null)
					return null;

				datiXml.Valore = datiXml.Valore.Replace(">Ignoto<", ">NonPresenteNellaDomanda<");

				return datiXml.Valore.DeserializeXML<DatiAnagraficiContribuenteDenunciaTares>();
			}
		}
	}
}
