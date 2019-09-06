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
    public class UrlFirmaDigitaleDocumento
    {
        IConfigurazione<ParametriUrlAreaRiservata> _configurazioneUrl;
        IResolveHttpContext _resolveContext;

        public UrlFirmaDigitaleDocumento(IConfigurazione<ParametriUrlAreaRiservata> configurazioneUrl, IResolveHttpContext context)
        {
            this._configurazioneUrl = configurazioneUrl;
            this._resolveContext = context;
        }

        public void Redirect(QsAliasComune alias, QsSoftware software, QsIdDomandaOnline idDomanda, QsCodiceOggetto codiceOggetto)
        {
            var context = this._resolveContext.Get;

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