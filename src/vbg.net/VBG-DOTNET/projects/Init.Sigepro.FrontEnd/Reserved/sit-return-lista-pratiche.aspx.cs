using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class sit_return_lista_pratiche : Ninject.Web.PageBase
    {
        [Inject]
        protected IAreaRiservataAuthenticationService _areaRiservataAuthenticationService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var token = (this.Page.RouteData.Values["token"] ?? String.Empty).ToString();
            var idcomune = (this.Page.RouteData.Values[QsAliasComune.QuerystringParameterName] ?? String.Empty).ToString();
            var software = (this.Page.RouteData.Values[QsSoftware.QuerystringParameterName] ?? String.Empty).ToString();

            this._areaRiservataAuthenticationService.AuthenticateUser(token);
            HttpContext.Current.Items["alias"] = idcomune;
            HttpContext.Current.Items["software"] = software;

            var ub = new UrlBuilder();

            var url = ub.Build("~/reserved/IstanzePresentate.aspx", pars =>
            {
                pars.Add(QsAliasComune.QuerystringParameterName, idcomune);
                pars.Add(QsSoftware.QuerystringParameterName, software);
                pars.Add("restore", "1");
            });

            this.Response.Redirect(url);
        }
    }
}