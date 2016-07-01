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

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class Benvenuto : IstanzeStepPage
	{
		private static class Constants
		{
			public const string MsgErroreComuneNonSelezionato = "Per poter proseguire è necessario selezionare il comune per cui si vuole presentare l'istanza";
			public const string QsParamSelezionaIntervento = "SelezionaIntervento";
		}


		[Inject]
		public CondizioneIngressoStepSempreVera _condizioneIngresso { get; set; }
		[Inject]
		public ComuneDiPresentazioneSelezionato _condizioneUscita { get; set; }
		[Inject]
		public DatiDomandaService DatiDomandaService { get; set; }
		[Inject]
		public IConfigurazione<ParametriWorkflow> _configurazione { get; set; }

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

		protected void Page_Load(object sender, EventArgs e)
		{
			// il service si occupa del salvataggio dei dati
			this.Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
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
				DatiDomandaService.ImpostaIdIntervento(IdDomanda, SelezionaIntervento.Value, (int?)null);
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
			ltrTestoListaStep.Text = PreparaTestoPagina(TestoDescrizioneSteps);

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
		}


		public string PreparaTestoPagina(string modelloTesto)
		{
			if (modelloTesto.IndexOf("{0}") == -1)
				return modelloTesto;

			var sb = new StringBuilder();
			var titoliSteps = _configurazione.Parametri.DefaultWorkflow.GetTitoliSteps();

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

			yield return new KeyValuePair<string, string>(String.Empty, "Selezionare...");

			foreach (var comune in l)
				yield return new KeyValuePair<string, string>(comune.CodiceComune, comune.Comune);
		}
	}
}
