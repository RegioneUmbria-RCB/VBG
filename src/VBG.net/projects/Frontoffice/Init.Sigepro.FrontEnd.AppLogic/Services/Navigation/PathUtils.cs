namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
	using System.Text;

	public class PathUtils
	{
		public static class UrlParameters
		{
			public const string Alias = "alias";
			public const string IdComune = "idComune";
			public const string Id = "id";
			public const string Token = "token";
			public const string Software = "software";
			public const string IdAllegato = "IdAllegato";
			public const string TipoAllegato = "TipoAllegato";
			public const string ReturnTo = "ReturnTo";
			public const string IdPresentazione = "IdPresentazione";
			public const string Convert = "convert";
			public const string CodiceOggetto = "codiceOggetto";
			public const string Timestamp = "_ts";
			public const string Fmt = "Fmt";
			public const string NomeFile = "nome";
			public const string PdfCompilabile = "pdfCompilabile";

			public const string CodiceFiscaleAzienda = "cfAzienda";
			public const string PartitaIvaAzienda = "ivaAzienda";
		}

		public static class UrlParametersValues
		{
			public const string TipoAllegatoEndo = "E";
			public const string TipoAllegatoIntervento = "I";
			public const string True = "true";
			public const string False = "false";
		}

		private static class Urls
		{
			public const string MostraOggetto = "~/MostraOggetto.ashx";
			public const string MostraOggettoPdf = "~/MostraOggettoPdf.ashx";
		}

		public ReservedPath Reserved { get; private set; }

		public PathUtils()
		{
			this.Reserved = new ReservedPath();
		}

		public string GetUrlMostraOggetto(string idComune, int codiceOggetto)
		{
			var sb = new StringBuilder(Urls.MostraOggetto);

			sb.AppendFormat("?{0}={1}", UrlParameters.IdComune, idComune);
			sb.AppendFormat("&{0}={1}", UrlParameters.CodiceOggetto, codiceOggetto);

			return sb.ToString();
		}

		public string GetUrlMostraOggettoPdf(string idComune, int codiceOggetto)
		{
			var sb = new StringBuilder(Urls.MostraOggettoPdf);

			sb.AppendFormat("?{0}={1}", UrlParameters.IdComune, idComune);
			sb.AppendFormat("&{0}={1}", UrlParameters.Id, codiceOggetto);

			return sb.ToString();
		}
	}
}
