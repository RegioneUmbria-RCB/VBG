using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Utils.Web.UI;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneLocalizzazioni : IstanzeStepPage
	{
		private static class Constants
		{
			public const int IdColonnaCivico = 0;
			// public const int IdColonnaEsponente = 1;
			// public const int IdColonnaColore = 2;
			public const int IdColonnaAltriDati = 1;
			public const int IdColonnaKm = 2;
			public const int IdColonnaNote = 3;
			public const int IdColonnaCoordinate = 4;
			public const int IdColonnaRiferimentiCatastali = 5;
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
			set { this._formLocalizzazioni.Civico.Visibile = value; }
		}

		public string CivicoEtichetta
		{
			set { this._formLocalizzazioni.Civico.Etichetta = value; }
		}

		public bool CivicoObbligatorio
		{
			set { this._formLocalizzazioni.Civico.Obbligatorio = value; }
		}
		//---------------------------------------

		public bool EsponenteVisibile
		{
			set { this._formLocalizzazioni.Esponente.Visibile = value; }
		}

		public string EsponenteEtichetta
		{
			set { this._formLocalizzazioni.Esponente.Etichetta = value; }
		}

		public bool EsponenteObbligatorio
		{
			set { this._formLocalizzazioni.Esponente.Obbligatorio = value; }
		}

		//-------------------------------------------

		public bool ColoreVisibile
		{
			set { this._formLocalizzazioni.Colore.Visibile = value; }
		}

		public string ColoreEtichetta
		{
			set { this._formLocalizzazioni.Colore.Etichetta = value; }
		}

		public bool ColoreObbligatorio
		{
			set { this._formLocalizzazioni.Colore.Obbligatorio = value; }
		}

		//-------------------------------------------------

		public bool ScalaVisibile
		{
			set { this._formLocalizzazioni.Scala.Visibile = value; }
		}

		public string ScalaEtichetta
		{
			set { this._formLocalizzazioni.Scala.Etichetta = value; }
		}

		public bool ScalaObbligatorio
		{
			set { this._formLocalizzazioni.Scala.Obbligatorio = value; }
		}

		//--------------------------------------------------

		public bool PianoVisibile
		{
			set { this._formLocalizzazioni.Piano.Visibile = value; }
		}

		public string PianoEtichetta
		{
			set { this._formLocalizzazioni.Piano.Etichetta = value; }
		}

		public bool PianoObbligatorio
		{
			set { this._formLocalizzazioni.Piano.Obbligatorio = value; }
		}

		//--------------------------------------------------

		public bool InternoVisibile
		{
			set { this._formLocalizzazioni.Interno.Visibile = value; }
		}

		public string InternoEtichetta
		{
			set { this._formLocalizzazioni.Interno.Etichetta = value; }
		}

		public bool InternoObbligatorio
		{
			set { this._formLocalizzazioni.Interno.Obbligatorio = value; }
		}

		//--------------------------------------------------

		public bool EsponenteInternoVisibile
		{
			set { this._formLocalizzazioni.EsponenteInterno.Visibile = value; }
		}

		public string EsponenteInternoEtichetta
		{
			set { this._formLocalizzazioni.EsponenteInterno.Etichetta = value; }
		}

		public bool EsponenteInternoObbligatorio
		{
			set { this._formLocalizzazioni.EsponenteInterno.Obbligatorio = value; }
		}

		//--------------------------------------------------


		public bool FabbricatoVisibile
		{
			set { this._formLocalizzazioni.Fabbricato.Visibile = value; }
		}

		public string FabbricatoEtichetta
		{
			set { this._formLocalizzazioni.Fabbricato.Etichetta = value; }
		}

		public bool FabbricatoObbligatorio
		{
			set { this._formLocalizzazioni.Fabbricato.Obbligatorio = value; }
		}

		//--------------------------------------------------

		public bool KmVisibile
		{
			set { this._formLocalizzazioni.Km.Visibile = value; }
		}

		public string KmEtichetta
		{
			set { this._formLocalizzazioni.Km.Etichetta = value; }
		}

		public bool KmObbligatorio
		{
			set { this._formLocalizzazioni.Km.Obbligatorio = value; }
		}

		//--------------------------------------------------

		public string NoteEtichetta
		{
			set { this._formLocalizzazioni.Note.Etichetta = value; }
		}

		public bool NoteObbligatorio
		{
			set { this._formLocalizzazioni.Note.Obbligatorio = value; }
		}


		//--------------------------------------------------

		public string TipoLocalizzazione
		{
			get { object o = this.ViewState["TipoLocalizzazione"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["TipoLocalizzazione"] = value; }
		}


		//--------------------------------------------------

		public bool CoordinateVisibili
		{
			get { return this.ViewState["CoordinateVisibili"] == null ? true : (bool)this.ViewState["CoordinateVisibili"]; }
			set 
			{
				this.ViewState["CoordinateVisibili"] = value;
				this._formLocalizzazioni.Longitudine.Visibile = value;
				this._formLocalizzazioni.Latitudine.Visibile = value; 
			}
		}

		public bool CoordinateObbligatorie
		{
			set
			{
				this._formLocalizzazioni.Longitudine.Etichetta = "Longitudine";
				this._formLocalizzazioni.Latitudine.Etichetta = "Latitudine";
				this._formLocalizzazioni.Longitudine.Obbligatorio = value;
				this._formLocalizzazioni.Latitudine.Obbligatorio = value;
			}
		}

		public string CoordinateEtichettaLongitudine
		{
			set
			{
				this._formLocalizzazioni.Longitudine.Etichetta = value;
			}
		}
		public string CoordinateEtichettaLatitudine
		{
			set
			{
				this._formLocalizzazioni.Latitudine.Etichetta = value;
			}
		}
		public string CoordinateEspressioneRegolare
		{
			set 
			{ 
				this._formLocalizzazioni.Longitudine.EspressioneRegolare = value;
				this._formLocalizzazioni.Latitudine.EspressioneRegolare = value; 
			}
		}

		public string CoordinateLongitudineValoreMin
		{
			set { this._formLocalizzazioni.Longitudine.ValoreMin = value; }
		}

		public string CoordinateLatitudineValoreMin
		{
			set { this._formLocalizzazioni.Latitudine.ValoreMin = value; }
		}

		public string CoordinateLongitudineValoreMax
		{
			set { this._formLocalizzazioni.Longitudine.ValoreMax = value; }
		}

		public string CoordinateLatitudineValoreMax
		{
			set { this._formLocalizzazioni.Latitudine.ValoreMax = value; }
		}

		public string CoordinateTitoloBlocco
		{
			set
			{
				this.ltrTitoloBloccoCoordinate.Text = value;
			}
		}

		//--------------------------------------------------

		public bool DatiCatastaliVisibili
		{
			get
			{
				return ViewstateGet("DatiCatastaliVisibili", true);
			}
			set 
			{
				this.ViewStateSet("DatiCatastaliVisibili", value);
				this._formLocalizzazioni.TipoCatasto.Visibile = value;
				this._formLocalizzazioni.Foglio.Visibile = value;
				this._formLocalizzazioni.Particella.Visibile = value;
				this._formLocalizzazioni.Sub.Visibile = value;
			}
		}

		public bool DatiCatastaliObbligatori
		{
			get
			{
				return ViewstateGet("DatiCatastaliObbligatori", true);
			}

			set
			{
				this.ViewStateSet("DatiCatastaliObbligatori", value);
				this._formLocalizzazioni.TipoCatasto.Etichetta = "TipoCatasto";
				this._formLocalizzazioni.Foglio.Etichetta = "Foglio";
				this._formLocalizzazioni.Particella.Etichetta = "Particella";
				this._formLocalizzazioni.Sub.Etichetta = "Subalterno";

				this._formLocalizzazioni.TipoCatasto.Obbligatorio = value;
				this._formLocalizzazioni.Foglio.Obbligatorio = value;
				this._formLocalizzazioni.Particella.Obbligatorio = value;
				this._formLocalizzazioni.Sub.Obbligatorio = value;
			}
		}

		//--------------------------------------------------
		public string AttivaConEndo
		{
			get { object o = this.ViewState["AttivaConEndo"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["AttivaConEndo"] = value; }
		}

        public string EscludiTipoCatasto
        {
            get { object o = this.ViewState["EscludiTipoCatasto"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["EscludiTipoCatasto"] = value; }
        }

        public int NumeroMassimoIndirizzi
        {
            get { object o = this.ViewState["NumeroMassimoIndirizzi"]; return o == null ? 999 : (int)o; }
            set { this.ViewState["NumeroMassimoIndirizzi"] = value; }
        }

		#endregion
		
		FormLocalizzazioni _formLocalizzazioni;


		public string CodiceComune
		{
			get { object o = this.ViewState["CodiceComune"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["CodiceComune"] = value; }
		}

		protected override void OnInit(EventArgs e)
		{
			this._formLocalizzazioni = new FormLocalizzazioni(this.ViewState)
			{
				CodiceCivico = new CampoHidden{ControlloEdit = HiddenCodiceCivico},
				CodiceViario = new CampoHidden{ControlloEdit =HiddenCodiceViario},
				Civico = new CampoLabeled { ControlloEdit = txtCivico},
				Esponente = new CampoLabeled { ControlloEdit = txtEsponente },
				Colore = new CampoLabeled { ControlloEdit = ddlColore },
				Scala = new CampoLabeled { ControlloEdit = txtScala },
				Piano = new CampoLabeled { ControlloEdit = txtPiano },
				Interno = new CampoLabeled { ControlloEdit = txtInterno },
				EsponenteInterno = new CampoLabeled { ControlloEdit = txtEsponenteInterno },
				Fabbricato = new CampoLabeled { ControlloEdit = txtFabbricato },
				Km = new CampoLabeled { ControlloEdit = txtKm, Colonna = dgStradario.Columns[Constants.IdColonnaKm] },
				Latitudine = new CampoLabeled { ControlloEdit = txtLatitudine},
				Longitudine = new CampoLabeled { ControlloEdit = txtLongitudine },
				TipoCatasto = new CampoDropDownLabeled( ddlTipoCatasto),
				Sezione = new CampoHidden { ControlloEdit = txtSezione },
				Foglio = new CampoLabeled { ControlloEdit = txtFoglio },
				Particella = new CampoLabeled { ControlloEdit = txtParticella },
				Sub = new CampoLabeled { ControlloEdit = txtSub },
				Note = new CampoLabeled { ControlloEdit = txtNote, Colonna = dgStradario.Columns[Constants.IdColonnaNote] }
			};

			base.OnInit(e);
		}


		protected void Page_Load(object sender, EventArgs e)
		{			
			// il service si occupa del salvataggio dei dati
			Master.IgnoraSalvataggioDati = true;

			CodiceComune = ReadFacade.Domanda.AltriDati.CodiceComune;

			if (!IsPostBack)
			{
				var listaColori = ReadFacade.Stradario.GetListaColori(this.IdComune);

				listaColori.Insert(0, new StradarioColore { CODICECOLORE = String.Empty, COLORE = String.Empty, IDCOMUNE = this.IdComune });

				ddlColore.DataSource = listaColori;
				ddlColore.DataBind();

				ddlTipoCatasto.Items.Add(new ListItem(String.Empty, String.Empty));

                if (!this.EscludiTipoCatasto.Contains("F"))
                {
                    ddlTipoCatasto.Items.Add(new ListItem("Fabbricati", "F"));
                }

                if (!this.EscludiTipoCatasto.Contains("T"))
                {
                    ddlTipoCatasto.Items.Add(new ListItem("Terreni", "T"));
                }

				DataBind();
			}
		}

		protected string FormattaAltriDati(object objIndirizzo)
		{
			var indirizzo = (IndirizzoStradario)objIndirizzo;

			if(!_formLocalizzazioni.Scala.Visibile && 
				   !_formLocalizzazioni.Piano.Visibile &&
				   !_formLocalizzazioni.Interno.Visibile &&
				   !_formLocalizzazioni.Fabbricato.Visibile )
			{
				dgStradario.Columns[Constants.IdColonnaAltriDati].Visible = false;
				return String.Empty;
			}

			var sb = new StringBuilder();

			if (_formLocalizzazioni.Scala.Visibile && !String.IsNullOrEmpty(indirizzo.Scala))
				sb.AppendFormat("Scala: {0}<br />", indirizzo.Scala);

			if (_formLocalizzazioni.Piano.Visibile && !String.IsNullOrEmpty(indirizzo.Piano))
				sb.AppendFormat("Piano: {0}<br />", indirizzo.Piano);

			if (_formLocalizzazioni.Interno.Visibile && !String.IsNullOrEmpty(indirizzo.Interno))
				sb.AppendFormat("Interno: {0}<br />", indirizzo.Interno);

			if (_formLocalizzazioni.Fabbricato.Visibile && !String.IsNullOrEmpty(indirizzo.Fabbricato))
				sb.AppendFormat("Fabbricato: {0}<br />", indirizzo.Fabbricato);

			return sb.ToString();
		}

		protected string FormattaCoordinate(object objIndirizzo)
		{
			var indirizzo = (IndirizzoStradario)objIndirizzo;

			if (!_formLocalizzazioni.Longitudine.Visibile)
			{
				dgStradario.Columns[Constants.IdColonnaCoordinate].Visible = false;
				return String.Empty;
			}
			var sb = new StringBuilder();

			if(!String.IsNullOrEmpty(indirizzo.Longitudine))
				sb.AppendFormat("Longitudine: {0}<br />Latitudine: {1}", indirizzo.Longitudine, indirizzo.Latitudine);

			return sb.ToString();

		}

		#region Ciclo della vita dello step

		public override bool CanEnterStep()
		{
			if (String.IsNullOrEmpty(AttivaConEndo))
				return true;

			var codiciEndoSelezionati = ReadFacade.Domanda.Endoprocedimenti.Endoprocedimenti.Select(x => x.Codice);
			var codiciEndoAttivazioneStep = AttivaConEndo.Split(',').Select(x => Convert.ToInt32(x.Trim()));

			foreach (var endoSelezionato in codiciEndoSelezionati)
			{
				if (codiciEndoAttivazioneStep.Contains(endoSelezionato))
					return true;
			}

			return false;
		}

		public override bool CanExitStep()
		{
			if (ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() == 0)
			{
				Errori.Add("Inserire almeno una localizzazione");
				return false;
			}

			return true;
		}

		#endregion

		public override void DataBind()
		{
			multiView.ActiveViewIndex = 0;

			dgStradario.DataSource = ReadFacade.Domanda.Localizzazioni.Indirizzi.Where( x => x.TipoLocalizzazione == this.TipoLocalizzazione );
			dgStradario.DataBind();

			this.Master.MostraPaginatoreSteps = true;
			this.Master.MostraBottoneAvanti = ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() > 0;

            var dataSourceComuni = ReadFacade.Stradario.GetComuniStradario(CodiceComune).ToArray();

            this.ddlComuneLocalizzazione.DataSource = dataSourceComuni;
			this.ddlComuneLocalizzazione.DataBind();
            this.ddlComuneLocalizzazione.Items.Insert(0, "");

            this.ddlComuneLocalizzazione.Visible = dataSourceComuni.Length > 0;

            this.cmdAddNew.Visible = ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() < NumeroMassimoIndirizzi;
		}

		/// <summary>
		/// Svuota tutti i controlli del panel di inserimento
		/// </summary>
		private void ClearDettaglio()
		{
            this.HiddenIdLocalizzazione.Value = String.Empty;
            this.HiddenCodiceCivico.Value = String.Empty;
            this.HiddenCodiceViario.Value = String.Empty;
            this.acIndirizzo.Value = String.Empty;
            this.acIndirizzo.Text = String.Empty;

			this._formLocalizzazioni.SvuotaCampiEdit();
			/*
			txtCivico.Value = String.Empty;
			txtEsponente.Value = String.Empty;
			ddlColore.Item.SelectedIndex = 0;
			txtScala.Value = String.Empty;
			txtInterno.Value = String.Empty;
			txtEsponenteInterno.Value = String.Empty;
			txtPiano.Value = String.Empty;
			txtFabbricato.Value = String.Empty;
			txtKm.Value = String.Empty;
			*/
			dgIndirizzi.Visible = false;

            if (ddlTipoCatasto.Items.Count == 2)
            {
                ddlTipoCatasto.SelectedIndex = 1;
            }

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

				 if (ddlComuneLocalizzazione.Items.Count > 1)
				{
					nomeVia = ddlComuneLocalizzazione.SelectedItem.Text + " - " + nomeVia;
				}

				var localizzazione = this._formLocalizzazioni.GetLocalizzazione(Convert.ToInt32(stradarioTrovato.CODICESTRADARIO), nomeVia, this.TipoLocalizzazione);
				var rifCatastali = this._formLocalizzazioni.GetRiferimentiCatastali();

                if (!String.IsNullOrEmpty(this.HiddenIdLocalizzazione.Value))
                {
                    this._localizzazioniService.EliminaLocalizzazione(IdDomanda, Convert.ToInt32(this.HiddenIdLocalizzazione.Value));
                }

				_localizzazioniService.AggiungiLocalizzazione(IdDomanda, localizzazione, rifCatastali);

				DataBind();

				return;
			}
			var comuneLocalizzazione = ddlComuneLocalizzazione.SelectedValue;
            var listaIndirizzi = ReadFacade.Stradario.GetByMatchParziale(IdComune, CodiceComune, comuneLocalizzazione, acIndirizzo.Text);

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
                acIndirizzo.Value = String.Empty;
                acIndirizzo.Text = String.Empty;
			}

		}

		public void cmdAddNew_Click(object sender, EventArgs e)
		{
			EditNew();
		}

		public void cmdConfirm_Click(object sender, EventArgs e)
		{
			dgIndirizzi.Visible = false;

			var erroriCompilazione = this._formLocalizzazioni.GetErroriValidazione();
			var erroriEspressioniRegolari = this._formLocalizzazioni.GetErroriEspressioniRegolari();
			var erroriValidazioneRange = this._formLocalizzazioni.GetErroriValidazioneRange();

			var erroriValidazione = erroriCompilazione.Union(erroriEspressioniRegolari).Union(erroriValidazioneRange);

			if (erroriValidazione.Count() > 0)
			{
				this.Errori.AddRange(erroriValidazione);

				return;
			}

			Stradario stradarioTrovato = CodiceStradarioTrovato() ? TrovaStradarioDaCodiceStradario() : TrovaStradarioDaIndirizzo();

			InserisciVoceStradario(stradarioTrovato);
		}

		private Stradario TrovaStradarioDaCodiceStradario()
		{
            return ReadFacade.Stradario.GetByCodiceStradario(IdComune, Convert.ToInt32(acIndirizzo.Value));
		}

		private Stradario TrovaStradarioDaIndirizzo()
		{
            return ReadFacade.Stradario.GetByIndirizzo(IdComune, CodiceComune, acIndirizzo.Text);
		}

		private bool CodiceStradarioTrovato()
		{
            return !String.IsNullOrEmpty(acIndirizzo.Value);
		}
		public void cmdCancel_Click(object sender, EventArgs e)
		{
			DataBind();
		}

        protected void dgStradario_RowEditing(object sender, GridViewEditEventArgs e)
        {
            var key = Convert.ToInt32(dgStradario.DataKeys[e.NewEditIndex].Value);

            var indirizzo = ReadFacade.Domanda.Localizzazioni.Indirizzi.Where(x => x.Id == key).FirstOrDefault();

            Edit(indirizzo);

            e.Cancel = true;
        }

        private void Edit(IndirizzoStradario i)
        {

            this.ClearDettaglio();
            this.Edit();

            var stradario = ReadFacade.Stradario.GetByCodiceStradario(IdComune, i.CodiceStradario);

            if (stradario != null && !String.IsNullOrEmpty(stradario.CodiceComuneLocalizzazione))
            {
                this.ddlComuneLocalizzazione.SelectedValue = stradario.CodiceComuneLocalizzazione;
            }

            this.HiddenIdLocalizzazione.Value = i.Id.ToString();
            this.HiddenCodiceCivico.Value = i.CodiceCivico;
            this.HiddenCodiceViario.Value = i.CodiceViario;
            this.acIndirizzo.Value = i.CodiceStradario.ToString();
            this.acIndirizzo.Text = i.Indirizzo;

            this.txtCivico.Text = i.Civico;
            this.txtEsponente.Value = i.Esponente;
            this.ddlColore.Value = i.Colore;
            this.txtScala.Value = i.Scala;
            this.txtPiano.Value = i.Piano;
            this.txtInterno.Value = i.Interno;
            this.txtEsponenteInterno.Value = i.EsponenteInterno;
            this.txtFabbricato.Value = i.Fabbricato;
            this.txtKm.Value = i.Km;
            this.txtNote.Value = i.Note;
            this.txtLongitudine.Value = i.Longitudine;
            this.txtLatitudine.Value = i.Latitudine;

            if (i.RiferimentiCatastali.Count() > 0)
            {
                var rc = i.RiferimentiCatastali.First();

                this.ddlTipoCatasto.Value = rc.CodiceTipoCatasto;
                this.txtSezione.Value = rc.Sezione;
                this.txtFoglio.Value = rc.Foglio;
                this.txtParticella.Value = rc.Particella;
                this.txtSub.Value = rc.Sub;
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
	}
}