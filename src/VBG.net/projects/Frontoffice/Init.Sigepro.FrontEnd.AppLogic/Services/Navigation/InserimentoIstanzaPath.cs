namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	public class InserimentoIstanzaPath
	{
		private static class Urls
		{
			public const string DownloadPdfcompilabile = "~/Reserved/InserimentoIstanza/DownloadPdfCompilabile.ashx";
			public const string DownloadOggettoCompilabile = "~/Reserved/InserimentoIstanza/DownloadOggettoCompilabile.ashx";
			public const string DownloadOggetto = "~/Reserved/MostraOggettoFo.ashx";
		}

		public EditOggettiPath EditOggetti { get; private set; }
		public GestioneBandiPath GestioneBandi { get; private set; }
		public FirmaGrafometricaPath FirmaGrafometrica { get; private set; }

		public InserimentoIstanzaPath()
		{
			this.EditOggetti = new EditOggettiPath();
			this.GestioneBandi = new GestioneBandiPath();
			this.FirmaGrafometrica = new FirmaGrafometricaPath();
		}

		public string GetUrlDownloadPdfCompilabile(string idComune, string token, string software, int codiceOggetto, int idDomanda)
		{
			var sb = new StringBuilder(Urls.DownloadPdfcompilabile);
			sb.AppendFormat("?{0}={1}", PathUtils.UrlParameters.IdComune, idComune)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Token, token)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Software, software)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.CodiceOggetto, codiceOggetto)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdPresentazione, idDomanda)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);

			return sb.ToString();
		}

		public string GetUrlDownloadOggettoCompilabile(string idComune, string token, string software, int codiceOggetto, int idDomanda, string formato)
		{
			var sb = new StringBuilder(Urls.DownloadOggettoCompilabile);
			sb.AppendFormat("?{0}={1}", PathUtils.UrlParameters.IdComune, idComune)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Token, token)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Software, software)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.CodiceOggetto, codiceOggetto)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdPresentazione, idDomanda)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Fmt, formato);

			return sb.ToString();
		}

		public string GetUrlDownloadOggetto(string idComune, string token, string software, int codiceOggetto, int idDomanda)
		{
			var sb = new StringBuilder(Urls.DownloadOggetto);
			sb.AppendFormat("?{0}={1}", PathUtils.UrlParameters.IdComune, idComune)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Token, token)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Software, software)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.CodiceOggetto, codiceOggetto)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdPresentazione, idDomanda)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);

			return sb.ToString();
		}
	}
}
