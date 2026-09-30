using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
// using Init.Sigepro.FrontEnd.AppLogic.Configuration;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class _default : ReservedBasePage
    {
        [Inject]
        public IConfigurazione<ParametriAreaRiservata> _configurazione { get; set; }


        protected void Page_Load(object sender, EventArgs e)
        {
            var page = this._configurazione.Parametri.PaginaIniziale;

            if (!String.IsNullOrEmpty(page))
            {
                var newUrl = UrlBuilder.Url(page, x =>
                {
                    x.Add(new QsAliasComune(this.Request.QueryString));
                    x.Add(new QsSoftware(this.Request.QueryString));
                });

                this.Response.Redirect(newUrl);
            }
        }
    }
}
