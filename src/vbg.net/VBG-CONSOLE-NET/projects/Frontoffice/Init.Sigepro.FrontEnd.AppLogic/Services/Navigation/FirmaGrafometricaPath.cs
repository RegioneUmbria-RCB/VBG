using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
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
            public const string HandlerUploadFileFirmato = "~/Reserved/InserimentoIstanza/FirmaGrafometrica/UploadHandler.ashx";
        }

        IAliasSoftwareResolver _aliasSoftwareResolver;

        public FirmaGrafometricaPath(IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public string UploadHandler(int codiceOggetto, int idDomanda)
        {
            var url = UrlBuilder.Url(Urls.HandlerUploadFileFirmato, x =>
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
