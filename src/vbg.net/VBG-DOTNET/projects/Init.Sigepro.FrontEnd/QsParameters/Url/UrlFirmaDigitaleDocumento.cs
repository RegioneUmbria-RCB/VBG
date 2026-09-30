using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters.Url
{
    public class UrlFirmaDigitaleDocumento
    {
        private readonly IConfigurazione<ParametriUrlAreaRiservata> _configurazioneUrl;

        public UrlFirmaDigitaleDocumento(IConfigurazione<ParametriUrlAreaRiservata> configurazioneUrl)
        {
            this._configurazioneUrl = configurazioneUrl;
        }

        public void Redirect(QsAliasComune alias, QsSoftware software, QsIdDomandaOnline idDomanda, QsCodiceOggetto codiceOggetto)
        {
            var context = HttpContext.Current;

            var returnUrl = new QsReturnTo(context.Request.Url.ToString().Replace("&AllegaRiepilogo=True", string.Empty));

            var url = new UrlBuilder().Build(this._configurazioneUrl.Parametri.FirmaDigitale, qs =>
            {
                qs.Add(alias);
                qs.Add(software);
                qs.Add(codiceOggetto);
                qs.Add(idDomanda);
                qs.Add(returnUrl);
            });

            context.Response.Redirect(url, true);
        }
    }
}