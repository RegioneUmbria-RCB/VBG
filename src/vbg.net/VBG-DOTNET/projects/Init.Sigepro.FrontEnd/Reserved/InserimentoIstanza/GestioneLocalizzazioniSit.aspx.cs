using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit;
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
    public partial class GestioneLocalizzazioniSit : IstanzeStepPage
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
        protected ISitService _sitService { get; set; }

        [Inject]
        protected IConfigurazione<ParametriSIT> ConfigurazioneSit { get; set; }

        #region dati letti dai parametri del workflow
        public string CivicoEtichetta
        {
            set { this._formLocalizzazioni.Civico.Etichetta = value; }
        }

        public bool CivicoObbligatorio
        {
            set { this._formLocalizzazioni.Civico.Obbligatorio = value; }
        }
        //---------------------------------------

        public string EsponenteEtichetta
        {
            set { this._formLocalizzazioni.Esponente.Etichetta = value; }
        }

        public bool EsponenteObbligatorio
        {
            set { this._formLocalizzazioni.Esponente.Obbligatorio = value; }
        }

        //-------------------------------------------

        public string ColoreEtichetta
        {
            set { this._formLocalizzazioni.Colore.Etichetta = value; }
        }

        public bool ColoreObbligatorio
        {
            set { this._formLocalizzazioni.Colore.Obbligatorio = value; }
        }

        //-------------------------------------------------


        public string ScalaEtichetta
        {
            set { this._formLocalizzazioni.Scala.Etichetta = value; }
        }

        public bool ScalaObbligatorio
        {
            set { this._formLocalizzazioni.Scala.Obbligatorio = value; }
        }

        //--------------------------------------------------


        public string PianoEtichetta
        {
            set { this._formLocalizzazioni.Piano.Etichetta = value; }
        }

        public bool PianoObbligatorio
        {
            set { this._formLocalizzazioni.Piano.Obbligatorio = value; }
        }

        //--------------------------------------------------

        public string InternoEtichetta
        {
            set { this._formLocalizzazioni.Interno.Etichetta = value; }
        }

        public bool InternoObbligatorio
        {
            set { this._formLocalizzazioni.Interno.Obbligatorio = value; }
        }

        //--------------------------------------------------

        public string EsponenteInternoEtichetta
        {
            set { this._formLocalizzazioni.EsponenteInterno.Etichetta = value; }
        }

        public bool EsponenteInternoObbligatorio
        {
            set { this._formLocalizzazioni.EsponenteInterno.Obbligatorio = value; }
        }

        //--------------------------------------------------

        public string FabbricatoEtichetta
        {
            set { this._formLocalizzazioni.Fabbricato.Etichetta = value; }
        }

        public bool FabbricatoObbligatorio
        {
            set { this._formLocalizzazioni.Fabbricato.Obbligatorio = value; }
        }

        //--------------------------------------------------

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



        #endregion

        private FormLocalizzazioni _formLocalizzazioni;


        public string CodiceComune
        {
            get { object o = this.ViewState["CodiceComune"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CodiceComune"] = value; }
        }

        public bool MostraLocalizzazioneDaIndirizzo
        {
            get { object o = this.ViewState["MostraLocalizzazioneDaIndirizzo"]; return o == null ? false : (bool)o; }
            set { this.ViewState["MostraLocalizzazioneDaIndirizzo"] = value; }
        }

        public bool MostraLocalizzazioneDaMappali
        {
            get { object o = this.ViewState["MostraLocalizzazioneDaMappali"]; return o == null ? false : (bool)o; }
            set { this.ViewState["MostraLocalizzazioneDaMappali"] = value; }
        }

        public string UrlLocalizzazioneDaIndirizzo
        {
            get { object o = this.ViewState["UrlLocalizzazioneDaIndirizzo"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["UrlLocalizzazioneDaIndirizzo"] = value; }
        }

        public string UrlLocalizzazioneDaMappali
        {
            get { object o = this.ViewState["UrlLocalizzazioneDaMappali"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["UrlLocalizzazioneDaMappali"] = value; }
        }

        protected override void OnInit(EventArgs e)
        {


            this._formLocalizzazioni = new FormLocalizzazioni(this.ViewState)
            {
                Civico = new CampoLabeled { ControlloEdit = this.txtCivico },
                Esponente = new CampoLabeled { ControlloEdit = this.txtEsponente },
                Colore = new CampoLabeled { ControlloEdit = this.ddlColore },
                Scala = new CampoLabeled { ControlloEdit = this.txtScala },
                Piano = new CampoLabeled { ControlloEdit = this.txtPiano },
                Interno = new CampoLabeled { ControlloEdit = this.txtInterno },
                EsponenteInterno = new CampoLabeled { ControlloEdit = this.txtEsponenteInterno },
                Fabbricato = new CampoLabeled { ControlloEdit = this.txtFabbricato },
                Km = new CampoLabeled { ControlloEdit = this.txtKm, Colonna = this.dgStradario.Columns[Constants.IdColonnaKm] },
                Latitudine = new CampoLabeled { ControlloEdit = this.txtLatitudine },
                Longitudine = new CampoLabeled { ControlloEdit = this.txtLongitudine },
                TipoCatasto = new CampoDropDownLabeled(this.ddlTipoCatasto),
                Foglio = new CampoLabeled { ControlloEdit = this.txtFoglio },
                Particella = new CampoLabeled { ControlloEdit = this.txtParticella },
                Sub = new CampoLabeled { ControlloEdit = this.txtSub },
                Note = new CampoLabeled { ControlloEdit = this.txtNote },
                Sezione = new CampoHidden { ControlloEdit = this.hiddenCodCivico },
                CodiceCivico = new CampoHidden { ControlloEdit = this.hiddenCodCivico },
                CodiceViario = new CampoHidden { ControlloEdit = this.hiddenCodViario },
                AccessoTipo = new CampoLabeled { ControlloEdit = this.txtAccessoTipo },
                AccessoNumero = new CampoLabeled { ControlloEdit = this.txtAccessoNumero },
                AccessoDescrizione = new CampoLabeled { ControlloEdit = this.txtAccessoDescrizione }
            };

            base.OnInit(e);
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.ConfigurazioneSit?.Parametri?.Attivo ?? false)
            {
                var url = UrlBuilder.Url("~/reserved/inserimentoistanza/gestionelocalizzazioni.aspx", mp =>
                {
                    mp.Add(new QsIdDomandaOnline(this.IdDomanda));
                    mp.Add(new QsSoftware(this.Software));
                    mp.Add(new QsAliasComune(this.IdComune));
                    mp.Add(new QsStepId(this.Request.QueryString));
                });

                this.Response.Redirect(url);
            }

            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            if (!this.IsPostBack)
            {
                /*
                var listaColori = this.ReadFacade.Stradario.GetListaColori(this.IdComune);
                listaColori.Insert(0, new StradarioColore { CODICECOLORE = String.Empty, COLORE = String.Empty, IDCOMUNE = this.IdComune });
                this.ddlColore.DataSource = listaColori;
                this.ddlColore.DataBind();
                */
                this.ddlTipoCatasto.Items.Add(new ListItem(String.Empty, String.Empty));
                this.ddlTipoCatasto.Items.Add(new ListItem("Fabbricati", "F"));
                this.ddlTipoCatasto.Items.Add(new ListItem("Terreni", "T"));

                var features = this._sitService.GetFeatures();
                var campiSupportati = features.CampiSupportati;

                this._formLocalizzazioni.Civico.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Civico);
                this._formLocalizzazioni.Esponente.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Esponente);
                this._formLocalizzazioni.Colore.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Colore);
                this._formLocalizzazioni.Scala.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Scala);
                this._formLocalizzazioni.Piano.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Piano);
                this._formLocalizzazioni.Interno.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Interno);
                this._formLocalizzazioni.EsponenteInterno.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.EsponenteInterno);
                this._formLocalizzazioni.Fabbricato.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Fabbricato);
                this._formLocalizzazioni.Km.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Km);
                this._formLocalizzazioni.Latitudine.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Coordinate);
                this._formLocalizzazioni.Longitudine.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Coordinate);


                this._formLocalizzazioni.Foglio.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Foglio);
                this._formLocalizzazioni.Particella.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Particella);
                this._formLocalizzazioni.Sub.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.Sub);

                this._formLocalizzazioni.TipoCatasto.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.TipoCatasto);

                this._formLocalizzazioni.AccessoTipo.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.AccessoTipo);
                this._formLocalizzazioni.AccessoNumero.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.AccessoNumero);
                this._formLocalizzazioni.AccessoDescrizione.Visibile = campiSupportati.Supporta(SitCampiSupportati.Campi.AccessoDescrizione);

                this.MostraLocalizzazioneDaMappali = features.VisualizzazioniSupportate.SupportaVisualizzazioneMappaDaMappale();
                this.UrlLocalizzazioneDaIndirizzo = features.VisualizzazioniSupportate.UrlVisualizzazioneMappaDaIndirizzo();
                this.UrlLocalizzazioneDaMappali = features.VisualizzazioniSupportate.UrlVisualizzazioneMappaDaMappale();




                this.DataBind();
            }

            this.CodiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune;
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

        }

        /// <summary>
        /// Svuota tutti i controlli del panel di inserimento
        /// </summary>
        private void ClearDettaglio()
        {
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
            this.hiddenIdLocalizzazione.Value = "";
        }

        protected void Edit()
        {
            this.multiView.ActiveViewIndex = 1;
            this.Master.MostraPaginatoreSteps = false;
        }

        protected void Edit(IndirizzoStradario indirizzoStradario)
        {
            this.Edit();

            this.hiddenIdLocalizzazione.Value = indirizzoStradario.Id.ToString();
            this.acIndirizzo.Text = indirizzoStradario.Indirizzo;
            this.acIndirizzo.Value = indirizzoStradario.CodiceStradario.ToString();

            this.hiddenCodViario.Value = indirizzoStradario.CodiceViario;

            this.txtCivico.Value = indirizzoStradario.Civico;
            this.hiddenCodCivico.Value = indirizzoStradario.CodiceCivico;
            this.txtScala.Value = indirizzoStradario.Scala;
            this.txtEsponente.Value = indirizzoStradario.Esponente;
            this.txtEsponenteInterno.Value = indirizzoStradario.EsponenteInterno;
            this.txtInterno.Value = indirizzoStradario.Interno;
            this.txtFabbricato.Value = indirizzoStradario.Fabbricato;

            var rifCatastali = indirizzoStradario.RiferimentiCatastali;
            if (rifCatastali.Count() > 0)
            {
                this.txtFoglio.Value = rifCatastali.FirstOrDefault().Foglio;
                this.txtParticella.Value = rifCatastali.FirstOrDefault().Particella;
                this.txtSub.Value = rifCatastali.FirstOrDefault().Sub;
            }
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
                var nomeVia = stradarioTrovato.Prefisso + " " + stradarioTrovato.Descrizione;

                if (!String.IsNullOrEmpty(stradarioTrovato.LocFraz))
                    nomeVia += " (" + stradarioTrovato.LocFraz + ")";

                var localizzazione = this._formLocalizzazioni.GetLocalizzazione(Convert.ToInt32(stradarioTrovato.CodiceStradario), nomeVia, this.TipoLocalizzazione);
                var rifCatastali = this._formLocalizzazioni.GetRiferimentiCatastali();

                if (!String.IsNullOrEmpty(this.hiddenIdLocalizzazione.Value))
                {
                    this._localizzazioniService.EliminaLocalizzazione(this.IdDomanda, Convert.ToInt32(this.hiddenIdLocalizzazione.Value));
                }

                this._localizzazioniService.AggiungiLocalizzazione(this.IdDomanda, localizzazione, rifCatastali);

                this.DataBind();

                return;
            }

            var comuneLocalizzazione = String.Empty;
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

        /// <summary>
        /// Handler dell'evento click sul bottone di eliminazione riga della datagrid di riepilogo
        /// </summary>
        public void dgStradario_DeleteCommand(object source, GridViewDeleteEventArgs e)
        {
            int key = Convert.ToInt32(this.dgStradario.DataKeys[e.RowIndex].Value);

            this._localizzazioniService.EliminaLocalizzazione(this.IdDomanda, key);

            this.DataBind();
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

        protected void dgStradario_SelectedIndexChanged(object sender, EventArgs e)
        {
            int key = Convert.ToInt32(this.dgStradario.DataKeys[this.dgStradario.SelectedIndex].Value);

            var indirizzoStradario = this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Where(x => x.Id == key).FirstOrDefault();
            this.Edit(indirizzoStradario);
        }
    }
}