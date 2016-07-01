using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System.Text;
using log4net;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria
{
	public partial class BenvenutoBandi : IstanzeStepPage
	{
		private static class Constants
		{
			public const string MsgErroreComuneNonSelezionato = "Per poter proseguire è necessario selezionare il comune per cui si vuole presentare l'istanza";
			public const string MsgErroreBandoNonAttivo = "Il bando sarà attivo dalle ore {0} del {1}. Non è ancora possibile presentare domande";
			public const string MsgErroreBandoScaduto = "Il bando è scaduto alle ore {0} del {1}. Non è più possibile presentare domande";
		}

		ILog _log = LogManager.GetLogger(typeof(BenvenutoBandi));

		[Inject]
		public CondizioneIngressoStepSempreVera _condizioneIngresso { get; set; }
		[Inject]
		public ComuneDiPresentazioneSelezionato _condizioneUscita { get; set; }
		[Inject]
		public DatiDomandaService DatiDomandaService { get; set; }
		[Inject]
		public IConfigurazione<ParametriWorkflow> _configurazione { get; set; }

		public string TestoDescrizioneSteps
		{
			get { object o = this.ViewState["TestoDescrizioneSteps"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["TestoDescrizioneSteps"] = value; }
		}

		public string DataInizioBando
		{
			get { object o = this.ViewState["DataInizioBando"]; return o == null ? "01/02/2015 23.59.59" : (string)o; }
			set { this.ViewState["DataInizioBando"] = value; }
		}

		public string DataFineBando
		{
			get { object o = this.ViewState["DataFineBando"]; return o == null ? "11/02/2015 23.59.59" : (string)o; }
			set { this.ViewState["DataFineBando"] = value; }
		}


		protected bool IgnoraBlocco
		{
			get 
			{ 
				var qs = Request.QueryString["ib"];

				return qs == "1";
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			// il service si occupa del salvataggio dei dati
			this.Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
		}

		public override void OnInitializeStep()
		{
			if (IgnoraBlocco)
				return;

			if (!String.IsNullOrEmpty(DataInizioBando))
			{
				var dt = DateTime.Parse(this.DataInizioBando);

				if (DateTime.Now < dt)
				{
					MostraErroreBandoNonAttivo();
					this.Master.MostraBottoneAvanti = false;
					this.Master.MostraPaginatoreSteps = false;

					return;
				}
			}

			if (!String.IsNullOrEmpty(DataFineBando))
			{
				var dt = DateTime.Parse(this.DataFineBando);

				if (DateTime.Now > dt)
				{
					MostraErroreBandoScaduto();
					this.Master.MostraBottoneAvanti = false;
					this.Master.MostraPaginatoreSteps = false;

					return;
				}
			}

			this.Master.MostraBottoneAvanti = true;
			this.Master.MostraPaginatoreSteps = true;
		}

		private void MostraErroreBandoScaduto()
		{
			this._log.ErrorFormat("L'utente {0} ha acceduto alle domande dei bandi ma il bando è scaduto {1}", UserAuthenticationResult.DatiUtente.Codicefiscale, DataFineBando);

			var dt = DateTime.Parse(this.DataFineBando);

			var err = String.Format(Constants.MsgErroreBandoScaduto, dt.ToString("HH:mm"), dt.ToString("dd/MM/yyyy"));

			this.Errori.Add(err);
		}

		private void MostraErroreBandoNonAttivo()
		{
			var dt = DateTime.Parse(this.DataInizioBando);

			var err = String.Format(Constants.MsgErroreBandoNonAttivo, dt.ToString("HH:mm"), dt.ToString("dd/MM/yyyy"));

			this.Errori.Add(err);
		}
		
		public override void OnBeforeExitStep()
		{
			DatiDomandaService.SetCodiceComune(IdDomanda, cmbComuni.SelectedValue);
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