// -----------------------------------------------------------------------
// <copyright file="EncodingUtils.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
    using log4net;
    using System;
    using System.Text;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class EncodingUtils
    {
        private static class Constants
        {
            public const string SettingsKey = "RiepiloghiSchedeDinamiche.Encoding";
            public static readonly Encoding DefaultEncoding = Encoding.Default;
        }

        public static Encoding GetEncoding(IAppConfigurationReader appConfigurationReader)
        {
            var log = LogManager.GetLogger(typeof(EncodingUtils));

            var settings = appConfigurationReader.GetSetting(Constants.SettingsKey);

            if (String.IsNullOrEmpty(settings))
            {
                log.DebugFormat("La conversione dell'html del riepilogo della scheda dinamica verrà effettuata con il charset {0} (default)", Constants.DefaultEncoding.WebName);
                return Constants.DefaultEncoding;
            }

            log.DebugFormat("La conversione dell'html del riepilogo della scheda dinamica verrà effettuata con il charset {0}", settings);

            return Encoding.GetEncoding(settings);
        }
    }
}
