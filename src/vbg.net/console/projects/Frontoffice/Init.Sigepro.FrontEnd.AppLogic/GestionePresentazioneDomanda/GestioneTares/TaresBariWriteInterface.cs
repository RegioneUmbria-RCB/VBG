using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneTares
{
	public class TaresBariWriteInterface: ITaresBariWriteInterface
	{
		private static class Constants
		{
			public const string ChiaveDb = "TaresBari.DatiUtenza";
		}

		PresentazioneIstanzaDbV2 _db;

		public TaresBariWriteInterface(PresentazioneIstanzaDbV2 db)
		{
			this._db = db;
		}

		public void ImpostaUtenza(DatiContribuenteDto datiutenza)
		{
			var chiaveEsistente = this._db.DatiExtra.FindByChiave(Constants.ChiaveDb);

			if (chiaveEsistente != null)
			{
				chiaveEsistente.Delete();
				this._db.DatiExtra.AcceptChanges();
			}

			this._db.DatiExtra.AddDatiExtraRow(Constants.ChiaveDb, datiutenza.ToXmlString());

			this._db.DatiExtra.AcceptChanges();
		}
	}
}
