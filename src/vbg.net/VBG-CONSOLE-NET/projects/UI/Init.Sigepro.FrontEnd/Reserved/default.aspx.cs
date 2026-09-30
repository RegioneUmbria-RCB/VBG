using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
// using Init.Sigepro.FrontEnd.AppLogic.Configuration;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class _default : ReservedBasePage
	{
		[Inject]
		public IConfigurazione<ParametriWorkflow> _configurazione { get; set; }


		protected void Page_Load(object sender, EventArgs e)
		{
			var page = _configurazione.Parametri.PaginaIniziale;

			if (!String.IsNullOrEmpty(page))
            {
                var newUrl = UrlBuilder.Url(page, x => {
                    x.Add(new QsAliasComune(Request.QueryString));
                    x.Add(new QsSoftware(Request.QueryString));
                });

                Response.Redirect(newUrl);
            }
		}
	}
}
