namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
	using System.Text;

	public class ReservedPath
	{
		private static class Urls
		{
			public const string MostraOggettoFo = "~/Reserved/MostraOggettoFo.ashx";
			public const string MostraOggettoFoFirmato = "~/Reserved/MostraOggettoFoFirmato.aspx";
		}

		public InserimentoIstanzaPath InserimentoIstanza { get; private set; }

		public ReservedPath()
		{
			this.InserimentoIstanza = new InserimentoIstanzaPath();
		}

		public string GetUrlMostraOggettoFo(string idComune, string token, string software, int codiceOggetto, int idPresentazione, string convert = "")
		{
			var sb = new StringBuilder(Urls.MostraOggettoFo);

			sb.AppendFormat("?{0}={1}", PathUtils.UrlParameters.IdComune, idComune)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Token, token)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Software, software)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.CodiceOggetto, codiceOggetto)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdPresentazione, idPresentazione)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Convert, convert);

			return sb.ToString();
		}

		public string GetUrlMostraOggettoFoFirmato(string idComune, string token, string software, int codiceOggetto, int idPresentazione)
		{
			var sb = new StringBuilder(Urls.MostraOggettoFoFirmato);

			sb.AppendFormat("?{0}={1}", PathUtils.UrlParameters.IdComune, idComune)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Token, token)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.Software, software)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.CodiceOggetto, codiceOggetto)
			  .AppendFormat("&{0}={1}", PathUtils.UrlParameters.IdPresentazione, idPresentazione);

			return sb.ToString();
		}
	}
}
