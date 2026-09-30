using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni;
using Init.SIGePro.Manager.DTO.StradarioComune;
using Ninject;
using System;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;

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

        [Inject]
        protected IConfigurazione<ParametriSIT> _parametriSIT { get; set; }

        protected bool CivicoNumerico { get { return this._configurazione.Parametri.UsaCiviciNumerici; } }

        protected bool EsponenteNumerico { get { return this._configurazione.Parametri.UsaEsponentiNumerici; } }

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
                return this.ViewstateGet("DatiCatastaliVisibili", true);
            }
            set
            {
                this.ViewStateSet("DatiCatastaliVisibili", value);
                this._formLocalizzazioni.TipoCatasto.Visibile = value;
                this._formLocalizzazioni.Foglio.Visibile = value;
                this._formLocalizzazioni.Particella.Visibile = value;
                this._formLocalizzazioni.Sub.Visibile = value;
                this.dgStradario.Columns[Constants.IdColonnaRiferimentiCatastali].Visible = value;

            }
        }

        public bool DatiCatastaliObbligatori
        {
            get
            {
                return this.ViewstateGet("DatiCatastaliObbligatori", true);
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

        private FormLocalizzazioni _formLocalizzazioni;


        public string CodiceComune
        {
            get { object o = this.ViewState["CodiceComune"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CodiceComune"] = value; }
        }

        protected override void OnInit(EventArgs e)
        {
            this._formLocalizzazioni = new FormLocalizzazioni(this.ViewState)
            {
                CodiceCivico = new CampoHidden { ControlloEdit = HiddenCodiceCivico },
                CodiceViario = new CampoHidden { ControlloEdit = HiddenCodiceViario },
                Civico = new CampoLabeled { ControlloEdit = txtCivico },
                Esponente = new CampoLabeled { ControlloEdit = txtEsponente },
                Colore = new CampoLabeled { ControlloEdit = ddlColore },
                Scala = new CampoLabeled { ControlloEdit = txtScala },
                Piano = new CampoLabeled { ControlloEdit = txtPiano },
                Interno = new CampoLabeled { ControlloEdit = txtInterno },
                EsponenteInterno = new CampoLabeled { ControlloEdit = txtEsponenteInterno },
                Fabbricato = new CampoLabeled { ControlloEdit = txtFabbricato },
                Km = new CampoLabeled { ControlloEdit = txtKm, Colonna = this.dgStradario.Columns[Constants.IdColonnaKm] },
                Latitudine = new CampoLabeled { ControlloEdit = txtLatitudine },
                Longitudine = new CampoLabeled { ControlloEdit = txtLongitudine },
                TipoCatasto = new CampoDropDownLabeled(this.ddlTipoCatasto),
                Sezione = new CampoHidden { ControlloEdit = txtSezione },
                Foglio = new CampoLabeled { ControlloEdit = txtFoglio },
                Particella = new CampoLabeled { ControlloEdit = txtParticella },
                Sub = new CampoLabeled { ControlloEdit = txtSub },
                Note = new CampoLabeled { ControlloEdit = txtNote, Colonna = this.dgStradario.Columns[Constants.IdColonnaNote] },
                AccessoTipo = new CampoHidden { ControlloEdit = txtAccessoTipo },
                AccessoNumero = new CampoHidden { ControlloEdit = txtAccessoNumero },
                AccessoDescrizione = new CampoHidden { ControlloEdit = txtAccessoDescrizione }
            };

            base.OnInit(e);
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            this.CodiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune;

            if (!this.IsPostBack)
            {

                var listaColori = this.ReadFacade.Stradario.GetListaColori(this.IdComune).ToList();
                listaColori.Insert(0, new ColoreStradarioDto { CodiceColore = String.Empty, Colore = String.Empty });
                this.ddlColore.DataSource = listaColori;
                this.ddlColore.DataBind();

                this.ddlTipoCatasto.Items.Add(new ListItem(String.Empty, String.Empty));

                if (!this.EscludiTipoCatasto.Contains("F"))
                {
                    this.ddlTipoCatasto.Items.Add(new ListItem("Fabbricati", "F"));
                }

                if (!this.EscludiTipoCatasto.Contains("T"))
                {
                    this.ddlTipoCatasto.Items.Add(new ListItem("Terreni", "T"));
                }

                this.DataBind();
            }
        }

        protected string FormattaAltriDati(object objIndirizzo)
        {
            var indirizzo = (IndirizzoStradario)objIndirizzo;

            if (!this._formLocalizzazioni.Scala.Visibile &&
                   !this._formLocalizzazioni.Piano.Visibile &&
                   !this._formLocalizzazioni.Interno.Visibile &&
                   !this._formLocalizzazioni.Fabbricato.Visibile)
            {
                this.dgStradario.Columns[Constants.IdColonnaAltriDati].Visible = false;
                return String.Empty;
            }

            var sb = new StringBuilder();

            if (this._formLocalizzazioni.Scala.Visibile && !String.IsNullOrEmpty(indirizzo.Scala))
                sb.AppendFormat("Scala: {0}<br />", indirizzo.Scala);

            if (this._formLocalizzazioni.Piano.Visibile && !String.IsNullOrEmpty(indirizzo.Piano))
                sb.AppendFormat("Piano: {0}<br />", indirizzo.Piano);

            if (this._formLocalizzazioni.Interno.Visibile && !String.IsNullOrEmpty(indirizzo.Interno))
                sb.AppendFormat("Interno: {0}<br />", indirizzo.Interno);

            if (this._formLocalizzazioni.Fabbricato.Visibile && !String.IsNullOrEmpty(indirizzo.Fabbricato))
                sb.AppendFormat("Fabbricato: {0}<br />", indirizzo.Fabbricato);

            return sb.ToString();
        }

        protected string FormattaCoordinate(object objIndirizzo)
        {
            var indirizzo = (IndirizzoStradario)objIndirizzo;

            if (!this._formLocalizzazioni.Longitudine.Visibile)
            {
                this.dgStradario.Columns[Constants.IdColonnaCoordinate].Visible = false;
                return String.Empty;
            }
            var sb = new StringBuilder();

            if (!String.IsNullOrEmpty(indirizzo.Longitudine))
                sb.AppendFormat("Longitudine: {0}<br />Latitudine: {1}", indirizzo.Longitudine, indirizzo.Latitudine);

            return sb.ToString();

        }

        #region Ciclo della vita dello step

        public override bool CanEnterStep()
        {
            if (this._parametriSIT.Parametri.ForzaStepLocalizzazioniSit)
            {
                var url = UrlBuilder.Url("~/reserved/inserimentoistanza/GestioneLocalizzazioniSit.aspx", mp =>
                {
                    mp.Add(new QsAliasComune(this.IdComune));
                    mp.Add(new QsSoftware(this.Software));
                    mp.Add(new QsStepId(this.Request.QueryString));
                    mp.Add(new QsIdDomandaOnline(this.IdDomanda));
                });

                this.Response.Redirect(url);
                return false;
            }

            if (String.IsNullOrEmpty(this.AttivaConEndo))
                return true;

            var codiciEndoSelezionati = this.ReadFacade.Domanda.Endoprocedimenti.Endoprocedimenti.Select(x => x.Codice);
            var codiciEndoAttivazioneStep = this.AttivaConEndo.Split(',').Select(x => Convert.ToInt32(x.Trim()));

            foreach (var endoSelezionato in codiciEndoSelezionati)
            {
                if (codiciEndoAttivazioneStep.Contains(endoSelezionato))
                    return true;
            }

            return false;
        }

        public override bool CanExitStep()
        {
            if (this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() == 0)
            {
                this.Errori.Add("Inserire almeno una localizzazione");
                return false;
            }

            return true;
        }

        #endregion

        public override void DataBind()
        {
            this.multiView.ActiveViewIndex = 0;

            this.dgStradario.DataSource = this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Where(x => x.TipoLocalizzazione == this.TipoLocalizzazione);
            this.dgStradario.DataBind();

            this.Master.MostraPaginatoreSteps = true;
            this.Master.MostraBottoneAvanti = this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() > 0;

            var dataSourceComuni = this.ReadFacade.Stradario.GetComuniStradario(this.CodiceComune).ToArray();

            this.ddlComuneLocalizzazione.DataSource = dataSourceComuni;
            this.ddlComuneLocalizzazione.DataBind();
            this.ddlComuneLocalizzazione.Items.Insert(0, "");

            this.ddlComuneLocalizzazione.Visible = dataSourceComuni.Length > 0;

            this.cmdAddNew.Visible = this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() < this.NumeroMassimoIndirizzi;
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
            this.dgIndirizzi.Visible = false;

            if (this.ddlTipoCatasto.Items.Count == 2)
            {
                this.ddlTipoCatasto.SelectedIndex = 1;
            }

        }

        protected void Edit()
        {
            this.multiView.ActiveViewIndex = 1;
            this.Master.MostraPaginatoreSteps = false;
        }

        protected void EditNew()
        {
            this.ClearDettaglio();
            this.Edit();
        }

        /// <summary>
        /// Delegate della selezione di un elemento della dataGrid dei risultati della ricerca nello stradario
        /// </summary>
        public void dgIndirizzi_SelectedIndexChanged(object sender, EventArgs e)
        {
            var codiceStradario = Convert.ToInt32(this.dgIndirizzi.DataKeys[this.dgIndirizzi.SelectedIndex].Value);

            this.InserisciVoceStradario(this.ReadFacade.Stradario.GetByCodiceStradario(this.IdComune, codiceStradario));
        }

        /// <summary>
        /// Aggiunge la voce di stradario trovata alla lista degli indirizzi dell'istanza
        /// </summary>
        /// <param name="stradarioTrovato">Voce dello stradario trovata o null se si deve effettuare una ricerca per match parziale</param>
        private void InserisciVoceStradario(StradarioEstesoDto stradarioTrovato)
        {
            if (stradarioTrovato != null)
            {
                var civicoValido = this._civicoValidoSpecification.IsSatisfiedBy(this.txtCivico.Value);
                var esponenteValido = this._esponenteValidoSpecification.IsSatisfiedBy(this.txtEsponente.Value);

                if (!civicoValido)
                {
                    this.Errori.Add("Il civico immesso non è valido. Il campo può contenere solamente valori numerici");
                }

                if (!esponenteValido)
                {
                    this.Errori.Add("L'esponente immesso non è valido. Il campo può contenere solamente valori numerici");
                }

                if (!(civicoValido && esponenteValido))
                {
                    return;
                }

                var nomeVia = stradarioTrovato.Prefisso + " " + stradarioTrovato.Descrizione;

                if (!String.IsNullOrEmpty(stradarioTrovato.LocFraz))
                    nomeVia += " (" + stradarioTrovato.LocFraz + ")";

                if (this.ddlComuneLocalizzazione.Items.Count > 1)
                {
                    nomeVia = this.ddlComuneLocalizzazione.SelectedItem.Text + " - " + nomeVia;
                }

                var localizzazione = this._formLocalizzazioni.GetLocalizzazione(Convert.ToInt32(stradarioTrovato.CodiceStradario), nomeVia, this.TipoLocalizzazione);
                var rifCatastali = this._formLocalizzazioni.GetRiferimentiCatastali();

                if (!String.IsNullOrEmpty(this.HiddenIdLocalizzazione.Value))
                {
                    this._localizzazioniService.EliminaLocalizzazione(this.IdDomanda, Convert.ToInt32(this.HiddenIdLocalizzazione.Value));
                }

                this._localizzazioniService.AggiungiLocalizzazione(this.IdDomanda, localizzazione, rifCatastali);

                this.DataBind();

                return;
            }
            var comuneLocalizzazione = this.ddlComuneLocalizzazione.SelectedValue;
            var listaIndirizzi = this.ReadFacade.Stradario.GetByMatchParziale(this.IdComune, this.CodiceComune, comuneLocalizzazione, this.acIndirizzo.Text);

            if (listaIndirizzi.Count > 0)
            {
                this.Errori.Add("Indirizzo non trovato. Sono però stati trovati i seguenti record simili");

                this.dgIndirizzi.DataSource = listaIndirizzi;
                this.dgIndirizzi.DataBind();

                this.dgIndirizzi.Visible = true;
            }
            else
            {
                this.Errori.Add("Indirizzo non trovato. Verificare i dati immessi");
                this.acIndirizzo.Value = String.Empty;
                this.acIndirizzo.Text = String.Empty;
            }

        }

        public void cmdAddNew_Click(object sender, EventArgs e)
        {
            this.EditNew();
        }

        public void cmdConfirm_Click(object sender, EventArgs e)
        {
            this.dgIndirizzi.Visible = false;

            var erroriCompilazione = this._formLocalizzazioni.GetErroriValidazione();
            var erroriEspressioniRegolari = this._formLocalizzazioni.GetErroriEspressioniRegolari();
            var erroriValidazioneRange = this._formLocalizzazioni.GetErroriValidazioneRange();

            var erroriValidazione = erroriCompilazione.Union(erroriEspressioniRegolari).Union(erroriValidazioneRange);

            if (erroriValidazione.Count() > 0)
            {
                this.Errori.AddRange(erroriValidazione);

                return;
            }

            StradarioEstesoDto stradarioTrovato = this.CodiceStradarioTrovato() ? this.TrovaStradarioDaCodiceStradario() : this.TrovaStradarioDaIndirizzo();

            this.InserisciVoceStradario(stradarioTrovato);
        }

        private StradarioEstesoDto TrovaStradarioDaCodiceStradario()
        {
            return this.ReadFacade.Stradario.GetByCodiceStradario(this.IdComune, Convert.ToInt32(this.acIndirizzo.Value));
        }

        private StradarioEstesoDto TrovaStradarioDaIndirizzo()
        {
            return this.ReadFacade.Stradario.GetByIndirizzo(this.IdComune, this.CodiceComune, this.acIndirizzo.Text);
        }

        private bool CodiceStradarioTrovato()
        {
            return !String.IsNullOrEmpty(this.acIndirizzo.Value);
        }
        public void cmdCancel_Click(object sender, EventArgs e)
        {
            this.DataBind();
        }

        protected void dgStradario_RowEditing(object sender, GridViewEditEventArgs e)
        {
            var key = Convert.ToInt32(this.dgStradario.DataKeys[e.NewEditIndex].Value);

            var indirizzo = this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Where(x => x.Id == key).FirstOrDefault();

            this.Edit(indirizzo);

            e.Cancel = true;
        }

        private void Edit(IndirizzoStradario i)
        {

            this.ClearDettaglio();
            this.Edit();

            var stradario = this.ReadFacade.Stradario.GetByCodiceStradario(this.IdComune, i.CodiceStradario);

            if (stradario != null && !String.IsNullOrEmpty(stradario.ComuneLocalizzazione?.CodiceComune))
            {
                this.ddlComuneLocalizzazione.SelectedValue = stradario.ComuneLocalizzazione?.CodiceComune;
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
            int key = Convert.ToInt32(this.dgStradario.DataKeys[e.RowIndex].Value);

            this._localizzazioniService.EliminaLocalizzazione(this.IdDomanda, key);

            this.DataBind();
        }
    }
}