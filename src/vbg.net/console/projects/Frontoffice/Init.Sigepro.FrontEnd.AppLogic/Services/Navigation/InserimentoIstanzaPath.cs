namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
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

        IAliasSoftwareResolver _aliasSoftwareResolver;

        public InserimentoIstanzaPath(IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this.EditOggetti = new EditOggettiPath(aliasSoftwareResolver);
            this.GestioneBandi = new GestioneBandiPath(aliasSoftwareResolver);
            this.FirmaGrafometrica = new FirmaGrafometricaPath(aliasSoftwareResolver);

            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public string GetUrlDownloadPdfCompilabile(int codiceOggetto, int idDomanda)
        {
            var url = UrlBuilder.Url(Urls.DownloadPdfcompilabile, x =>
            {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idDomanda);
                x.Add(PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);
            });

            return url;
        }

        public string GetUrlDownloadOggettoCompilabile(int codiceOggetto, int idDomanda, string formato)
        {
            var url = UrlBuilder.Url(Urls.DownloadOggettoCompilabile, x =>
            {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idDomanda);
                x.Add(PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);
                x.Add(PathUtils.UrlParameters.Fmt, formato);
            });

            return url;
        }

        public string GetUrlDownloadOggetto(int codiceOggetto, int idDomanda)
        {
            var url = UrlBuilder.Url(Urls.DownloadOggetto, x =>
            {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idDomanda);
                x.Add(PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);
            });

            return url;
        }
    }
}
