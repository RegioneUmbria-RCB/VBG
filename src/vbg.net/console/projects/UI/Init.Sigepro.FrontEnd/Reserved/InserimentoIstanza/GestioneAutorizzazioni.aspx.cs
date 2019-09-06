using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAutorizzazioniMercati;
using Ninject;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneAutorizzazioni : IstanzeStepPage
	{
		public static class Constants
		{
			public const string StringaFormattazioneAutorizzazioni = "N.{0} del {1} rilasciata da {2} a {3} (n.presenze: {5})";
			public const bool NumeroAutorizzazioneObbligatorio = true;
			public const bool DataAutorizzazioneObbligatoria = true;
			public const int IdCampoCodiceAutorizzazione = -1;
			public const int IdCampoNumeroAutorizzazione = -1;
			public const int IdCampoDataAutorizzazione = -1;
			public const int IdCampoCodiceEnteRilascio = -1;
			public const int IdCampoNumeroPresenzeCalcolate = -1;
			public const int IdCampoDescrizioneEnteRilascio = -1;
			public const int IdCampoNumeroPresenzeDichiarate = -1;

			public const string TestoAutorizzazioneNonTrovata = "Non ho trovato la mia autorizzazione";
		}

		[Inject]
		protected AutorizzazioniMercatiService _autorizzazioniMercatiService { get; set; }


		#region Parametri letti dal file xml
		/* determina come verranno visualizzate le autorizzazioni nella combo, i segnaposto hanno i seguenti valori
		{0} - Numero autorizzazione
		{1} - Data autorizzazione
		{2} - Ente di rilascio
		{3} - Titolare
		{4} - Registro
		{5} - Numero presenze */
		public string StringaFormattazioneAutorizzazioni
		{
			get { object o = this.ViewState["StringaFormattazioneAutorizzazioni"];return o == null ? Constants.StringaFormattazioneAutorizzazioni : (string)o;}
			set { this.ViewState["StringaFormattazioneAutorizzazioni"] = value;}
		}

		// lista di id di registri da utilizzare nella ricerca delle autorizzazioni
		public string CodiciRegistri
		{
			get { object o = this.ViewState["CodiciRegistri"];return o == null ? String.Empty : (string)o;}
			set { this.ViewState["CodiciRegistri"] = value;}
		}

		// specifica se il numero di autorizzazione deve essere obbligatorio
		public bool NumeroAutorizzazioneObbligatorio
		{
			get { object o = this.ViewState["NumeroAutorizzazioneObbligatorio"];return o == null ? Constants.NumeroAutorizzazioneObbligatorio : (bool)o;}
			set { this.ViewState["NumeroAutorizzazioneObbligatorio"] = value;}
		}

		// specifica se la data dell autorizzazione deve essere obbligatoria
		public bool DataAutorizzazioneObbligatoria
		{
			get { object o = this.ViewState["DataAutorizzazioneObbligatoria"];return o == null ? Constants.DataAutorizzazioneObbligatoria : (bool)o;}
			set { this.ViewState["DataAutorizzazioneObbligatoria"] = value;}
		}

		public int IdCampoCodiceAutorizzazione
		{
			get { object o = this.ViewState["IdCampoCodiceAutorizzazione"];return o == null ? Constants.IdCampoCodiceAutorizzazione : (int)o;}
			set { this.ViewState["IdCampoCodiceAutorizzazione"] = value;}
		}
	
		public int IdCampoNumeroAutorizzazione
		{
			get { object o = this.ViewState["IdCampoNumeroAutorizzazione"];return o == null ? Constants.IdCampoNumeroAutorizzazione : (int)o;}
			set { this.ViewState["IdCampoNumeroAutorizzazione"] = value;}
		}

		public int IdCampoDataAutorizzazione
		{
			get { object o = this.ViewState["IdCampoDataAutorizzazione"]; return o == null ? Constants.IdCampoDataAutorizzazione : (int)o; }
			set { this.ViewState["IdCampoDataAutorizzazione"] = value; }
		}

		public int IdCampoCodiceEnteRilascio
		{
			get { object o = this.ViewState["IdCampoCodiceEnteRilascio"];return o == null ? Constants.IdCampoCodiceEnteRilascio : (int)o;}
			set { this.ViewState["IdCampoCodiceEnteRilascio"] = value;}
		}

		public int IdCampoDescrizioneEnteRilascio
		{
			get { object o = this.ViewState["IdCampoDescrizioneEnteRilascio"];return o == null ? Constants.IdCampoDescrizioneEnteRilascio : (int)o;}
			set { this.ViewState["IdCampoDescrizioneEnteRilascio"] = value;}
		}

		public int IdCampoNumeroPresenzeCalcolate
		{
			get { object o = this.ViewState["IdCampoNumeroPresenzeCalcolate"];return o == null ? Constants.IdCampoNumeroPresenzeCalcolate : (int)o;}
			set { this.ViewState["IdCampoNumeroPresenzeCalcolate"] = value;}
		}

		public int IdCampoNumeroPresenzeDichiarate
		{
			get { object o = this.ViewState["IdCampoNumeroPresenzeDichiarate"];return o == null ? Constants.IdCampoNumeroPresenzeDichiarate : (int)o;}
			set { this.ViewState["IdCampoNumeroPresenzeDichiarate"] = value;}
		}

		#endregion


		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			ddlAutorizzazioni.DataSource = GetListaAutorizzazioni();
			ddlAutorizzazioni.DataBind();

			var autorizzazione = ReadFacade.Domanda.AutorizzazioniMercati.Autorizzazione;

			if (autorizzazione != null)
			{
				ddlAutorizzazioni.SelectedValue = autorizzazione.Id.ToString();
				txtNumeroPresenzeDichiarateAutTrovata.Text = autorizzazione.Id != -1 ? autorizzazione.NumeroPresenzeDichiarato : "";
				txtNumeroPresenteAutNonTrovata.Value = autorizzazione.Id == -1 ? autorizzazione.NumeroPresenzeDichiarato : "";

				if (autorizzazione.Id == -1)
				{
					txtNumeroAutorizzazione.Value = autorizzazione.Numero;
					txtDataAutorizzazione.Value = autorizzazione.Data;

					if (autorizzazione.EnteRilascio.Codice == "")
					{
						txtEnteCustom.Value = autorizzazione.EnteRilascio.Descrizione;
						chkEnteNonTrovato.Checked = true;
					}
					else
					{
						hidIdEnte.Value = autorizzazione.EnteRilascio.Codice;
						txtEnte.Value = autorizzazione.EnteRilascio.Descrizione;
					}
				}
			}

			txtNumeroAutorizzazione.Descrizione.Replace("*", "");
			txtDataAutorizzazione.Descrizione.Replace("*", "");

			if(this.NumeroAutorizzazioneObbligatorio)
				txtNumeroAutorizzazione.Descrizione = txtNumeroAutorizzazione.Descrizione + "*";

			if (this.DataAutorizzazioneObbligatoria)
				txtDataAutorizzazione.Descrizione = txtDataAutorizzazione.Descrizione + "*";
		}

		private IEnumerable<object> GetListaAutorizzazioni()
		{
			yield return new { Text = "", Value = "" };

			foreach(var x in this._autorizzazioniMercatiService
									.GetAutorizzazioni(IdDomanda, CodiciRegistri.Split(','), StringaFormattazioneAutorizzazioni)
									.Select( x => new { Text = x.Descrizione, Value = x.Codice }))
				yield return x;

			yield return new { Text = Constants.TestoAutorizzazioneNonTrovata, Value = "-1" };
		}

		#region Ciclo di vita dello step

		public override bool CanExitStep()
		{
			if (ddlAutorizzazioni.SelectedValue == "")
			{
				Errori.Add( "Specificare un'autorizzazione tra quelle presenti oppure selezionare \"" + Constants.TestoAutorizzazioneNonTrovata + "\"");
				return false;
			}

			if (ddlAutorizzazioni.SelectedValue == "-1")
			{
				if (this.NumeroAutorizzazioneObbligatorio && String.IsNullOrEmpty(txtNumeroAutorizzazione.Value))
				{
					Errori.Add("Specificare il numero autorizzazione");
					return false;
				}

				if (this.DataAutorizzazioneObbligatoria && String.IsNullOrEmpty(txtDataAutorizzazione.Value))
				{
					Errori.Add("Specificare la data dell'autorizzazione");
					return false;
				}

				if ( !chkEnteNonTrovato.Checked && (String.IsNullOrEmpty(hidIdEnte.Value) || String.IsNullOrEmpty(txtEnte.Value)))
				{
					Errori.Add("Selezionare l'ente che ha rilasciato l'autorizzazione");
					return false;
				}

				if (chkEnteNonTrovato.Checked && String.IsNullOrEmpty(txtEnteCustom.Value))
				{
					Errori.Add("Specificare il nome dell'ente che ha rilasciato l'autorizzazione");
					return false;
				}

				if (String.IsNullOrEmpty(txtNumeroPresenteAutNonTrovata.Value))
				{
					Errori.Add("Specificare l'il numero presenze, indicare 0 nel caso in cui non siano state effettuate presenze");
					return false;
				}
			}

			

			if (ddlAutorizzazioni.SelectedValue == "-1")
			{
				this._autorizzazioniMercatiService.ImpostaEstremiAutorizzazione(IdDomanda, txtNumeroAutorizzazione.Value, txtDataAutorizzazione.Value, hidIdEnte.Value, txtEnte.Value, txtEnteCustom.Value, txtNumeroPresenteAutNonTrovata.Value);
			}
			else
			{
				this._autorizzazioniMercatiService.ImpostaEstremiAutorizzazione(IdDomanda, Convert.ToInt32(ddlAutorizzazioni.SelectedValue), txtNumeroPresenzeDichiarateAutTrovata.Text);
			}

			var mappatura = new MappaturaDatiAutorizzazioni
			{
				IdCampoCodiceAutorizzazione = this.IdCampoCodiceAutorizzazione,
				IdCampoCodiceEnteRilascio = this.IdCampoCodiceEnteRilascio,
				IdCampoDataAutorizzazione = this.IdCampoDataAutorizzazione,
				IdCampoDescrizioneEnteRilascio = this.IdCampoDescrizioneEnteRilascio,
				IdCampoNumeroAutorizzazione = this.IdCampoNumeroAutorizzazione,
				IdCampoNumeroPresenzeCalcolate = this.IdCampoNumeroPresenzeCalcolate,
				IdCampoNumeroPresenzeDichiarate = this.IdCampoNumeroPresenzeDichiarate
			};

			this._autorizzazioniMercatiService.MappaDatiAutorizzazioneSuDatiDinamici(IdDomanda, mappatura);

			return true;
		}
		#endregion
	}
}