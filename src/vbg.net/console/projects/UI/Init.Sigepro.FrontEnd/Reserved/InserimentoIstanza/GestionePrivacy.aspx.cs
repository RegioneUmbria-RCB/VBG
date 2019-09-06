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
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestionePrivacy : IstanzeStepPage
	{
        private static class Constants
        {
            public const string ResponsabileTrattamento = "{responsabile_trattamento}";
            public const string DPO = "{dpo}";
            public const string DenominazioneComune = "{denominazione_comune}";
        }


		[Inject]
		public CondizioneIngressoStepSempreVera _condizioneIngresso { get; set; }

		[Inject]
		public CondizioneUscitaPrivacyAccettata _condizioneUscita { get; set; }

        [Inject]
        public IComuniService _comuniService { get; set; }

		[Inject]
		public DatiDomandaService DatiDomandaService { get; set; }
        [Inject]
        public IConfigurazione<ParametriVbg> _configurazioneVbg { get; set; }

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
                ltrTestoPrivacy.Text = RisolviSegnaposto(value); 
            } 
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

        private string RisolviSegnaposto(string value)
        {
            value = value.Replace(Constants.DenominazioneComune, this._configurazioneVbg.Parametri.DenominazioneComune);
            value = value.Replace(Constants.ResponsabileTrattamento, this._configurazioneVbg.Parametri.ResponsabileTrattamento);
            value = value.Replace(Constants.DPO, this._configurazioneVbg.Parametri.DataProtectionOfficer);

            return value;
        }
    }
}
