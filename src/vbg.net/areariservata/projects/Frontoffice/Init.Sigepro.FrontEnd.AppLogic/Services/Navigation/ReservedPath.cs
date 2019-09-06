namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
    using System.Text;

	public class ReservedPath
	{
		private static class Urls
		{
			public const string MostraOggettoFo = "~/Reserved/MostraOggettoFo.ashx";
			public const string MostraOggettoFoFirmato = "~/Reserved/MostraOggettoFoFirmato.aspx";
		}

		public InserimentoIstanzaPath InserimentoIstanza { get; private set; }

        IAliasSoftwareResolver _aliasSoftwareResolver;


        public ReservedPath(IAliasSoftwareResolver aliasSoftwareResolver)
		{
			this.InserimentoIstanza = new InserimentoIstanzaPath(aliasSoftwareResolver);
            this._aliasSoftwareResolver = aliasSoftwareResolver;
		}

		public string GetUrlMostraOggettoFo(int codiceOggetto, int idPresentazione, string convert = "")
		{
            var url = UrlBuilder.Url(Urls.MostraOggettoFo, x =>
            {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idPresentazione);
                x.Add(PathUtils.UrlParameters.Convert, convert);
            });

            return url;
        }

		public string GetUrlMostraOggettoFoFirmato(int codiceOggetto, int idPresentazione)
		{
            var url = UrlBuilder.Url(Urls.MostraOggettoFoFirmato, x =>
            {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idPresentazione);
            });

            return url;
        }
	}
}
