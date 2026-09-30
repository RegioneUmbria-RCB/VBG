using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters.Url
{
    public class UrlCompilazioneOggetti
    {
        private readonly IConfigurazione<ParametriUrlAreaRiservata> _configurazioneUrl;

        public UrlCompilazioneOggetti(IConfigurazione<ParametriUrlAreaRiservata> configurazioneUrl)
        {
            this._configurazioneUrl = configurazioneUrl;
        }

        public void Redirect(string idComune, string software, int idPresentazione, int idAllegato, string tipoAllegato)
        {
            var context = HttpContext.Current;

            var returnUrl = new QsReturnTo(context.Request.Url.ToString());

            var url = new UrlBuilder().Build(this._configurazioneUrl.Parametri.EditOggetti, qs =>
            {
                qs.Add(new QsAliasComune(idComune));
                qs.Add(new QsSoftware(software));
                qs.Add(new QsIdDomandaOnline(idPresentazione));
                qs.Add("idallegato", idAllegato);
                qs.Add("tipoallegato", tipoAllegato);
                qs.Add(new QsTimestamp());
                qs.Add(returnUrl);
            });

            context.Response.Redirect(url, true);
        }
    }
}