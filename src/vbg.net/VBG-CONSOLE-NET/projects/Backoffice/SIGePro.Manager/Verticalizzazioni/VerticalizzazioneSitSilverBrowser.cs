// -----------------------------------------------------------------------
// <copyright file="VerticalizzazioneSitSilverBrowser.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Verticalizzazioni
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Verticalizzazioni;

	public class VerticalizzazioneSitSilverBrowser : Verticalizzazione
	{
		private static class Constants
		{
			public const string NomeVerticalizzazione = "SIT_SILVERBROWSER";
			public const string ServiceBaseUrl = "SERVICE_BASE_URL";
			public const string MapZoomDaPunto = "MAP_ZOOM_DA_PUNTO";
			public const string MapZoomDaParticella = "MAP_ZOOM_DA_PARTICELLA";
			public const string MapZoomDaPuntoBo = "MAP_ZOOM_DA_PUNTO_BO";
			public const string MapZoomDaParticellaBo = "MAP_ZOOM_DA_PARTICELLA_BO";
			public const string MapTooltip = "MAP_URL_TOOLTIP";

		}

		public VerticalizzazioneSitSilverBrowser()
			: base()
		{
		}

		public VerticalizzazioneSitSilverBrowser(string idComuneAlias, string software)
			: base(idComuneAlias, Constants.NomeVerticalizzazione, software)
		{
		}

		public string ServiceBaseUrl
		{
			get { return GetString(Constants.ServiceBaseUrl); }
			set { SetString(Constants.ServiceBaseUrl, value); }
		}

		public string MapZoomDaPunto 
		{
			get { return GetString(Constants.MapZoomDaPunto); }
			set { SetString(Constants.MapZoomDaPunto, value); }	
		}


		public string MapZoomDaParticella
		{
			get { return GetString(Constants.MapZoomDaParticella); }
			set { SetString(Constants.MapZoomDaParticella, value); }
		}

		public string MapZoomDaParticellaBo
		{
			get { return GetString(Constants.MapZoomDaParticellaBo); }
			set { SetString(Constants.MapZoomDaParticellaBo, value); }
		}

		public string MapZoomDaPuntoBo
		{
			get { return GetString(Constants.MapZoomDaPuntoBo); }
			set { SetString(Constants.MapZoomDaPuntoBo, value); }
		}
	}
}
