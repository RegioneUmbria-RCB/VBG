namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
	using System;
	using System.Text;
	using System.Web;
	using CuttingEdge.Conditions;

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

		public RedirectService(HttpContext context)
		{
			Condition.Requires(context, "context").IsNotNull();

			this._response = context.Response;
			this._context = context;
			this._pathUtils = new PathUtils();
		}

		public void RedirectToHomeAreaRiservata(string idComune, string software, string token, string returnTo = "")
		{
			var urlPaginaDefault = new NavigationService(this._context).GetPathCompletoReservedDefaultPage(idComune, software, token, returnTo);

			this._response.Redirect(urlPaginaDefault, true);
		}

		public void RedirectToReservedAddress(string url, string idComune, string software, string token)
		{
			var url2 = GetReservedAddress(url, idComune, software, token);

			this._response.Redirect(url2);
		}

		public string GetReservedAddress(string url, string idComune, string software, string token)
		{
			StringBuilder sb = new StringBuilder(url);

			sb.Append((url.IndexOf('?') > 0) ? "&" : "?")
			  .Append(PathUtils.UrlParameters.IdComune)
			  .Append("=")
			  .Append(idComune)
			  .Append("&")
			  .Append(PathUtils.UrlParameters.Software)
			  .Append("=")
			  .Append(software)
			  .Append("&")
			  .Append(PathUtils.UrlParameters.Token)
			  .Append("=")
			  .Append(token);

			return sb.ToString();
		}

		public void RedirectToHomeContenuti(string aliasComune, string software)
		{
			Condition.Requires(aliasComune, "aliasComune").IsNotNullOrEmpty();

			var sb = new StringBuilder();

			sb.Append(Constants.Urls.DefaultPageContenuti)
			  .Append("?")
			  .Append(PathUtils.UrlParameters.Alias)
			  .Append("=")
			  .Append(aliasComune)
			  .Append("&")
			  .Append(PathUtils.UrlParameters.Software)
			  .Append("=")
			  .Append(software);

			this._response.Redirect(sb.ToString(), true);
		}

		public void Redirect(string url)
		{
			this._response.Redirect(url, true);
		}

		public void RedirectToLogoutUrl(string idComune, string software)
		{
			this.RedirectToUnprotectedAddress(Constants.Urls.Logout, idComune, software);
		}

		public void RedirectToUrlRegistrazioneCompletata(string idComune, string software)
		{
			this.RedirectToUnprotectedAddress(Constants.Urls.RegistrazioneCompletata, idComune, software);
		}

		public void RedirectToUrlRegistrazioneCompletataCie(string idComune, string software)
		{
			this.RedirectToUnprotectedAddress(Constants.Urls.RegistrazioneCompletataCie, idComune, software);
		}

		public void ToFirmaDigitale(string idComune, string software, string token, int idDomanda, int codiceOggetto)
		{
			var returnUrl = this._context.Server.UrlEncode(this._context.Request.Url.ToString().Replace("&AllegaRiepilogo=True", string.Empty));

			var firmaDigitaleFmtString = "~/Reserved/InserimentoIstanza/FirmaDigitale/FirmaDocumento.aspx?IdComune={0}&Software={1}&Token={2}&codiceOggetto={3}&IdPresentazione={4}&returnTo={5}";

			var redirectTo = string.Format(firmaDigitaleFmtString, idComune, software, token, codiceOggetto, idDomanda, returnUrl);

			this._response.Redirect(redirectTo);
		}

		public void RedirectToPaginaCompilazioneOggetti(string idComune, string software, string token, int idPresentazione, int idAllegato, string tipoAllegato)
		{
			var returnTo = this._context.Server.UrlEncode(this._context.Request.Url.ToString());

			var url = this._pathUtils.Reserved.InserimentoIstanza.EditOggetti.GetUrlEdit(idComune, software, token, idPresentazione, idAllegato, tipoAllegato, returnTo);

			this._response.Redirect(url);
		}

		private void RedirectToUnprotectedAddress(string url, string idComune, string software)
		{
			Condition.Requires(idComune, "idComune").IsNotNullOrEmpty();
			Condition.Requires(software, "software").IsNotNullOrEmpty();

			StringBuilder sb = new StringBuilder(url);

			sb.Append((url.IndexOf('?') > 0) ? "&" : "?")
			  .Append(PathUtils.UrlParameters.IdComune)
			  .Append("=")
			  .Append(idComune)
			  .Append("&")
			  .Append(PathUtils.UrlParameters.Software)
			  .Append("=")
			  .Append(software);

			this._response.Redirect(sb.ToString());
		}

        public void ToFirmaCidPin(string idComune, string software, string token, int idDomanda, int codiceOggetto)
        {
            var returnUrl = this._context.Server.UrlEncode(this._context.Request.Url.ToString().Replace("&AllegaRiepilogo=True", string.Empty));

            var firmaDigitaleFmtString = "~/Reserved/InserimentoIstanza/FirmaCidPin/Firma.aspx?IdComune={0}&Software={1}&Token={2}&codiceOggetto={3}&IdPresentazione={4}&returnTo={5}";

            var redirectTo = string.Format(firmaDigitaleFmtString, idComune, software, token, codiceOggetto, idDomanda, returnUrl);

            this._response.Redirect(redirectTo);
        }

        public void ToFirmaGrafometrica(string idComune, string software, string token, int idDomanda, int codiceOggetto)
        {
            var returnUrl = this._context.Server.UrlEncode(this._context.Request.Url.ToString().Replace("&AllegaRiepilogo=True", string.Empty));

            var firmaDigitaleFmtString = "~/Reserved/InserimentoIstanza/FirmaGrafometrica/Firma.aspx?IdComune={0}&Software={1}&Token={2}&codiceOggetto={3}&IdPresentazione={4}&returnTo={5}";

            var redirectTo = string.Format(firmaDigitaleFmtString, idComune, software, token, codiceOggetto, idDomanda, returnUrl);

            this._response.Redirect(redirectTo);
        }
    }
}
