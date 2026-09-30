using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class sit_return_dettaglio_pratica : Ninject.Web.PageBase
    {
        [Inject]
        protected IAreaRiservataAuthenticationService _areaRiservataAuthenticationService { get; set; }

        protected QsReturnTo ReturnTo
        {
            get
            {
                return new QsReturnTo(this.Request.QueryString);
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            var token = (this.Page.RouteData.Values["token"] ?? String.Empty).ToString();
            var idcomune = (this.Page.RouteData.Values[QsAliasComune.QuerystringParameterName] ?? String.Empty).ToString();
            var software = (this.Page.RouteData.Values[QsSoftware.QuerystringParameterName] ?? String.Empty).ToString();
            var uuidPratica = (this.Page.RouteData.Values[QsUuidIstanza.QuerystringParameterName] ?? String.Empty).ToString();

            this._areaRiservataAuthenticationService.AuthenticateUser(token);
            HttpContext.Current.Items["alias"] = idcomune;
            HttpContext.Current.Items["software"] = software;

            var url = UrlBuilder.Url("~/reserved/DettaglioIstanzaEx.aspx", pars =>
            {
                pars.Add(QsAliasComune.QuerystringParameterName, idcomune);
                pars.Add(QsSoftware.QuerystringParameterName, software);
                pars.Add(this.ReturnTo);
                pars.Add(QsUuidIstanza.QuerystringParameterName, uuidPratica);
            });

            this.Response.Redirect(url);
        }
    }
}