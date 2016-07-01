namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
	using System;
	using System.Text;
	using CuttingEdge.Conditions;

	public class EditOggettiPath
	{
		private static class Urls
		{
			public const string Upload = "~/Reserved/InserimentoIstanza/EditOggetti/Upload.ashx";
			public const string Edit = "~/Reserved/InserimentoIstanza/EditOggetti/Edit.aspx";
		}

		public string GetUrlUpload(string idComune, string token, string software, int idAllegato, string tipoAllegato, int idDomanda, string nomefile, int? codiceOggetto)
		{
			var sb = new StringBuilder(Urls.Upload);
			sb.AppendFormat("?{0}={1}", PathUtils.UrlParameters.IdComune, idComune)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Token, token)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Software, software)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdAllegato, idAllegato)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdPresentazione, idDomanda)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.TipoAllegato, tipoAllegato)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.NomeFile, nomefile)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.CodiceOggetto, codiceOggetto)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);

			return sb.ToString();
		}

		public string GetUrlEdit(string idComune, string software, string token, int idPresentazione, int codiceOggetto, bool pdfCompilabile,string returnTo)
		{
			Condition.Requires(idComune, "idComune").IsNotNullOrEmpty();
			Condition.Requires(software, "software").IsNotNullOrEmpty();
			Condition.Requires(token, "token").IsNotNullOrEmpty();
			Condition.Requires(codiceOggetto, "codiceOggetto").IsGreaterThan(0);

			StringBuilder sb = new StringBuilder(Urls.Edit);

			sb.AppendFormat("?{0}={1}", PathUtils.UrlParameters.IdComune, idComune)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Software, software)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Token, token)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdPresentazione, idPresentazione)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.CodiceOggetto, codiceOggetto)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.PdfCompilabile, pdfCompilabile ? PathUtils.UrlParametersValues.True : PathUtils.UrlParametersValues.False)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond)
			  
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.ReturnTo, returnTo);

			return sb.ToString();
		}

		public string GetUrlEdit(string idComune, string software, string token, int idPresentazione, int idAllegato, string tipoAllegato, string returnTo)
		{
			Condition.Requires(idComune, "idComune").IsNotNullOrEmpty();
			Condition.Requires(software, "software").IsNotNullOrEmpty();
			Condition.Requires(token, "token").IsNotNullOrEmpty();
			Condition.Requires(idAllegato, "idAllegato").IsGreaterThan(0);

			StringBuilder sb = new StringBuilder(Urls.Edit);

			sb.AppendFormat("?{0}={1}", PathUtils.UrlParameters.IdComune, idComune)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Software, software)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Token, token)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdPresentazione, idPresentazione)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.TipoAllegato, tipoAllegato)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdAllegato, idAllegato)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.ReturnTo, returnTo);

			return sb.ToString();
		}
	}
}
