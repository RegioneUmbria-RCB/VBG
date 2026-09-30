using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.CSSSchede;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class GenerazioneHtmlDomandaConstants
    {
        private const string _wrappingDivHtml = "<div id='datiDinamici'>{0}</div>";
        public const string SegnapostoSchedeDinamiche1 = "<schedeDinamiche />";
        public const string SegnapostoSchedeDinamiche2 = "<schedeDinamiche></schedeDinamiche>";
        public const string HtmlWrapper = @"<!doctype html>
<html lang=""en"">
	<head>
		<meta charset=""UTF-8"">
	</head>
	<body>
	{1}
	</body>
</html>";

        private readonly IAppConfigurationReader _appConfigurationReader;

        public GenerazioneHtmlDomandaConstants(IAppConfigurationReader appConfigurationReader)
        {
            this._appConfigurationReader = appConfigurationReader;
        }

        private bool IgnoraCssModelliDinamici
        {
            get
            {
                var setting = this._appConfigurationReader.GetSetting("generazionePdfDatiDinamici.ignoraCssModelliDinamici");

                if (String.IsNullOrEmpty(setting) || setting.ToUpper() != "TRUE")
                    return false;

                return true;
            }
        }

        public string WrappingDivHtml
        {
            get
            {
                if (this.IgnoraCssModelliDinamici)
                {
                    return "{0}";
                }

                return _wrappingDivHtml;
            }
        }

        public string CssModelliDinamici
        {
            get
            {
                if (this.IgnoraCssModelliDinamici)
                {
                    return String.Empty;
                }

#if NETFRAMEWORK
                return CSSSchedeFramework.CSS;
#else
                return CSSSchedeCore.CSS;
#endif
            }
        }
    }
}
