using System;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda.CopiaDomanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class Benvenuto : IstanzeStepPage
	{
		private static class Constants
		{
			public const string MsgErroreComuneNonSelezionato = "Per poter proseguire è necessario selezionare il comune per cui si vuole presentare l'istanza";
			public const string QsParamSelezionaIntervento = "SelezionaIntervento";
            public const string UrlBenvenutoStar = "~/reserved/inserimentoIstanza/BenvenutoSTAR.aspx";

            public const int ViewIdCopiaDa = 0;
            public const int ViewIdSelezionaComune = 1;
		}


		[Inject]
		public CondizioneIngressoStepSempreVera _condizioneIngresso { get; set; }
		[Inject]
		public ComuneDiPresentazioneSelezionato _condizioneUscita { get; set; }
		[Inject]
		public DatiDomandaService DatiDomandaService { get; set; }
		[Inject]
        public IWorkflowService WorkflowService { get; set; }
        [Inject]
        public STARUrlService _starUrlService { get; set; }
        [Inject]
        public ISalvataggioDomandaStrategy _salvataggioDomandaStrategy { get; set; }
        [Inject]
        protected ICopiaDatiDomandaService _copiaDomandaService { get; set; }

        protected QsCopiaDa CopiaDa
        {
            get { return new QsCopiaDa(Request.QueryString); }
        }

        protected bool NoStar
        {
            get
            {
                return Request.QueryString["star"] == "0";
            }
        }


		protected int? SelezionaIntervento
		{
			get
			{
				try
				{
					var qs = Request.QueryString[Constants.QsParamSelezionaIntervento];

					if (string.IsNullOrEmpty(qs))
						return (int?)null;

					return Convert.ToInt32(qs);
				}
				catch (FormatException)
				{
					return (int?)null;
				}
			}
		}

		public string TestoDescrizioneSteps
		{
			get { object o = this.ViewState["TestoDescrizioneSteps"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["TestoDescrizioneSteps"] = value; }
		}

        public string SelezionaComune
        {
            get { object o = this.ViewState["SelezionaComune"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["SelezionaComune"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
		{
			// il service si occupa del salvataggio dei dati
			this.Master.IgnoraSalvataggioDati = true;

            if (!IsPostBack)
            {
                VerificaRedirectStar();

                if (MostraCopiaDa())
                {
                    DatabindCopiaDa();
                }
                else
                {
                    DataBind();
                }
                
            }
		}

        private void VerificaRedirectStar()
        {
            if (!NoStar && this._starUrlService.StarAttivo())
            {
                var qs = Request.QueryString.ToString();
                Response.Redirect(String.Format("{0}?{1}", Constants.UrlBenvenutoStar, qs));
                Response.End();
            }
        }

		#region ciclo di vita della pagina
		public override bool CanEnterStep()
		{
			return _condizioneIngresso.Verificata();
		}

		public override void OnBeforeExitStep()
		{
			DatiDomandaService.SetCodiceComune(IdDomanda, cmbComuni.SelectedValue);

			if (SelezionaIntervento.HasValue)
			{
				DatiDomandaService.ImpostaIdIntervento(IdDomanda, SelezionaIntervento.Value, (int?)null, true);
			}

            if (this.CopiaDa.HasValue)
            {
                DatiDomandaService.ImpostaIdDomandaCollegata(IdDomanda, this.CopiaDa.Value);
            }

        }

		public override bool CanExitStep()
		{
			if (!_condizioneUscita.Verificata())
			{
				this.Errori.Add(Constants.MsgErroreComuneNonSelezionato);
				return false;
			}

            

			return true;
		}
		#endregion

		public override void DataBind()
		{
            this.Master.MostraPaginatoreSteps = true;
            multiView.ActiveViewIndex = Constants.ViewIdSelezionaComune;

			ltrTestoListaStep.Text = PreparaTestoPagina();

			var listaComuni = GetComuniAssociatiBindingSource();

			cmbComuni.DataSource = listaComuni;
			cmbComuni.DataBind();

			var idComuneAssociatoSelezionato = ReadFacade.Domanda.AltriDati.CodiceComune;

			if (!String.IsNullOrEmpty(idComuneAssociatoSelezionato))
				cmbComuni.SelectedValue = idComuneAssociatoSelezionato;

			pnlSelezioneComune.Visible = true;

			if (listaComuni.Count() == 2)	// La prima riga è qella che riporta il testo "Selezionare...", la seconda è la riga del comune corrente
			{
				pnlSelezioneComune.Visible = false;
				cmbComuni.SelectedIndex = 1;
			}

            if (!String.IsNullOrEmpty(this.SelezionaComune))
            {
                pnlSelezioneComune.Visible = false;

                if (String.IsNullOrEmpty(idComuneAssociatoSelezionato))
                {
                    cmbComuni.SelectedValue = this.SelezionaComune;
                }
            }
		}

        private bool MostraCopiaDa()
        {
            return this.CopiaDa.HasValue;
        }

        private void DatabindCopiaDa()
        {
            this.Master.MostraPaginatoreSteps = false;
            multiView.ActiveViewIndex = Constants.ViewIdCopiaDa;
            var domandaOrigine = this._salvataggioDomandaStrategy.GetById(this.CopiaDa.Value);

            formCopiaDati.Domanda = domandaOrigine.ReadInterface;
            formCopiaDati.DataBind();
        }


		public string PreparaTestoPagina()
		{
            string modelloTesto = TestoDescrizioneSteps;

            if (modelloTesto.IndexOf("{0}") == -1)
                return modelloTesto;

            var sb = new StringBuilder();
            var titoliSteps = this.WorkflowService.GetCurrentWorkflow().GetTitoliSteps();

            sb.Append("<ol>");

            foreach (var titoloStep in titoliSteps)
                sb.AppendFormat("<li>{0}</li>", titoloStep);

            sb.Append("</ol>");

            return String.Format(modelloTesto, sb.ToString());
		}

		public IEnumerable<KeyValuePair<string, string>> GetComuniAssociatiBindingSource()
		{
			var l = ReadFacade.Comuni.GetComuniAssociati();

			if (l.Count() == 0)
				throw new ApplicationException("La tabella comuniassociati non contiene righe");

            yield return new KeyValuePair<string, string>(String.Empty, String.Empty);

			foreach (var comune in l)
				yield return new KeyValuePair<string, string>(comune.CodiceComune, comune.Comune);
		}

        protected void confermaCopia_Click(object sender, EventArgs e)
        {
            try
            {
                var erroriValidazione = formCopiaDati.GetErroriDiValidazione();

                if (erroriValidazione.Count() > 0)
                {
                    foreach(var errore in erroriValidazione)
                    {
                        this.Errori.Add(errore);
                    }

                    return;
                }
                // Copia dati su nuova istanza...
                var elementiDaCopiare = formCopiaDati.GetElementiSelezionati();

                this._copiaDomandaService.CopiaDatiDomanda(CopiaDa.Value, IdDomanda, elementiDaCopiare);

                DataBind();                
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }

        }
    }
}
