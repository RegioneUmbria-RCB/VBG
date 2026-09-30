using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.visura
{
    public partial class attivaVisura : BasePage
    {
        [Inject]
        protected IVbgAuthenticationService _authenticationService { get; set; }
        [Inject]
        protected IConfigurazione<ParametriUrlAreaRiservata> _parametriUrl { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var alias = this.Page.RouteData.Values["alias"].ToString();
            var software = this.Page.RouteData.Values["software"].ToString();
            var uuid = this.Page.RouteData.Values["uuid"]?.ToString();

            this._authenticationService.LoginAnonimo(alias);

            var url = String.Empty;

            if (String.IsNullOrEmpty(uuid))
            {
                url = UrlBuilder.Url("~/Reserved/ArchivioPratiche.aspx", qs =>
                {
                    qs.Add(new QsAliasComune(alias));
                    qs.Add(new QsSoftware(software));
                    qs.Add("popup", 1);
                });

                this.Response.Redirect(url);

                return;
            }

            url = UrlBuilder.Url(this._parametriUrl.Parametri.VisuraAutenticata, qs =>
            {
                qs.Add(new QsAliasComune(alias));
                qs.Add(new QsSoftware(software));
                qs.Add(new QsUuidIstanza(uuid));
                qs.Add("visura", "1");
            });

            this.Response.Redirect(url);

        }
    }
}