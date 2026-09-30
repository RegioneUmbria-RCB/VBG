using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.RiprendiDomanda
{
    public partial class cartografico_return : Ninject.Web.PageBase
    {
        [Inject]
        protected IWorkflowService _workflowService { get; set; }

        [Inject]
        protected IAreaRiservataAuthenticationService _areaRiservataAuthenticationService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            var serializationCode = this.Page.RouteData.Values["idDomanda"].ToString();
            var token = (this.Page.RouteData.Values["token"] ?? String.Empty).ToString();
            var stepId = this.Page.RouteData.Values["idStep"].ToString();
            var uuidLocalizzazione = this.Page.RouteData.Values["uuidLocalizzazione"].ToString();

            this._areaRiservataAuthenticationService.AuthenticateUser(token);

            var dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(serializationCode);
            HttpContext.Current.Items["alias"] = dataKey.IdComune;
            HttpContext.Current.Items["software"] = dataKey.Software;

            this._workflowService.ClearCacheDomanda(dataKey.IdPresentazione);

            var ub = new UrlBuilder();

            var url = ub.Build("~/reserved/InserimentoIstanza/GestioneLocalizzazioniCartografico.aspx", pars =>
            {
                pars.Add(QsAliasComune.QuerystringParameterName, dataKey.IdComune);
                pars.Add(QsSoftware.QuerystringParameterName, dataKey.Software);
                pars.Add(QsIdDomandaOnline.QuerystringParameterName, dataKey.IdPresentazione);
                pars.Add(QsReturning.QuerystringParameterName, "true");
                pars.Add(QsStepId.QuerystringParameterName, stepId);
                pars.Add(QsUuidLocalizzazione.QuerystringParameterName, uuidLocalizzazione);
            });

            this.Response.Redirect(url);
        }
    }
}