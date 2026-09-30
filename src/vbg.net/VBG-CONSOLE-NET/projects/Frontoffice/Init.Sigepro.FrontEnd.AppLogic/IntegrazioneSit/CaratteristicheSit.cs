// -----------------------------------------------------------------------
// <copyright file="CaratteristicheSit.cs" company="">
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
	public class CaratteristicheSit
	{
		public SitCampiSupportati CampiSupportati { get; private set; }
		public SitVisualizzazioniSupportate VisualizzazioniSupportate { get; private set; }

		public CaratteristicheSit(string[] listaNomiCampi, BaseDtoOfTipoVisualizzazioneString[] visualizzazioniSupportate)
		{
			// TODO: Complete member initialization
			this.CampiSupportati = new SitCampiSupportati(listaNomiCampi);
			this.VisualizzazioniSupportate = new SitVisualizzazioniSupportate(visualizzazioniSupportate);
		}

	}
}
