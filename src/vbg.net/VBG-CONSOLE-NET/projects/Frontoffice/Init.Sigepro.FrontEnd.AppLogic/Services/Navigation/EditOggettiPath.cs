namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
    using System;
    using System.Text;
    using CuttingEdge.Conditions;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;

    public class EditOggettiPath
    {
        private static class Urls
        {
            public const string Upload = "~/Reserved/InserimentoIstanza/EditOggetti/Upload.ashx";
            public const string Edit = "~/Reserved/InserimentoIstanza/EditOggetti/Edit.aspx";
        }

        IAliasSoftwareResolver _aliasSoftwareResolver;

        public EditOggettiPath(IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public string GetUrlUpload(int idAllegato, string tipoAllegato, int idDomanda, string nomefile, int? codiceOggetto)
        {
            var url = UrlBuilder.Url(Urls.Upload, x =>
            {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.IdAllegato, idAllegato);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idDomanda);
                x.Add(PathUtils.UrlParameters.TipoAllegato, tipoAllegato);
                x.Add(PathUtils.UrlParameters.NomeFile, nomefile);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);
            });

            return url;
        }

        public string GetUrlEdit(int idPresentazione, int codiceOggetto, bool pdfCompilabile, string returnTo)
        {
            Condition.Requires(codiceOggetto, "codiceOggetto").IsGreaterThan(0);

            var url = UrlBuilder.Url(Urls.Edit, x =>
            {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idPresentazione);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.PdfCompilabile, pdfCompilabile ? PathUtils.UrlParametersValues.True : PathUtils.UrlParametersValues.False);
                x.Add(PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);
                x.Add(PathUtils.UrlParameters.ReturnTo, returnTo);

            });

            return url;
        }

        public string GetUrlEdit(int idPresentazione, int idAllegato, string tipoAllegato, string returnTo)
        {
            Condition.Requires(idAllegato, "idAllegato").IsGreaterThan(0);
            
            var url = UrlBuilder.Url(Urls.Edit, x =>
            {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idPresentazione);
                x.Add(PathUtils.UrlParameters.TipoAllegato, tipoAllegato);
                x.Add(PathUtils.UrlParameters.IdAllegato, idAllegato);
                x.Add(PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);
                x.Add(PathUtils.UrlParameters.ReturnTo, returnTo);
            });


            return url;
        }

    }
}
