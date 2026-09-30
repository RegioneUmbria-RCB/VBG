using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.GestioneFirma.FirmaGrafometrica;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using log4net;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.FirmaGrafometrica
{
    public partial class Firma : IstanzeStepPage
    {
        private static class Constants
        {
            public const int ViewIdErrore = 1;
            public const int ViewIdFirma = 0;
		}

        [Inject]
        protected FirmaGrafometricaService _service { get; set; }
        [Inject]
        protected IWorkflowService _workflowService { get; set; }
		[Inject]
		protected IVerificaFirmaDigitaleService _firmaDigitaleService { get; set; }
        [Inject]
        protected PathUtils _pathUtils { get; set; }

		ILog _log = LogManager.GetLogger(typeof(Firma));


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

		public string SignatureId
		{
			get { object o = this.ViewState["SignatureId"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["SignatureId"] = value; }
		}


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
				this.SignatureId = Guid.NewGuid().ToString();
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

            var stepUrl = _workflowService.GetCurrentWorkflow().GetStepUrl(stepId);

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
        }

        public string GetUrlMostraOggetto()
        {
			return ToAbsoluteUrl(this._pathUtils.Reserved.GetUrlMostraOggettoFo(CodiceOggetto, IdDomanda));
        }

		public string GetUrlUploadOggetto()
		{
			return ToAbsoluteUrl(this._pathUtils.Reserved.InserimentoIstanza.FirmaGrafometrica.UploadHandler(CodiceOggetto, IdDomanda));
		}

		protected void hidEsito_ValueChanged(object sender, EventArgs e)
		{
			if (this.hidEsito.Value != this.SignatureId)
			{
				Errori.Add("Si è verificato un errore durante l'operazione di firma");
				return;
			}

			try
			{
				// Verifico la firma digitale
				/*var esitoVerificaFirma = this._firmaDigitaleService.VerificaFirmaDigitale(CodiceOggetto);

				if (!new FirmaValidaSpecification().IsSatisfiedBy(esitoVerificaFirma))
				{
					this._log.ErrorFormat("FirmaDocumentoViewModel->AggiornaStatoFirma: Il file firmato tramite firma grafometrica non dispone di una firma digitale valida. Id utente={0}, codiceoggetto={1}, errore={2}", this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale, CodiceOggetto, esitoVerificaFirma.Errore);

					throw new FirmaDigitaleNonValidaException(esitoVerificaFirma.Errore);
				}*/

				GoBackToCallingPage(this, EventArgs.Empty);
			}
			catch (FirmaDigitaleNonValidaException ex)
			{
				this.Errori.Add(ex.Message);
			}
		}

		private string ToAbsoluteUrl(string relative)
		{
			var pre = HttpContext.Current.Request.Url.Scheme
						+ "://"
						+ HttpContext.Current.Request.Url.Authority
						+ HttpContext.Current.Request.ApplicationPath;

			return pre + relative.Replace("~", String.Empty);
		}

    }
}