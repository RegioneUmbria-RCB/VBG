using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Exceptions;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestionePrivacy : IstanzeStepPage
	{
		[Inject]
		public CondizioneIngressoStepSempreVera _condizioneIngresso { get; set; }

		[Inject]
		public CondizioneUscitaPrivacyAccettata _condizioneUscita { get; set; }

        [Inject]
        public IComuniService _comuniService { get; set; }

		[Inject]
		public DatiDomandaService DatiDomandaService { get; set; }

		public string MessaggioErrore
		{
			get { return ViewstateGet("MessaggioErrore", ""); }
			set { ViewStateSet("MessaggioErrore",value); }
		}

        public string TestoAccettazionePrivacy 
        { 
            get 
            { 
                return chkAccetto.Text; 
            } 
            set 
            { 
                chkAccetto.Text = "&nbsp;<b>" + value + "</b>"; 
            } 
        }
        public string TestoPrivacy 
        { 
            get 
            { 
                return ltrTestoPrivacy.Text; 
            } 
            set 
            { 
                ltrTestoPrivacy.Text = String.Format(value, GetNomeComune()); 
            } 
        }
        public bool MostraCheckAccettazione
        {
            get { object o = this.ViewState["MostraCheckAccettazione"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraCheckAccettazione"] = value; }
        }




        protected void Page_Load(object sender, EventArgs e)
		{
			// il service si occupa del salvataggio dei dati
			this.Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			chkAccetto.Checked = ReadFacade.Domanda.AltriDati.FlagPrivacy;

            if (!this.MostraCheckAccettazione)
            {
                chkAccetto.Checked = true;
                chkAccetto.Visible = false;
            }
		}

		public override bool CanEnterStep()
		{
			return _condizioneIngresso.Verificata();
		}

		public override bool CanExitStep()
		{
			try
			{
				_condizioneUscita.MessaggioErrore = this.MessaggioErrore;

				return _condizioneUscita.Verificata();
			}
			catch (StepException ex)
			{
				Errori.AddRange(ex.ErrorMessages);

				return false;
			}
		}

		public override void OnBeforeExitStep()
		{
			DatiDomandaService.SetFlagPrivacy(IdDomanda, chkAccetto.Checked);
		}

        private string GetNomeComune()
        {
            var comune = _comuniService.GetDatiComune(ReadFacade.Domanda.AltriDati.CodiceComune);

            if (comune == null)
            {
                return String.Empty;
            }

            return comune.Comune;
        }
	}
}
