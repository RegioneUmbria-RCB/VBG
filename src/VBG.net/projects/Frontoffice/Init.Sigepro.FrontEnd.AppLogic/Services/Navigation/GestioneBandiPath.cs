// -----------------------------------------------------------------------
// <copyright file="GestioneBandiPath.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class GestioneBandiPath
	{
		private static class Urls
		{
			public const string DownloadPdfCompilabileAzienda = "~/Reserved/InserimentoIstanza/GestioneBandiUmbria/DownloadPdfCompilabileAzienda.ashx";
		}

		public string GetUrlDownloadPdfCompilabileAzienda(string idComune, string token, string software, int codiceOggetto, int idDomanda, string cfAzienda, string ivaAzienda)
		{
			var sb = new StringBuilder(Urls.DownloadPdfCompilabileAzienda);
			sb.AppendFormat("?{0}={1}", PathUtils.UrlParameters.IdComune, idComune)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Token, token)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Software, software)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.CodiceOggetto, codiceOggetto)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdPresentazione, idDomanda)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.CodiceFiscaleAzienda, cfAzienda)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.PartitaIvaAzienda, ivaAzienda)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);

			return sb.ToString();
		}
	}
}
