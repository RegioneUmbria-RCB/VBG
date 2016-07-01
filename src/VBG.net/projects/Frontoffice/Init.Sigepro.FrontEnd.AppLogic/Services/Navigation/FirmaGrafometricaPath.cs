using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
	public class FirmaGrafometricaPath
	{
		public static class Urls
		{
			public const string HandlerUploadFileFirmato= "~/Reserved/InserimentoIstanza/FirmaGrafometrica/UploadHandler.ashx";
		}

		public string UploadHandler(string idComune, string token, string software, int codiceOggetto, int idDomanda)
		{
			var sb = new StringBuilder(Urls.HandlerUploadFileFirmato);
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
