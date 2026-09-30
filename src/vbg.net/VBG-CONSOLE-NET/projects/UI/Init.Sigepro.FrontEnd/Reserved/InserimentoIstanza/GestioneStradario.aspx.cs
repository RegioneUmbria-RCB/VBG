using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Utils.Web.UI;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.WebControls.FormControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneStradario : IstanzeStepPage
	{
		private static class Constants
		{
			public const int IdColonnaCivico = 1;
			public const int IdColonnaEsponente = 2;
			public const int IdColonnaColore = 3;
			public const int IdColonnaScala = 4;
			public const int IdColonnaPiano = 5;
			public const int IdColonnaInterno = 6;
			public const int IdColonnaEsponenteInterno = 7;
			public const int IdColonnaFabbricato = 8;
			public const int IdColonnaKm = 9;
		}

		[Inject]
		protected LocalizzazioniService _localizzazioniService { get; set; }

        [Inject]
        protected CivicoValidoSpecification _civicoValidoSpecification { get; set; }

        [Inject]
        protected EsponenteValidoSpecification _esponenteValidoSpecification { get; set; }

        [Inject]
        protected IConfigurazione<ParametriLocalizzazioni> _configurazione { get; set; }

        protected bool CivicoNumerico { get { return _configurazione.Parametri.UsaCiviciNumerici; } }

        protected bool EsponenteNumerico { get { return _configurazione.Parametri.UsaEsponentiNumerici; } }

		#region dati letti dai parametri del workflow
		public bool CivicoVisibile
		{
			get { object o = this.ViewState["CivicoVisibile"]; return o == null ? true : (bool)o; }
			set { this.ViewState["CivicoVisibile"] = value; }
		}

		public string CivicoEtichetta
		{
			get { object o = this.ViewState["CivicoEtichetta"]; return o == null ? "Civico" : o.ToString(); }
			set { this.ViewState["CivicoEtichetta"] = value; }
		}

		public bool CivicoObbligatorio
		{
			get { object o = this.ViewState["CivicoObbligatorio"]; return o == null ? false : (bool)o; }
			set { this.ViewState["CivicoObbligatorio"] = value; }
		}
		//---------------------------------------

		public bool EsponenteVisibile
		{
			get { object o = this.ViewState["EsponenteVisibile"]; return o == null ? true : (bool)o; }
			set { this.ViewState["EsponenteVisibile"] = value; }
		}

		public string EsponenteEtichetta
		{
			get { object o = this.ViewState["EsponenteEtichetta"]; return o == null ? "Esponente" : o.ToString(); }
			set { this.ViewState["EsponenteEtichetta"] = value; }
		}

		public bool EsponenteObbligatorio
		{
			get { object o = this.ViewState["EsponenteObbligatorio"]; return o == null ? false : (bool)o; }
			set { this.ViewState["EsponenteObbligatorio"] = value; }
		}

		//-------------------------------------------

		public bool ColoreVisibile
		{
			get { object o = this.ViewState["ColoreVisibile"]; return o == null ? true : (bool)o; }
			set { this.ViewState["ColoreVisibile"] = value; }
		}

		public string ColoreEtichetta
		{
			get { object o = this.ViewState["ColoreEtichetta"]; return o == null ? "Colore" : o.ToString(); }
			set { this.ViewState["ColoreEtichetta"] = value; }
		}

		public bool ColoreObbligatorio
		{
			get { object o = this.ViewState["ColoreObbligatorio"]; return o == null ? false : (bool)o; }
			set { this.ViewState["ColoreObbligatorio"] = value; }
		}

		//-------------------------------------------------

		public bool ScalaVisibile
		{
			get { object o = this.ViewState["ScalaVisibile"]; return o == null ? true : (bool)o; }
			set { this.ViewState["ScalaVisibile"] = value; }
		}

		public string ScalaEtichetta
		{
			get { object o = this.ViewState["ScalaEtichetta"]; return o == null ? "Scala" : o.ToString(); }
			set { this.ViewState["ScalaEtichetta"] = value; }
		}

		public bool ScalaObbligatorio
		{
			get { object o = this.ViewState["ScalaObbligatorio"]; return o == null ? false : (bool)o; }
			set { this.ViewState["ScalaObbligatorio"] = value; }
		}

		//--------------------------------------------------

		public bool PianoVisibile
		{
			get { object o = this.ViewState["PianoVisibile"]; return o == null ? true : (bool)o; }
			set { this.ViewState["PianoVisibile"] = value; }
		}

		public string PianoEtichetta
		{
			get { object o = this.ViewState["PianoEtichetta"]; return o == null ? "Piano" : o.ToString(); }
			set { this.ViewState["PianoEtichetta"] = value; }
		}

		public bool PianoObbligatorio
		{
			get { object o = this.ViewState["PianoObbligatorio"]; return o == null ? false : (bool)o; }
			set { this.ViewState["PianoObbligatorio"] = value; }
		}

		//--------------------------------------------------

		public bool InternoVisibile
		{
			get { object o = this.ViewState["InternoVisibile"]; return o == null ? true : (bool)o; }
			set { this.ViewState["InternoVisibile"] = value; }
		}

		public string InternoEtichetta
		{
			get { object o = this.ViewState["InternoEtichetta"]; return o == null ? "Interno" : o.ToString(); }
			set { this.ViewState["InternoEtichetta"] = value; }
		}

		public bool InternoObbligatorio
		{
			get { object o = this.ViewState["InternoObbligatorio"]; return o == null ? false : (bool)o; }
			set { this.ViewState["InternoObbligatorio"] = value; }
		}

		//--------------------------------------------------

		public bool EsponenteInternoVisibile
		{
			get { object o = this.ViewState["EsponenteInternoVisibile"]; return o == null ? true : (bool)o; }
			set { this.ViewState["EsponenteInternoVisibile"] = value; }
		}

		public string EsponenteInternoEtichetta
		{
			get { object o = this.ViewState["EsponenteInternoEtichetta"]; return o == null ? "EsponenteInterno" : o.ToString(); }
			set { this.ViewState["EsponenteInternoEtichetta"] = value; }
		}

		public bool EsponenteInternoObbligatorio
		{
			get { object o = this.ViewState["EsponenteInternoObbligatorio"]; return o == null ? false : (bool)o; }
			set { this.ViewState["EsponenteInternoObbligatorio"] = value; }
		}

		//--------------------------------------------------


		public bool FabbricatoVisibile
		{
			get { object o = this.ViewState["FabbricatoVisibile"]; return o == null ? true : (bool)o; }
			set { this.ViewState["FabbricatoVisibile"] = value; }
		}

		public string FabbricatoEtichetta
		{
			get { object o = this.ViewState["FabbricatoEtichetta"]; return o == null ? "Fabbricato" : o.ToString(); }
			set { this.ViewState["FabbricatoEtichetta"] = value; }
		}

		public bool FabbricatoObbligatorio
		{
			get { object o = this.ViewState["FabbricatoObbligatorio"]; return o == null ? false : (bool)o; }
			set { this.ViewState["FabbricatoObbligatorio"] = value; }
		}

		//--------------------------------------------------

		public bool KmVisibile
		{
			get { object o = this.ViewState["KmVisibile"]; return o == null ? true : (bool)o; }
			set { this.ViewState["KmVisibile"] = value; }
		}

		public string KmEtichetta
		{
			get { object o = this.ViewState["KmEtichetta"]; return o == null ? "Km" : o.ToString(); }
			set { this.ViewState["KmEtichetta"] = value; }
		}

		public bool KmObbligatorio
		{
			get { object o = this.ViewState["KmObbligatorio"]; return o == null ? false : (bool)o; }
			set { this.ViewState["KmObbligatorio"] = value; }
		}


		#endregion

		#region gestione della validazione e delle etichette dei campi di editing
		IEnumerable<ConfigurazioneCampiStradario> _configurazioneCampi;


		protected class ConfigurazioneCampiStradario
		{
            IBootstrapFormControl _controlloInput;
			DataControlField	_colonna;
			bool				_obbligatorio;
			string				_etichetta;
			bool				_visibile;

			public string Etichetta { get { return this._etichetta; } }

            public ConfigurazioneCampiStradario(IBootstrapFormControl controlloInput, DataControlField colonna, bool obbligatorio, string etichetta, bool visibile)
			{
				this._controlloInput		= controlloInput;
				this._colonna				= colonna;
				this._obbligatorio			= obbligatorio;
				this._etichetta				= etichetta;
				this._visibile				= visibile;
			}

			public void ApplicaCriteriDiVisibilita()
			{
				this._controlloInput.Visible = this._colonna.Visible = this._visibile;
				this._controlloInput.Label = this._colonna.HeaderText = this._etichetta;

                if (this._obbligatorio) {
                    this._controlloInput.Required = true;
                }
                    
			}

			public bool VerificaCompilazione()
			{
				if (!this._visibile || !this._obbligatorio)
					return true;

				return !String.IsNullOrEmpty(this._controlloInput.Value);
			}
		}

		private void InizializzaConfigurazioneCampi()
		{
			this._configurazioneCampi = new List<ConfigurazioneCampiStradario>
			{
				new ConfigurazioneCampiStradario( txtCivico, dgStradario.Columns[ Constants.IdColonnaCivico], CivicoObbligatorio , CivicoEtichetta , CivicoVisibile ),
				new ConfigurazioneCampiStradario( txtEsponente, dgStradario.Columns[ Constants.IdColonnaEsponente], EsponenteObbligatorio , EsponenteEtichetta , EsponenteVisibile ),
				new ConfigurazioneCampiStradario( ddlColore, dgStradario.Columns[ Constants.IdColonnaColore], ColoreObbligatorio , ColoreEtichetta, ColoreVisibile),
				new ConfigurazioneCampiStradario( txtScala, dgStradario.Columns[ Constants.IdColonnaScala], ScalaObbligatorio , ScalaEtichetta, ScalaVisibile),
				new ConfigurazioneCampiStradario( txtPiano, dgStradario.Columns[ Constants.IdColonnaPiano], PianoObbligatorio , PianoEtichetta, PianoVisibile),
				new ConfigurazioneCampiStradario( txtInterno, dgStradario.Columns[ Constants.IdColonnaInterno], InternoObbligatorio , InternoEtichetta, InternoVisibile),
				new ConfigurazioneCampiStradario( txtEsponenteInterno, dgStradario.Columns[ Constants.IdColonnaEsponenteInterno], EsponenteInternoObbligatorio , EsponenteInternoEtichetta, EsponenteInternoVisibile),
				new ConfigurazioneCampiStradario( txtFabbricato, dgStradario.Columns[ Constants.IdColonnaFabbricato], FabbricatoObbligatorio , FabbricatoEtichetta, FabbricatoVisibile),
				new ConfigurazioneCampiStradario( txtKm, dgStradario.Columns[ Constants.IdColonnaKm], KmObbligatorio , KmEtichetta, KmVisibile),

			};
		}

		private void ApplicaCriteriVisibilita()
		{
			foreach (var campo in this._configurazioneCampi)
				campo.ApplicaCriteriDiVisibilita();
		}

		private bool VerificaCompilazione()
		{
			var errori = false;

			if (String.IsNullOrEmpty(Indirizzo.Text))
			{
				Errori.Add("Il campo \"Indirizzo\" è obbligatorio");
				errori = true;
			}

			foreach (var campo in this._configurazioneCampi)
			{
				if(! campo.VerificaCompilazione())
				{
					errori = true;
					Errori.Add( String.Format( "Il campo \"{0}\" è obbligatorio", campo.Etichetta ));
				}
			}

			return !errori;
		}


		#endregion

  		public string CodiceComune
		{
			get { object o = this.ViewState["CodiceComune"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["CodiceComune"] = value; }
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			// il service si occupa del salvataggio dei dati
			Master.IgnoraSalvataggioDati = true;

			InizializzaConfigurazioneCampi();
			ApplicaCriteriVisibilita();

			if (!IsPostBack)
			{
				var listaColori = ReadFacade.Stradario.GetListaColori(this.IdComune);

				listaColori.Insert(0, new StradarioColore { CODICECOLORE = String.Empty, COLORE = String.Empty, IDCOMUNE = this.IdComune });

				ddlColore.DataSource = listaColori;
				ddlColore.DataBind();

				DataBind();
			}

			CodiceComune = ReadFacade.Domanda.AltriDati.CodiceComune;


		}

		#region Ciclo della vita dello step

		public override bool CanEnterStep()
		{
			return true;
		}

		public override bool CanExitStep()
		{
			if (ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() == 0)
			{
				Errori.Add("Inserire almeno un indirizzo dello stradario");
				return false;
			}

			return true;
		}

		#endregion

		public override void DataBind()
		{
			multiView.ActiveViewIndex = 0;

			dgStradario.DataSource = ReadFacade.Domanda.Localizzazioni.Indirizzi;
			dgStradario.DataBind();

			this.Master.MostraPaginatoreSteps = true;
			this.Master.MostraBottoneAvanti = ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() > 0;
		}

		/// <summary>
		/// Svuota tutti i controlli del panel di inserimento
		/// </summary>
		private void ClearDettaglio()
		{
			Indirizzo.Value = String.Empty;
			Indirizzo.Text = String.Empty;
			Note.Text = String.Empty;

			txtCivico.Value = String.Empty;
			txtEsponente.Value  = String.Empty;
			ddlColore.SelectedIndex = 0;
			txtScala.Value  = String.Empty;
			txtInterno.Value  = String.Empty;
			txtEsponenteInterno.Value  = String.Empty;
			txtPiano.Value  = String.Empty;
			txtFabbricato.Value  = String.Empty;
			txtKm.Value  = String.Empty;

			dgIndirizzi.Visible = false;

		}

		protected void Edit()
		{
			multiView.ActiveViewIndex = 1;
			this.Master.MostraPaginatoreSteps = false;
		}

		protected void EditNew()
		{
			ClearDettaglio();
			Edit();
		}

		/// <summary>
		/// Delegate della selezione di un elemento della dataGrid dei risultati della ricerca nello stradario
		/// </summary>
		public void dgIndirizzi_SelectedIndexChanged(object sender, EventArgs e)
		{
			var codiceStradario = Convert.ToInt32(dgIndirizzi.DataKeys[dgIndirizzi.SelectedIndex].Value);

			InserisciVoceStradario(ReadFacade.Stradario.GetByCodiceStradario(IdComune, codiceStradario));
		}

		/// <summary>
		/// Aggiunge la voce di stradario trovata alla lista degli indirizzi dell'istanza
		/// </summary>
		/// <param name="stradarioTrovato">Voce dello stradario trovata o null se si deve effettuare una ricerca per match parziale</param>
		private void InserisciVoceStradario(Stradario stradarioTrovato)
		{
			if (stradarioTrovato != null)
			{
                var civicoValido = this._civicoValidoSpecification.IsSatisfiedBy(txtCivico.Value);
                var esponenteValido = this._esponenteValidoSpecification.IsSatisfiedBy(txtEsponente.Value);

                if (!civicoValido)
                {
                    Errori.Add("Il civico immesso non è valido. Il campo può contenere solamente valori numerici");
                }

                if (!esponenteValido)
                {
                    Errori.Add("L'esponente immesso non è valido. Il campo può contenere solamente valori numerici");
                }

                if (!(civicoValido && esponenteValido))
                {
                    return;
                }

				var nomeVia = stradarioTrovato.PREFISSO + " " + stradarioTrovato.DESCRIZIONE;

				if (!String.IsNullOrEmpty(stradarioTrovato.LOCFRAZ))
					nomeVia += " (" + stradarioTrovato.LOCFRAZ + ")";

				var nuovoStradario = new NuovaLocalizzazione(Convert.ToInt32(stradarioTrovato.CODICESTRADARIO), nomeVia, txtCivico.Value)
				{
					Colore = ddlColore.Value,
					Esponente = txtEsponente.Value,
					EsponenteInterno = txtEsponenteInterno.Value,
					Interno = txtInterno.Value,
					Note = Note.Text,
					Scala = txtScala.Value,
					Piano = txtPiano.Value,
					Fabbricato = txtFabbricato.Value,
					Km = txtKm.Value
				};

				_localizzazioniService.AggiungiLocalizzazione(IdDomanda, nuovoStradario);

				DataBind();

				return;
			}

			var listaIndirizzi = ReadFacade.Stradario.GetByMatchParziale(IdComune, CodiceComune, String.Empty, Indirizzo.Text);

			if (listaIndirizzi.Count > 0)
			{
				Errori.Add("Indirizzo non trovato. Sono però stati trovati i seguenti record simili");

				dgIndirizzi.DataSource = listaIndirizzi;
				dgIndirizzi.DataBind();

				dgIndirizzi.Visible = true;
			}
			else
			{
				Errori.Add("Indirizzo non trovato. Verificare i dati immessi");
				Indirizzo.Text = String.Empty;
			}

		}

		/// <summary>
		/// Handler dell'evento click sul bottone di eliminazione riga della datagrid di riepilogo
		/// </summary>
		public void dgStradario_DeleteCommand(object source, GridViewDeleteEventArgs e)
		{
			int key = Convert.ToInt32(dgStradario.DataKeys[e.RowIndex].Value);

			_localizzazioniService.EliminaLocalizzazione(IdDomanda, key);

			DataBind();
		}


		public void cmdAddNew_Click(object sender, EventArgs e)
		{
			EditNew();
		}
		public void cmdConfirm_Click(object sender, EventArgs e)
		{
			dgIndirizzi.Visible = false;

			if ( !VerificaCompilazione() )
				return;

			Stradario stradarioTrovato = CodiceStradarioTrovato() ? TrovaStradarioDaCodiceStradario() : TrovaStradarioDaIndirizzo();

			InserisciVoceStradario(stradarioTrovato);
		}

		private Stradario TrovaStradarioDaCodiceStradario()
		{
			return ReadFacade.Stradario.GetByCodiceStradario(IdComune, Convert.ToInt32(Indirizzo.Value));
		}

		private Stradario TrovaStradarioDaIndirizzo()
		{
			return ReadFacade.Stradario.GetByIndirizzo(IdComune, CodiceComune, Indirizzo.Text);
		}

		private bool CodiceStradarioTrovato()
		{
            return !String.IsNullOrEmpty(Indirizzo.Value);
		}
		public void cmdCancel_Click(object sender, EventArgs e)
		{
			DataBind();
		}
	}
}
