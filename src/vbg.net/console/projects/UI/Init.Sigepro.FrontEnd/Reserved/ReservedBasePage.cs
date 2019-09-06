using System;
using System.Linq;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.WebControls;
using System.Globalization;
using System.Threading;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRitornoScrivaniaVirtuale;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public class ReservedBasePage : BasePage
	{
		[Inject]
		public IAuthenticationDataResolver _authenticationDataResolver { get; set; }
        [Inject]
        public RitornoScrivaniaVirtualeService _ritornoScrivaniaVirtualeService { get; set; }

        public UserAuthenticationResult UserAuthenticationResult
		{
			get { return _authenticationDataResolver.DatiAutenticazione; }
		}

		protected string CodiceUtente
		{
			get { return UserAuthenticationResult.DatiUtente.Codicefiscale; }
		}


		public ReservedBasePage()
		{

		}


        protected override void OnLoad(EventArgs e)
        {
            var qs = Request.QueryString["url-scrivania-virtuale"];

            if (!String.IsNullOrEmpty(qs))
            {
                this._ritornoScrivaniaVirtualeService.SalvaUrlRitorno(qs);
            }

            base.OnLoad(e);
        }


        #region gestione della navigazione

        public string ResolvePlaceholders(string url)
        {
            if (url.IndexOf("{software}") >= 0)
            {
                url = url.Replace("{software}", Software);
            }

            if (url.IndexOf("{idcomune}") >= 0)
            {
                url = url.Replace("{idcomune}", IdComune);
            }

            return url;
        }

        public string BuildClientUrl(string url, Action<QuerystringArgumentsList> parametersBuilder = null)
        {
            var ub = new UrlBuilder();

            if (url.IndexOf("{software}") >= 0)
            {
                url = url.Replace("{software}", Software);
            }
            else
            {
                ub.AddDefaultParameter("software", Software);
            }

            if (url.IndexOf("{idcomune}") >= 0)
            {
                url = url.Replace("{idcomune}", IdComune);
            }
            else
            {
                ub.AddDefaultParameter("idcomune", IdComune);
            }

            var newUrl = ub.Build(url, parametersBuilder);

            return ResolveClientUrl(newUrl);
        }

		protected void Redirect(string url, Action<QuerystringArgumentsList> parametersBuilder)
		{
            var ub = new UrlBuilder();

            ub.AddDefaultParameter("Software", Software);
            ub.AddDefaultParameter("IdComune", IdComune);

            var newUrl = ub.Build(url, parametersBuilder);

            Response.Redirect(newUrl);
		}
        
		#endregion

	}
}
