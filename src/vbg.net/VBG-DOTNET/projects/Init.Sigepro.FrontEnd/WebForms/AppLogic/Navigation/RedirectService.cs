using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System;
using System.Configuration;
using System.Diagnostics;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Navigation
{


    public class RedirectService : IRedirectService
    {
        public static class Constants
        {
            public static class Urls
            {
                public const string DefaultPageContenuti = "~/Contenuti/default.aspx";
                public const string Logout = "~/LogoutCompleted.aspx";
                public const string RegistrazioneCompletata = "~/RegistrazioneCompletata.aspx";
                public const string RegistrazioneCompletataCie = "~/RegistrazioneCompletataCie.aspx";
                public const string InternetExplorerNonSupportato = "~/AvvisoInternetExplorer.aspx";
                public const string EditOggetti = "~/Reserved/InserimentoIstanza/EditOggetti/Edit.aspx";
            }
        }

        private readonly HttpResponse _response;
        private readonly HttpContext _context;
        // private readonly PathUtils _pathUtils;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;


        public RedirectService(IAliasSoftwareResolver aliasSoftwareResolver)
        {

            if (aliasSoftwareResolver == null)
                throw new System.ArgumentNullException(nameof(aliasSoftwareResolver));
            //Condition.Requires(resolveContext.Get, "context").IsNotNull();

            this._response = HttpContext.Current.Response;
            this._context = HttpContext.Current;
            // this._pathUtils = new PathUtils(aliasSoftwareResolver);

            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public void RedirectToHomeAreaRiservata(string returnTo = "")
        {
            var urlPaginaDefault = this.GetPathCompletoReservedDefaultPage(returnTo);

            this._response.Redirect(urlPaginaDefault, true);
        }

        public string GetPathCompletoRootApplicazione()
        {
            var req = HttpContext.Current.Request;
            //var urlAssoluto = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;
            var urlAssoluto = "//" + req.Url.Host;

            if (!String.IsNullOrEmpty(ConfigurationManager.AppSettings["NavigationService.UsaPortaSuUrlRedirect"]))
            {
                urlAssoluto = "//" + req.Url.Host + ":" + req.Url.Port;
            }

            if (!string.IsNullOrEmpty(req.ApplicationPath))
            {
                urlAssoluto += req.ApplicationPath;
            }

            if (!urlAssoluto.EndsWith("/"))
            {
                urlAssoluto += "/";
            }

            return urlAssoluto;
        }

        public string GetPathCompletoReservedDefaultPage(string returnTo = "")
        {
            var root = "Reserved/default.aspx";
            var url = this.GetPathCompletoRootApplicazione() + UrlBuilder.Url(root, x =>
            {
                x.Add(new QsAliasComune(this._aliasSoftwareResolver.AliasComune));
                x.Add(new QsSoftware(this._aliasSoftwareResolver.Software));

                if (!string.IsNullOrEmpty(returnTo))
                {
                    x.Add(new QsReturnTo(returnTo));
                }
            });

            return url;
        }

        [DebuggerNonUserCode]
        public void RedirectToHomeContenuti()
        {
            var url = UrlBuilder.Url(Constants.Urls.DefaultPageContenuti, x =>
            {
                x.Add(new QsAliasComune(this._aliasSoftwareResolver.AliasComune));
                x.Add(new QsSoftware(this._aliasSoftwareResolver.Software));
            });

            this._response.Redirect(url);
        }

        public void RedirectToAvvisoInternetExplorer()
        {
            this.RedirectToUnprotectedAddress(Constants.Urls.InternetExplorerNonSupportato);
        }

        public void RedirectToLogoutUrl()
        {
            this.RedirectToUnprotectedAddress(Constants.Urls.Logout);
        }

        public void RedirectToUrlRegistrazioneCompletata()
        {
            this.RedirectToUnprotectedAddress(Constants.Urls.RegistrazioneCompletata);
        }

        public void RedirectToUrlRegistrazioneCompletataCie()
        {
            this.RedirectToUnprotectedAddress(Constants.Urls.RegistrazioneCompletataCie);
        }

        public void ToFirmaDigitale(int idDomanda, int codiceOggetto)
        {
            var returnUrl = this._context.Request.Url.ToString().Replace("&AllegaRiepilogo=True", string.Empty);

            var url = UrlBuilder.Url("~/Reserved/InserimentoIstanza/FirmaDigitale/FirmaDocumento.aspx", x =>
            {
                x.Add(new QsAliasComune(this._aliasSoftwareResolver.AliasComune));
                x.Add(new QsSoftware(this._aliasSoftwareResolver.Software));
                x.Add(new QsCodiceOggetto(codiceOggetto));
                x.Add(new QsIdDomandaOnline(idDomanda));
                x.Add(new QsReturnTo(returnUrl));
            });

            this._response.Redirect(url);
        }

        public void ToUploadAllegatiMultipli(int idDomanda, string origine, int idAllegato)
        {
            var returnUrl = this._context.Request.Url.ToString();
            var src = origine + idAllegato.ToString();

            var url = UrlBuilder.Url("~/Reserved/InserimentoIstanza/GestioneAllegatiMultipli.aspx", x =>
            {
                x.Add(new QsAliasComune(this._aliasSoftwareResolver.AliasComune));
                x.Add(new QsSoftware(this._aliasSoftwareResolver.Software));
                x.Add("src", src);
                x.Add(new QsIdDomandaOnline(idDomanda));
                x.Add(new QsReturnTo(returnUrl));
            });

            this._response.Redirect(url);
        }

        public void RedirectToPaginaCompilazioneOggetti(int idPresentazione, int idAllegato, string tipoAllegato)
        {
            var returnTo = this._context.Request.Url.ToString();

            if (idAllegato <= 0)
                throw new ArgumentNullException(nameof(idAllegato));

            //Condition.Requires(idAllegato, "idAllegato").IsGreaterThan(0);

            var url = UrlBuilder.Url(Constants.Urls.EditOggetti, x =>
            {
                x.Add(new QsAliasComune(this._aliasSoftwareResolver.AliasComune));
                x.Add(new QsSoftware(this._aliasSoftwareResolver.Software));
                x.Add(new QsIdDomandaOnline(idPresentazione));
                x.Add("TipoAllegato", tipoAllegato);
                x.Add("IdAllegato", idAllegato);
                x.Add("Timestamp", DateTime.Now.Millisecond);
                x.Add(new QsReturnTo(returnTo));
            });

            this._response.Redirect(url);
        }

        private void RedirectToUnprotectedAddress(string url)
        {
            var redir = UrlBuilder.Url(url, x =>
            {
                x.Add(new QsAliasComune(this._aliasSoftwareResolver.AliasComune));
                x.Add(new QsSoftware(this._aliasSoftwareResolver.Software));
            });

            this._response.Redirect(redir);
        }
    }
}