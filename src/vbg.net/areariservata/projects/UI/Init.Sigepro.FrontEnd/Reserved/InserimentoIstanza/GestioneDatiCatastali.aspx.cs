using System;
using System.Linq;
using System.Web.UI.WebControls;
//using Init.Sigepro.FrontEnd.AppLogic.Readers;

using System.Collections.Generic;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneDatiCatastali : IstanzeStepPage
	{
		[Inject]
		protected LocalizzazioniService _localizzazioniService { get; set; }


		public bool InserimentoObbligatorio
		{
			get { object o = ViewState["InserimentoObbligatorio"]; return o == null ? false : (bool)o; }
			set { ViewState["InserimentoObbligatorio"] = value; }
		}



		protected void Page_Load(object sender, EventArgs e)
		{
			// Il service si occupa del salvataggio dei dati
			Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
			{
				ddlIndirizzo.DataSource = ReadFacade.Domanda.Localizzazioni.Indirizzi.Select(x => new KeyValuePair<string, string>(x.Id.ToString(), x.ToString()));
				ddlIndirizzo.DataBind();

				var list = new KeyValuePair<string, string>[] { 
					new KeyValuePair<string,string>("F","Fabbricati"),	
					new KeyValuePair<string,string>("T","Terreni")						
					};

				ddlTipoCatasto.DataSource = list;
				ddlTipoCatasto.DataBind();

				DataBind();
			}

		}

		#region Ciclo di vita dello step
		public override bool CanEnterStep()
		{
			return true;
		}

		public override bool CanExitStep()
		{
			if (InserimentoObbligatorio && !ReadFacade.Domanda.Localizzazioni.ContieneRiferimentiCatastali)
			{
				Errori.Add("E' obbligatorio specificare almeno un dato localizzativo");
				return false;

			}
			return true;
		}

		#endregion



		protected void cmdNuovo_Click(object sender, EventArgs e)
		{
			MostraVistaInserimento();
		}


		public override void DataBind()
		{
			if (InserimentoObbligatorio && !ReadFacade.Domanda.Localizzazioni.ContieneRiferimentiCatastali)
			{
				MostraVistaInserimento();
			}
			else
			{
				MostraVistaLista();
			}
		}

		private void MostraVistaLista()
		{
			multiView.ActiveViewIndex = 0;

			dgDatiCatastali.DataSource = ReadFacade.Domanda.Localizzazioni.Indirizzi.SelectMany( x => x.RiferimentiCatastali );
			dgDatiCatastali.DataBind();

			this.Master.MostraPaginatoreSteps = true;
		}

		private void MostraVistaInserimento()
		{
			multiView.ActiveViewIndex = 1;

			ddlIndirizzo.SelectedIndex = 0;
			ddlTipoCatasto.SelectedIndex = 0;
			txtFoglio.Text = "";
			txtParticella.Text = "";
			txtSub.Text = "";

			this.Master.MostraPaginatoreSteps = false;

			if(InserimentoObbligatorio)
				cmdReset.Visible = ReadFacade.Domanda.Localizzazioni.ContieneRiferimentiCatastali;
		}

		public void cmdAdd_Click(object sender, EventArgs e)
		{
			try
			{
				if (String.IsNullOrEmpty(txtFoglio.Text.Trim()))
					throw new Exception("Specificare il foglio");

				if (String.IsNullOrEmpty(txtParticella.Text.Trim()))
					throw new Exception("Specificare la particella");

				var nuovoCatasto = new NuovoRiferimentoCatastale(ddlTipoCatasto.SelectedValue, ddlTipoCatasto.SelectedItem.Text, txtFoglio.Text, txtParticella.Text, txtSub.Text);
				var idLocalizzazione = Convert.ToInt32(ddlIndirizzo.SelectedValue);

				_localizzazioniService.AssegnaRiferimentiCatastaliALocalizzazione(IdDomanda, idLocalizzazione, nuovoCatasto);

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		public void cmdReset_Click(object sender, EventArgs e)
		{
			DataBind();
		}


		public void dgDatiCatastali_DeleteCommand(object source, GridViewDeleteEventArgs e)
		{
			int dataKey = Convert.ToInt32(dgDatiCatastali.DataKeys[e.RowIndex].Value);

			_localizzazioniService.EliminaRiferimentiCatastali(IdDomanda, dataKey);

			DataBind();
		}

		protected string GetLocalizzazione(object objDatiCatastali)
		{
			var rc = (RiferimentoCatastale)objDatiCatastali;
			var indirizzo = ReadFacade.Domanda.Localizzazioni.Indirizzi.Where(x => x.Id == rc.IdLocalizzazione).FirstOrDefault();

			if (indirizzo == null)
				return String.Empty;

			return indirizzo.ToString();
		}
	}
}
