using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.servizi
{
    public partial class attivaServizio : BasePage
    {
        [Inject]
        protected BookmarksService _bookmarksService { get; set; }
        [Inject]
        protected VbgAuthenticationService _authenticationService { get; set; }

        [Inject]
        protected RedirectService _redirService { get; set; }
        [Inject]
        protected ITokenResolver _tokenResolver { get; set; } 

        protected void Page_Load(object sender, EventArgs e)
        {
            var alias = Page.RouteData.Values["alias"].ToString();
            var software = Page.RouteData.Values["software"].ToString();
            var servizio = Page.RouteData.Values["servizio"].ToString();

            if (String.IsNullOrEmpty(servizio))
            {
                throw new ArgumentException("Pagina non trovata");
            }

            var datiBookmark = this._bookmarksService.GetDatiBookmark(servizio);

            if (datiBookmark == null)
            {
                throw new ArgumentException("Pagina non trovata: /" + servizio);
            }

            var url = UrlBuilder.Url("~/reserved/inserimentoIstanza/benvenutoBookmark.aspx", x => {
                x.Add(new QsAliasComune(alias));
                x.Add(new QsSoftware(software));
                x.Add(new QsBookmarkId(servizio));
            });

            if (datiBookmark.Anonimo && String.IsNullOrEmpty(this._tokenResolver.Token))
            {
                _authenticationService.LoginAnonimo(alias);
            }

            Response.Redirect(url);
        }
    }
}