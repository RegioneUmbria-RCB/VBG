// -----------------------------------------------------------------------
// <copyright file="GestioneBandiPath.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
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

        IAliasSoftwareResolver _aliasSoftwareResolver;

        public GestioneBandiPath(IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public string GetUrlDownloadPdfCompilabileAzienda(int codiceOggetto, int idDomanda, string cfAzienda, string ivaAzienda)
        {
            var url = UrlBuilder.Url(Urls.DownloadPdfCompilabileAzienda, x =>
            {
                x.Add(PathUtils.UrlParameters.IdComune, this._aliasSoftwareResolver.AliasComune);
                x.Add(PathUtils.UrlParameters.Software, this._aliasSoftwareResolver.Software);
                x.Add(PathUtils.UrlParameters.CodiceOggetto, codiceOggetto);
                x.Add(PathUtils.UrlParameters.IdPresentazione, idDomanda);
                x.Add(PathUtils.UrlParameters.CodiceFiscaleAzienda, cfAzienda);
                x.Add(PathUtils.UrlParameters.PartitaIvaAzienda, ivaAzienda);
                x.Add(PathUtils.UrlParameters.Timestamp, DateTime.Now.Millisecond);
            });

            return url;
        }
    }
}
