using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneTares
{
	public class TaresBariReadInterface: ITaresBariReadInterface
	{

		private static class Constants
		{
			public const string ChiaveDb = "TaresBari.DatiUtenza";
		}

		PresentazioneIstanzaDbV2 _db;

		public TaresBariReadInterface(PresentazioneIstanzaDbV2 db)
		{
			this._db = db;
		}

		public DatiContribuenteDto DatiContribuente
		{
			get
			{
				var datiXml = this._db.DatiExtra.FindByChiave(Constants.ChiaveDb);

				if (datiXml == null)
					return null;

				datiXml.Valore = datiXml.Valore.Replace(">Ignoto<", ">NonPresenteNellaDomanda<");

				return datiXml.Valore.DeserializeXML<DatiContribuenteDto>();
			}
		}
	}
}
