using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.servizi
{
    public partial class attivaServizio : BasePage
    {
        [Inject]
        protected BookmarksService _bookmarksService { get; set; }
        [Inject]
        protected IVbgAuthenticationService _authenticationService { get; set; }

        [Inject]
        protected IRedirectService _redirService { get; set; }
        [Inject]
        protected ITokenResolver _tokenResolver { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var alias = this.Page.RouteData.Values["alias"].ToString();
            var software = this.Page.RouteData.Values["software"].ToString();
            var servizio = this.Page.RouteData.Values["servizio"].ToString();

            if (String.IsNullOrEmpty(servizio))
            {
                throw new ArgumentException("Pagina non trovata");
            }

            var datiBookmark = this._bookmarksService.GetDatiBookmark(servizio);

            if (datiBookmark == null)
            {
                throw new ArgumentException("Pagina non trovata: /" + servizio);
            }

            var url = UrlBuilder.Url("~/reserved/inserimentoIstanza/benvenutoBookmark.aspx", x =>
            {
                x.Add(new QsAliasComune(alias));
                x.Add(new QsSoftware(software));
                x.Add(new QsBookmarkId(servizio));
            });

            if (datiBookmark.Anonimo && String.IsNullOrEmpty(this._tokenResolver.Token))
            {
                this._authenticationService.LoginAnonimo(alias);
            }

            this.Response.Redirect(url);
        }
    }
}