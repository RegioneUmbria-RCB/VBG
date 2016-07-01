// -----------------------------------------------------------------------
// <copyright file="DatiExtraReadInterface.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiExtra
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiExtraReadInterface: IDatiExtraReadInterface
	{
		private PresentazioneIstanzaDbV2 _db;

		public DatiExtraReadInterface(PresentazioneIstanzaDbV2 db)
		{
			this._db = db;
		}


		public string GetValoreDato(string chiave)
		{
			var datiXml = this._db.DatiExtra.FindByChiave(chiave);

			if (datiXml == null)
				return null;
				
			return datiXml.Valore;
		}


		public IEnumerable<string> Keys
		{
			get { return this._db.DatiExtra.Select(x => x.Chiave); }
		}
	}
}
