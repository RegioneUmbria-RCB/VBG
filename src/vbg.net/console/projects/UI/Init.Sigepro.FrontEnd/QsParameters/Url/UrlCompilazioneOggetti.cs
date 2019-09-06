using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters.Url
{
    public class UrlCompilazioneOggetti
    {
        IConfigurazione<ParametriUrlAreaRiservata> _configurazioneUrl;
        IResolveHttpContext _resolveContext;

        public UrlCompilazioneOggetti(IConfigurazione<ParametriUrlAreaRiservata> configurazioneUrl, IResolveHttpContext context)
        {
            this._configurazioneUrl = configurazioneUrl;
            this._resolveContext = context;
        }

        public void Redirect(string idComune, string software, int idPresentazione, int idAllegato, string tipoAllegato)
        {
            var context = this._resolveContext.Get;

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