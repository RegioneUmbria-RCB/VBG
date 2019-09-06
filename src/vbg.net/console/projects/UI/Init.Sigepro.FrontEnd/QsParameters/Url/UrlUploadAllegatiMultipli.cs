using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters.Url
{
    public class UrlUploadAllegatiMultipli
    {
        IConfigurazione<ParametriUrlAreaRiservata> _configurazioneUrl;
        IResolveHttpContext _resolveContext;

        public UrlUploadAllegatiMultipli(IConfigurazione<ParametriUrlAreaRiservata> configurazioneUrl, IResolveHttpContext context)
        {
            this._configurazioneUrl = configurazioneUrl;
            this._resolveContext = context;
        }

        public void Redirect(string alias, string software, int idDomanda, string origine, int idAllegato)
        {
            var context = this._resolveContext.Get;

            var returnUrl = new QsReturnTo(context.Request.Url.ToString());
            
            var src = origine + idAllegato.ToString();

            var url = new UrlBuilder().Build(this._configurazioneUrl.Parametri.UploadAllegatiMultipli, qs =>
            {
                qs.Add(new QsAliasComune(alias));
                qs.Add(new QsSoftware(software));
                qs.Add("src", src);
                qs.Add(new QsIdDomandaOnline(idDomanda));
                qs.Add(returnUrl);
            });

            context.Response.Redirect(url, true);
        }
    }
}