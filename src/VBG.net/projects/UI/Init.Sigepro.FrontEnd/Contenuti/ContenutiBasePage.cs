using System;
using System.Globalization;
using System.Threading;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti;
using Init.Sigepro.FrontEnd.WebControls;
using Ninject;

namespace Init.Sigepro.FrontEnd.Contenuti
{
	public class ContenutiBasePage : Ninject.Web.PageBase, IIdComunePage
	{
		[Inject]
		protected ConfigurazioneContenuti Configurazione{get;set;}

		public string AliasComune
		{
			get
			{
				return Request.QueryString["alias"];
			}
		}

		public string IdComune
		{
			get
			{
				return Configurazione.DatiComune.IdComune;
			}
		}


		public string Software
		{
			get
			{
				var sw = Request.QueryString["software"];

				if (string.IsNullOrEmpty(sw))
					return "SS";

				return sw;
			}
		}

		public ContenutiBasePage()
		{
			CultureInfo uiCulture = new CultureInfo("it-IT");
			uiCulture.NumberFormat.NumberDecimalSeparator = ",";
			uiCulture.NumberFormat.NumberGroupSeparator = ".";
			uiCulture.NumberFormat.CurrencyDecimalSeparator = ",";
			uiCulture.NumberFormat.CurrencyGroupSeparator = ".";

			Thread.CurrentThread.CurrentUICulture = uiCulture;
			Thread.CurrentThread.CurrentCulture = uiCulture;

			this.Load += new EventHandler(ContenutiBasePage_Load);

		}

		void ContenutiBasePage_Load(object sender, EventArgs e)
		{

			Response.Cache.SetCacheability(HttpCacheability.NoCache);
			Response.Cache.SetNoServerCaching();
			Response.Cache.SetNoStore();
			Response.Cache.SetExpires(DateTime.Now.AddDays(-1));
		}

		public string GetUrlBase()
		{
			var req = HttpContext.Current.Request;
			var downloadUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

			if (!String.IsNullOrEmpty(req.ApplicationPath))
				downloadUrl += req.ApplicationPath;

			if (!downloadUrl.EndsWith("/"))
				downloadUrl += "/";

			return downloadUrl;
		}
	}
}
