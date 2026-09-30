using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.Infrastructure.Web;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP
{
    public class DownloadPdfDomanda : IDownloadPdfDomanda
    {
        private static class Constants
        {
            // http://siena.staging.ldpgis.it/presentazione_pratiche_edilizie_online/pratiche/report_pratica_pdf_connector.php?identificativo_temporaneo={idDomandaEsteso}
            public static string SegnapostoIdentificativoDomanda = "{idDomandaEsteso}";
            public static string FileName = "DatiDomanda.pdf";
            public static string FileMimeType = "application/pdf";
        }

        IConfigurazione<ParametriIntegrazioneLDP> _config;
        ILog _log = LogManager.GetLogger(typeof(DownloadPdfDomanda));

        public DownloadPdfDomanda(IConfigurazione<ParametriIntegrazioneLDP> config)
        {
            this._config = config;
        }

        public BinaryFile FromIdentificativoTemporaneo(string identificativoTemporaneo)
        {
            var url = this._config.Parametri.UrlDownloadPdf.Replace(Constants.SegnapostoIdentificativoDomanda, identificativoTemporaneo);

            this._log.DebugFormat("Inizio download del pdf compilato dall'indirizzo {0}", url);

            using (var downloader = new WebDownloader())
            {
                downloader.Download(url);

                return new BinaryFile(Constants.FileName, Constants.FileMimeType, downloader.GetBytes());
            }
        }
    }
}
