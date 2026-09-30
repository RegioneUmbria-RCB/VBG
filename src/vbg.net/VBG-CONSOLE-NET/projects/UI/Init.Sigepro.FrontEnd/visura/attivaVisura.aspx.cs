using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.visura
{
    public partial class attivaVisura : BasePage
    {
        [Inject]
        protected VbgAuthenticationService _authenticationService { get; set; }
        [Inject]
        protected IConfigurazione<ParametriUrlAreaRiservata> _parametriUrl { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var alias = Page.RouteData.Values["alias"].ToString();
            var software = Page.RouteData.Values["software"].ToString();
            var uuid = Page.RouteData.Values["uuid"]?.ToString();

            _authenticationService.LoginAnonimo(alias);

            var url = String.Empty;

            if (String.IsNullOrEmpty(uuid))
            {
                url = UrlBuilder.Url("~/Reserved/ArchivioPratiche.aspx", qs => {
                    qs.Add(new QsAliasComune(alias));
                    qs.Add(new QsSoftware(software));
                    qs.Add("popup", 1);
                });

                Response.Redirect(url);

                return;
            }

            url = UrlBuilder.Url(_parametriUrl.Parametri.VisuraAutenticata, qs => {
                qs.Add(new QsAliasComune(alias));
                qs.Add(new QsSoftware(software));
                qs.Add(new QsUuidIstanza(uuid));
                qs.Add("visura","1");
            });

            Response.Redirect(url);
            
        }
    }
}