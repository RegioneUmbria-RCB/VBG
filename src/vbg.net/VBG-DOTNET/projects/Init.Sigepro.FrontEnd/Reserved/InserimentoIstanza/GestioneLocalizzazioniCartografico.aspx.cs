using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
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
    public partial class GestioneLocalizzazioniCartografico : IstanzeStepPage
    {
        private static class Constants
        {
            public const int IdColonnaKm = 1;
            public const int IdColonnaCoordinate = 2;
        }

        [Inject]
        public ILocalizzazioniSICSyncService _localizzazioniSicService { get; set; }
        [Inject]
        public ILocalizzazioniSICDomandaService _localizzazioniSicDomandaService { get; set; }

        [Inject]
        public IDatiDinamiciService _datiDinamiciService { get; set; }

        #region dati letti dai parametri del workflow
        [StepProperty]
        public string KmEtichetta
        {
            set { this._formLocalizzazioni.Km.Etichetta = value; }
        }

        [StepProperty]
        public string NoteEtichetta
        {
            set { this._formLocalizzazioni.Note.Etichetta = value; }
        }

        [StepProperty]
        public bool NoteObbligatorio
        {
            set { this._formLocalizzazioni.Note.Obbligatorio = value; }
        }

        //--------------------------------------------------

        [StepProperty]
        public string TipoLocalizzazione
        {
            get { object o = this.ViewState["TipoLocalizzazione"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["TipoLocalizzazione"] = value; }
        }

        //--------------------------------------------------

        [StepProperty]
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

        [StepProperty]
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

        [StepProperty]
        public string CoordinateEtichettaLongitudine
        {
            set
            {
                this._formLocalizzazioni.Longitudine.Etichetta = value;
            }
        }
        [StepProperty]
        public string CoordinateEtichettaLatitudine
        {
            set
            {
                this._formLocalizzazioni.Latitudine.Etichetta = value;
            }
        }
        [StepProperty]
        public string CoordinateEspressioneRegolare
        {
            set
            {
                this._formLocalizzazioni.Longitudine.EspressioneRegolare = value;
                this._formLocalizzazioni.Latitudine.EspressioneRegolare = value;
            }
        }

        [StepProperty]
        public string CoordinateLongitudineValoreMin
        {
            set { this._formLocalizzazioni.Longitudine.ValoreMin = value; }
        }

        [StepProperty]
        public string CoordinateLatitudineValoreMin
        {
            set { this._formLocalizzazioni.Latitudine.ValoreMin = value; }
        }

        [StepProperty]
        public string CoordinateLongitudineValoreMax
        {
            set { this._formLocalizzazioni.Longitudine.ValoreMax = value; }
        }

        [StepProperty]
        public string CoordinateLatitudineValoreMax
        {
            set { this._formLocalizzazioni.Latitudine.ValoreMax = value; }
        }

        [StepProperty]
        public string CoordinateTitoloBlocco
        {
            set
            {
                this.ltrTitoloBloccoCoordinate.Text = value;
            }
        }

        //--------------------------------------------------

        [StepProperty]
        public bool DatiCatastaliVisibili
        {
            get
            {
                //non sono gestiti i dati catastali in questa integrazione
                return false;
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

        //--------------------------------------------------

        [StepProperty]
        public int NumeroMassimoIndirizzi
        {
            get { object o = this.ViewState["NumeroMassimoIndirizzi"]; return o == null ? 999 : (int)o; }
            set { this.ViewState["NumeroMassimoIndirizzi"] = value; }
        }

        //--------------------------------------------------

        [StepProperty]
        public string CodiceComune
        {
            get { object o = this.ViewState["CodiceComune"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CodiceComune"] = value; }
        }

        //--------------------------------------------------

        [StepProperty]
        public string ModelloDinamicoJsonDatiAggiuntivi
        {
            get { object o = this.ViewState["ModelloDinamicoJsonDatiAggiuntivi"]; return o == null ? null : (string)o; }
            set { this.ViewState["ModelloDinamicoJsonDatiAggiuntivi"] = value; }
        }

        //--------------------------------------------------

        [StepProperty]
        public string CampoDinamicoJsonDatiAggiuntivi
        {
            get { object o = this.ViewState["CampoDinamicoJsonDatiAggiuntivi"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CampoDinamicoJsonDatiAggiuntivi"] = value; }
        }

        //--------------------------------------------------

        #endregion

        private FormLocalizzazioni _formLocalizzazioni;
        private QsStepId _qsStepId => new QsStepId(this.Request.QueryString);
        private QsUuidLocalizzazione _qsUuidLocalizzazione => new QsUuidLocalizzazione(this.Request.QueryString);
        private bool _isReturning
        {
            get
            {
                return new QsReturning(this.Request.QueryString).Value == "true";
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.ValidaForm();

            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;


            this.CodiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune;

            if (!this.IsPostBack)
            {
                this.DataBind();

                if (this._isReturning && !String.IsNullOrEmpty(this._qsUuidLocalizzazione.Value))
                {
                    //1. Verifico se si tratta di localizzazione già presente nella domanda
                    var indirizzo = this.ReadFacade.Domanda.Localizzazioni.Indirizzi.FirstOrDefault(x => x.Uuid == this._qsUuidLocalizzazione.Value);
                    if (indirizzo == null)
                    {
                        this.EditNew();
                        this.txtUuid.Text = this._qsUuidLocalizzazione.Value;
                    }
                    else
                    {
                        this.Edit(indirizzo);
                    }

                    //chiamata per parametri da spostare sul service
                    this.RecuperaInfoAggiuntive();
                }
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            var feats = this._localizzazioniSicService.GetFeatures();

            this.cmdMostraMappa.Visible = feats.MostraMappaPresentazioneIstanza;
        }

        protected override void OnInit(EventArgs e)
        {
            this._formLocalizzazioni = new FormLocalizzazioni(this.ViewState)
            {
                AccessoDescrizione = new CampoHidden { ControlloEdit = this.HiddenAccessoDescrizione },
                AccessoNumero = new CampoHidden { ControlloEdit = this.HiddenAccessoNumero },
                AccessoTipo = new CampoHidden { ControlloEdit = this.HiddenAccessoTipo },
                Civico = new CampoHidden { ControlloEdit = this.HiddenCivico },
                CodiceCivico = new CampoHidden { ControlloEdit = this.HiddenCodiceCivico },
                CodiceViario = new CampoHidden { ControlloEdit = this.HiddenCodiceViario },
                Colore = new CampoHidden { ControlloEdit = this.HiddenColore },
                Esponente = new CampoHidden { ControlloEdit = this.HiddenEsponente },
                EsponenteInterno = new CampoHidden { ControlloEdit = this.HiddenEsponenteInterno },
                Fabbricato = new CampoHidden { ControlloEdit = this.HiddenFabbricato },
                Foglio = new CampoLabeled { ControlloEdit = this.txtFoglio },
                Interno = new CampoHidden { ControlloEdit = this.HiddenInterno },
                Km = new CampoLabeled { ControlloEdit = this.txtKm, Colonna = this.dgStradario.Columns[Constants.IdColonnaKm] },
                Latitudine = new CampoLabeled { ControlloEdit = this.txtLatitudine },
                Longitudine = new CampoLabeled { ControlloEdit = this.txtLongitudine },
                Note = new CampoLabeled { ControlloEdit = this.txtNote },
                Particella = new CampoLabeled { ControlloEdit = this.txtParticella },
                Piano = new CampoHidden { ControlloEdit = this.HiddenPiano },
                Scala = new CampoHidden { ControlloEdit = this.HiddenScala },
                Sezione = new CampoHidden { ControlloEdit = this.txtSezione },
                Sub = new CampoLabeled { ControlloEdit = this.txtSub },
                TipoCatasto = new CampoHidden { ControlloEdit = this.HiddenTipoCatasto }
            };

            base.OnInit(e);
        }

        private void ValidaForm()
        {
            //TODO: Cosa fare se il campo o il modello non sono configurati?

            if (!String.IsNullOrEmpty(this.ModelloDinamicoJsonDatiAggiuntivi))
            {
                var idModello = this._datiDinamiciService.GetIdModelloDaCodice(this.ModelloDinamicoJsonDatiAggiuntivi);
                if (!idModello.HasValue)
                {
                    this.Errori.Add($"Il modello dinamico {this.ModelloDinamicoJsonDatiAggiuntivi} non esiste");
                }
            }

            if (!String.IsNullOrEmpty(this.CampoDinamicoJsonDatiAggiuntivi))
            {
                var idCampo = this._datiDinamiciService.GetIdCampoDaNome(this.CampoDinamicoJsonDatiAggiuntivi);
                if (!idCampo.HasValue)
                {
                    this.Errori.Add($"Il campo dinamico {this.CampoDinamicoJsonDatiAggiuntivi} non esiste");
                }
            }
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
            return true;
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
            this.Master.MostraBottoneAvanti = this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Any();

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
            this.HiddenCodiceViario.Value = String.Empty;
            this.acIndirizzo.Value = String.Empty;
            this.acIndirizzo.Text = String.Empty;

            this._formLocalizzazioni.SvuotaCampiEdit();

            this.dgIndirizzi.Visible = false;
        }

        protected void Edit()
        {
            this.multiView.ActiveViewIndex = 1;
            this.Master.MostraPaginatoreSteps = false;
        }

        protected void GeneraUuid()
        {
            this.txtUuid.Text = Guid.NewGuid().ToString();
        }

        protected void EditNew()
        {
            this.ClearDettaglio();
            this.Edit();
            if (String.IsNullOrEmpty(this.txtUuid.Text))
            {
                this.GeneraUuid();
            }
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

                if (this.ddlComuneLocalizzazione.Items.Count > 1)
                {
                    nomeVia = this.ddlComuneLocalizzazione.SelectedItem.Text + " - " + nomeVia;
                }

                var localizzazione = this._formLocalizzazioni.GetLocalizzazione(Convert.ToInt32(stradarioTrovato.CodiceStradario), nomeVia, this.TipoLocalizzazione);
                localizzazione.Uuid = this.txtUuid.Text;

                // Se non sono stati configurati correttamente i parametri del modello dinamico, non si può procedere con il 
                // salvataggio del dato nella scheda. Valutare se sollevare un errore in fase di inizializzazione dello step
                RiferimentiCampoDinamico riferimentoCampoDinamico = null;

                if (!String.IsNullOrEmpty(this.CampoDinamicoJsonDatiAggiuntivi) && !String.IsNullOrEmpty(this.ModelloDinamicoJsonDatiAggiuntivi))
                {
                    riferimentoCampoDinamico = new RiferimentiCampoDinamico
                    {
                        IdCampo = this._datiDinamiciService.GetIdCampoDaNome(this.CampoDinamicoJsonDatiAggiuntivi).Value,
                        IdModello = this._datiDinamiciService.GetIdModelloDaCodice(this.ModelloDinamicoJsonDatiAggiuntivi).Value,
                        NomeCampo = this.CampoDinamicoJsonDatiAggiuntivi
                    };
                }


                if (!String.IsNullOrEmpty(this.HiddenIdLocalizzazione.Value))
                {
                    this._localizzazioniSicDomandaService.EliminaLocalizzazione(this.IdDomanda, Convert.ToInt32(this.HiddenIdLocalizzazione.Value), riferimentoCampoDinamico);
                }

                this._localizzazioniSicDomandaService.AggiungiLocalizzazione(this.IdDomanda, localizzazione, riferimentoCampoDinamico);

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

        public void cmdMostraMappa_Click(object sender, EventArgs e)
        {
            this.ValidaFormPerVisualizzazioneMappa();
            if (this.Errori.Count == 0)
            {
                this.RedirectAllaMappa();
            }
        }

        private void ValidaFormPerVisualizzazioneMappa()
        {
            if (String.IsNullOrEmpty(this.acIndirizzo.Value))
            {
                this.Errori.Add("Per visualizzare la mappa è necessario indicare almeno l' indirizzo");
            }
        }

        private void RedirectAllaMappa()
        {
            var host = this.Request.Url.Host;
            var scheme = this.Request.Url.Scheme;
            var port = this.Request.Url.Port;
            var appName = this.Request.ApplicationPath;
            var token = this.UserAuthenticationResult.Token;
            var idDomanda = this.ReadFacade.Domanda.AltriDati.IdentificativoDomanda;
            var idStep = this._qsStepId.Value;
            var uuidLocalizzazione = this.txtUuid.Text;

            var response = this._localizzazioniSicService.GeneraURLMappa(new GeneraURLMappaLocalizzazioneRequest
            {
                CallbackUrl = $"{scheme}://{host}:{port}{appName}/cartografico-return/{token}/{idDomanda}/{uuidLocalizzazione}/{idStep}",
                CancelUrl = "",
                CodiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune,
                CodiceStradario = Convert.ToInt32(this.acIndirizzo.Value),
                Km = this.txtKm.Text,
                UuidLocalizzazione = this.txtUuid.Text,
                RiferimentoPratica = this.ReadFacade.Domanda.AltriDati.IdentificativoDomanda
            });

            if (!String.IsNullOrEmpty(response.Errore))
            {
                this.Errori.Add(response.Errore);
                return;
            }

            this.Response.Redirect(response.Url);
        }

        private void RecuperaInfoAggiuntive()
        {
            var uuidLocalizzazione = this.txtUuid.Text;

            var response = this._localizzazioniSicService.RecuperaInformazioniAggiuntive(new RecuperaInformazioniAggiuntiveRequest
            {
                IdPratica = this.IdDomanda,
                RiferimentoPratica = this.ReadFacade.Domanda.AltriDati.IdentificativoDomanda,
                UuidLocalizzazione = uuidLocalizzazione
            });

            if (response.Exceptions.Count > 0)
            {
                this.Errori.AddRange(response.Exceptions);
                return;
            }

            // Rimosso dal metodo RecuperaInformazioniAggiuntive del service per separare le responsabilità
            this._localizzazioniSicDomandaService.SalvaInformazioniAggiuntive(this.IdDomanda, uuidLocalizzazione, response);

            this.acIndirizzo.Value = response.CodiceStradario.ToString();
            this.acIndirizzo.Text = response.Stradario;
            this.txtKm.Text = response.Km.Replace('+', ',');
            this.txtLatitudine.Text = response.Latitudine;
            this.txtLongitudine.Text = response.Longitudine;
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

            if (erroriValidazione.Any())
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

            var indirizzo = this.ReadFacade.Domanda.Localizzazioni.Indirizzi.FirstOrDefault(x => x.Id == key);

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
            this.HiddenCodiceViario.Value = i.CodiceViario;
            this.acIndirizzo.Value = i.CodiceStradario.ToString();
            this.acIndirizzo.Text = i.Indirizzo;

            this.txtKm.Value = i.Km;
            this.txtNote.Value = i.Note;
            this.txtLongitudine.Value = i.Longitudine;
            this.txtLatitudine.Value = i.Latitudine;
            this.txtUuid.Text = i.Uuid;

            /*
            //rilettura delle proprietà aggiuntive
            var infoAggiuntive = this._localizzazioniSicService.RecuperaInfoAggiuntive(this.IdDomanda, i.Uuid);
            this.txtDirezioneViabilita.Text = infoAggiuntive.DirezioneViabilita?.ToString();
            this.txtInCentroAbitato.Text = infoAggiuntive.InCentroAbitato?.ToString();
            this.txtUnitaOperativa.Text = infoAggiuntive.UnitaOperativa?.ToString();
            this.txtNomeCircolo.Text = infoAggiuntive.NomeCircolo;
            this.txtNumeroCircolo.Text = infoAggiuntive.NumeroCircolo?.ToString();
            this.txtTariffaCOSAP.Text = infoAggiuntive.TariffaCOSAP;
            this.txtTariffaDistrMezziPubb.Text = infoAggiuntive.TariffaDistributoriMezziPubblicitari;
            */
        }

        /// <summary>
        /// Handler dell'evento click sul bottone di eliminazione riga della datagrid di riepilogo
        /// </summary>
        public void dgStradario_DeleteCommand(object source, GridViewDeleteEventArgs e)
        {
            int key = Convert.ToInt32(this.dgStradario.DataKeys[e.RowIndex].Value);

            var riferimentoCampoDinamico = new RiferimentiCampoDinamico
            {
                IdCampo = this._datiDinamiciService.GetIdCampoDaNome(this.CampoDinamicoJsonDatiAggiuntivi).Value,
                IdModello = this._datiDinamiciService.GetIdModelloDaCodice(this.ModelloDinamicoJsonDatiAggiuntivi).Value,
                NomeCampo = this.CampoDinamicoJsonDatiAggiuntivi
            };

            this._localizzazioniSicDomandaService.EliminaLocalizzazione(this.IdDomanda, key, riferimentoCampoDinamico);

            this.DataBind();
        }
    }
}