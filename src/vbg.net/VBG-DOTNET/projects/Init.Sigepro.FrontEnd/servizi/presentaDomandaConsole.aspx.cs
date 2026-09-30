using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.servizi
{
    public partial class PresentaDomandaConsole : System.Web.UI.Page
    {
        [Inject]
        protected IVbgAuthenticationService _authenticationService { get; set; }
        [Inject]
        protected ITokenResolver _tokenResolver { get; set; }
        protected void Page_Load(object sender, EventArgs e)
        {
            string alias = this.Page.RouteData.Values["alias"].ToString();
            string software = this.Page.RouteData.Values["software"].ToString();
            string codiceIntervento = this.Page.RouteData.Values["codiceIntervento"].ToString();

            var url = UrlBuilder.Url("~/Reserved/vbg-nuova-domanda.aspx", x =>
            {
                x.Add(new QsAliasComune(alias));
                x.Add(new QsSoftware(software));
                x.Add(new QsSelezionaIntervento(codiceIntervento));
            });

            this.Response.Redirect(url);
        }
    }
}