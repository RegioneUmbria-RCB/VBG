using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;


namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Paths
{
    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class GestioneBandiPath
    {
        public static class Constants
        {
            public const string DownloadPdfCompilabileAzienda = "~/Reserved/InserimentoIstanza/GestioneBandiUmbria/DownloadPdfCompilabileAzienda.ashx";
            public const string CodiceFiscaleAzienda = "cfAzienda";
            public const string PartitaIvaAzienda = "ivaAzienda";
            public const string Timestamp = "_ts";
        }

        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;

        public GestioneBandiPath(IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public string GetUrlDownloadPdfCompilabileAzienda(int codiceOggetto, int idDomanda, string cfAzienda, string ivaAzienda)
        {
            var url = UrlBuilder.Url(Constants.DownloadPdfCompilabileAzienda, x =>
            {
                x.Add("idcomune", this._aliasSoftwareResolver.AliasComune);
                x.Add("software", this._aliasSoftwareResolver.Software);
                x.Add("codiceoggetto", codiceOggetto);
                x.Add("idpresentazione", idDomanda);
                x.Add(Constants.CodiceFiscaleAzienda, cfAzienda);
                x.Add(Constants.PartitaIvaAzienda, ivaAzienda);
                x.Add(Constants.Timestamp, DateTime.Now.Millisecond);
            });

            return url;
        }
    }
}
