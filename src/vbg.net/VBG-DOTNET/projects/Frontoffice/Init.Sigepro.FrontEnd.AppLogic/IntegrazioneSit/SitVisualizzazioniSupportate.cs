// -----------------------------------------------------------------------
// <copyright file="SitVisualizzazioniSupportate.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.SigeproSitWebService;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class SitVisualizzazioniSupportate
	{
		BaseDtoOfTipoVisualizzazioneString[] _visualizzazioniSupportate;

		public SitVisualizzazioniSupportate(BaseDtoOfTipoVisualizzazioneString[] visualizzazioniSupportate)
		{
			this._visualizzazioniSupportate = visualizzazioniSupportate;
		}

		public bool SupportaVisualizzazioneMappaDaIndirizzo()
		{
			return this._visualizzazioniSupportate.Where(x => x.Codice == TipoVisualizzazione.PuntoDaIndirizzo).Count() > 0;
		}

		public bool SupportaVisualizzazioneMappaDaMappale()
		{
			return this._visualizzazioniSupportate.Where(x => x.Codice == TipoVisualizzazione.PuntoDaMappale).Count() > 0;
		}

		public string UrlVisualizzazioneMappaDaIndirizzo()
		{
			var visualizzazione = this._visualizzazioniSupportate.Where(x => x.Codice == TipoVisualizzazione.PuntoDaIndirizzo).FirstOrDefault();

			if (visualizzazione == null)
				return String.Empty;

			return visualizzazione.Descrizione;
		}

		public string UrlVisualizzazioneMappaDaMappale()
		{
			var visualizzazione = this._visualizzazioniSupportate.Where(x => x.Codice == TipoVisualizzazione.PuntoDaMappale).FirstOrDefault();

			if (visualizzazione == null)
				return String.Empty;

			return visualizzazione.Descrizione;
		}

	}
}
