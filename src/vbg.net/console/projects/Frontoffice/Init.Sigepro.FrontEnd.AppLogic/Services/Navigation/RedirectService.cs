namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
	using System;
	using System.Text;
	using System.Web;
	using CuttingEdge.Conditions;
    using System.Diagnostics;
    using Init.Sigepro.FrontEnd.AppLogic.Utils;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;

    public class RedirectService
	{
		public static class Constants
		{
			public static class Urls
			{
				public const string DefaultPageContenuti = "~/Contenuti/default.aspx";
				public const string Logout = "~/LogoutCompleted.aspx";
				public const string RegistrazioneCompletata = "~/RegistrazioneCompletata.aspx";
				public const string RegistrazioneCompletataCie = "~/RegistrazioneCompletataCie.aspx";
			}
		}

		private HttpResponse _response;
		private HttpContext _context;
		private PathUtils _pathUtils;
        IAliasSoftwareResolver _aliasSoftwareResolver;


        public RedirectService(IResolveHttpContext resolveContext, IAliasSoftwareResolver aliasSoftwareResolver)
		{
			Condition.Requires(resolveContext.Get, "context").IsNotNull();

			this._response = resolveContext.Get.Response;
			this._context = resolveContext.Get;
			this._pathUtils = new PathUtils(aliasSoftwareResolver);

            this._aliasSoftwareResolver = aliasSoftwareResolver;
		}

		public void RedirectToHomeAreaRiservata(string returnTo = "")
		{
			var urlPaginaDefault = new NavigationService(this._context, this._aliasSoftwareResolver).GetPathCompletoReservedDefaultPage(returnTo);

			this._response.Redirect(urlPaginaDefault, true);
		}

        [DebuggerNonUserCode]
		public void RedirectToHomeContenuti()
		{			
            var url = UrlBuilder.Url(Constants.Urls.DefaultPageContenuti, x =>
            {
                x.Add(PathUtils.UrlParameters.Alias, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
            });

			this._response.Redirect(url);
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

            var url = UrlBuilder.Url("~/Reserved/InserimentoIstanza/FirmaDigitale/FirmaDocumento.aspx", x => {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idDomanda);
                x.Add(PathUtils.UrlParameters.ReturnTo, returnUrl);
            });

			this._response.Redirect(url);
		}

        public void ToUploadAllegatiMultipli(int idDomanda, string origine, int idAllegato)
        {
            var returnUrl = this._context.Request.Url.ToString();
            var src = origine + idAllegato.ToString();

            var url = UrlBuilder.Url("~/Reserved/InserimentoIstanza/GestioneAllegatiMultipli.aspx", x => {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add("src", src);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idDomanda);
                x.Add(PathUtils.UrlParameters.ReturnTo, returnUrl);
            });

            this._response.Redirect(url);
        }

		public void RedirectToPaginaCompilazioneOggetti(int idPresentazione, int idAllegato, string tipoAllegato)
		{
			var returnTo = this._context.Request.Url.ToString();

			var url = this._pathUtils.Reserved.InserimentoIstanza.EditOggetti.GetUrlEdit(idPresentazione, idAllegato, tipoAllegato, returnTo);

			this._response.Redirect(url);
		}

		private void RedirectToUnprotectedAddress(string url)
		{
            var redir = UrlBuilder.Url(url, x =>
            {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
            });

			this._response.Redirect(redir);
		}

        public void ToFirmaCidPin(int idDomanda, int codiceOggetto)
        {
            var returnUrl = this._context.Request.Url.ToString().Replace("&AllegaRiepilogo=True", string.Empty);
 
            var url = UrlBuilder.Url("~/Reserved/InserimentoIstanza/FirmaCidPin/Firma.aspx", x => {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idDomanda);
                x.Add(PathUtils.UrlParameters.ReturnTo, returnUrl);
            });

            this._response.Redirect(url);
        }

        public void ToFirmaGrafometrica( int idDomanda, int codiceOggetto)
        {
            var returnUrl = this._context.Request.Url.ToString().Replace("&AllegaRiepilogo=True", string.Empty);

            var url = UrlBuilder.Url("~/Reserved/InserimentoIstanza/FirmaGrafometrica/Firma.aspx", x => {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idDomanda);
                x.Add(PathUtils.UrlParameters.ReturnTo, returnUrl);
            });

            this._response.Redirect(url);
        }
    }
}
