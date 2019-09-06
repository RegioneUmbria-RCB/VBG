using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Cid;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using System.Security.Authentication;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.FirmaCidPin
{
    public partial class Firma : IstanzeStepPage
    {
        private static class Constants
        {
            public const int ViewIdErrore = 1;
            public const int ViewIdFirma = 0;
        }

        [Inject]
        protected FirmaCidPinService _service { get; set; }
        [Inject]
        protected IWorkflowService _workflowService { get; set; }
        [Inject]
        protected PathUtils _pathUtils { get; set; }

        protected string ReturnTo
        {
            get
            {
                return Request.QueryString[PathUtils.UrlParameters.ReturnTo];
            }
        }

        protected int CodiceOggetto
        {
            get
            {
                return Convert.ToInt32(Request.QueryString[PathUtils.UrlParameters.CodiceOggetto]);
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                VerificaPresenzaSchedaConEstremiDocumento();
            }
        }

        private void VerificaPresenzaSchedaConEstremiDocumento()
        {
            if (!this._service.VerificaPresenzaSchedaConEstremiDocumento(IdDomanda))
            {
                MostraVistaErrore();
            }
            else
            {
                MostraVistaFirma();
            }
        }

        private void MostraVistaFirma()
        {
            multiView.ActiveViewIndex = Constants.ViewIdFirma;
        }

        private void MostraVistaErrore()
        {
            multiView.ActiveViewIndex = Constants.ViewIdErrore;
        }

        protected void GoBackToCallingPage(object sender, EventArgs e)
        {
            HttpContext.Current.Response.Redirect(ReturnTo);
        }

        protected string GetNomeSchedaDinamica()
        {
            return this._service.GetNomeSchedaDinamicaEstremiDocumento(IdDomanda);
        }

        protected int GetIndiceSchedaDinamica()
        {
            return this._workflowService.GetCurrentWorkflow().TrovaIndiceStepDaUrlParziale("/GestioneDatiDinamici.aspx");
        }

        protected void cmdVaiAllaScheda_Click(object sender, EventArgs e)
        {
            var stepId = GetIndiceSchedaDinamica();

            string stepUrl = _workflowService.GetCurrentWorkflow().GetStepUrl(stepId);

            var url = UrlBuilder.Url(stepUrl, pb =>
            {
                pb.Add(new QsAliasComune(IdComune));
                pb.Add(new QsSoftware(Software));
                pb.Add(new QsStepId(stepId));
                pb.Add(new QsIdDomandaOnline(IdDomanda));
            });

            HttpContext.Current.Response.Redirect(url);
        }

        protected void cmdFirma_Click(object sender, EventArgs e)
        {
            var cid = txtCid.Text;
            var pin = txtPin.Text;

            if (String.IsNullOrEmpty(cid))
            {
                this.Errori.Add("Il CID immesso non è valido");
                return;
            }

            if (String.IsNullOrEmpty(pin))
            {
                this.Errori.Add("Il PIN immesso non è valido");
                return;
            }


			try
			{
				this._service.Firma(IdDomanda, cid, pin, CodiceOggetto);
			}
			catch (AuthenticationException ex)
			{
				this.Errori.Add("Autenticazione fallita, verificare i valori immessi e riprovare");
				return;
			}
			catch (Exception ex)
			{
				this.Errori.Add("Si è verificato un errore imprevisto durante l'operazione di firma");
				return;
			}

			GoBackToCallingPage(this, EventArgs.Empty);				
        }

        public string GetUrlMostraOggetto()
        {
            return ResolveClientUrl(this._pathUtils.Reserved.GetUrlMostraOggettoFo(CodiceOggetto, IdDomanda));
        }
        
    }
}