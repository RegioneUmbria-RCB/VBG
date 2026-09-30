using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.Infrastructure.PropertiesResolver;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Exceptions;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{


    public partial class GestioneAnagraficheSemplificata : GestioneAnagraficheStepPage
    {

        [Inject]
        public CondizioniUscitaGestioneAnagraficheSemplificata _condizioneUscita { get; set; }

        [Inject]
        protected IConfigurazione<ParametriPresentazioneDomanda> _configurazione { get; set; }

        #region parametri letti dalla configurazione steps

        public string TestoInserimentoUtenteLoggato
        {
            get
            {
                return (this.ViewState["TestoInserimentoUtenteLoggato"] == null) ? null : this.ViewState["TestoInserimentoUtenteLoggato"].ToString();
            }
            set
            {
                this.ViewState["TestoInserimentoUtenteLoggato"] = value;
            }
        }

        public string TestoInserimentoAziendaIntermediario
        {
            get
            {
                return (this.ViewState["TestoInserimentoAziendaIntermediario"] == null) ? null : this.ViewState["TestoInserimentoAziendaIntermediario"].ToString();
            }
            set
            {
                this.ViewState["TestoInserimentoAziendaIntermediario"] = value;
            }
        }

        public string TestoInserimentoRichiedente
        {
            get
            {
                return (this.ViewState["TestoInserimentoRichiedente"] == null) ? null : this.ViewState["TestoInserimentoRichiedente"].ToString();
            }
            set
            {
                this.ViewState["TestoInserimentoRichiedente"] = value;
            }
        }

        public string TestoInserimentoAziendaRichiedente
        {
            get
            {
                return (this.ViewState["TestoInserimentoAziendaRichiedente"] == null) ? null : this.ViewState["TestoInserimentoAziendaRichiedente"].ToString();
            }
            set
            {
                this.ViewState["TestoInserimentoAziendaRichiedente"] = value;
            }
        }

        public string TestoTuttiISoggettiPresenti
        {
            get
            {
                return (this.ViewState["TestoTuttiISoggettiPresenti"] == null) ? null : this.ViewState["TestoTuttiISoggettiPresenti"].ToString();
            }
            set
            {
                this.ViewState["TestoTuttiISoggettiPresenti"] = value;
            }
        }

        public bool UtenteLoggatoPresente
        {
            get
            {
                return this.ReadFacade.Domanda.Anagrafiche.Anagrafiche.Where(x => x.Codicefiscale.ToUpper() == this.UserAuthenticationResult.DatiUtente.Codicefiscale.ToUpper()).Any();
            }
        }

        public int PFCampoEmail
        {
            set
            {
                this.DettagliPf.EmailVisible = value > 0;
                this.DettagliPf.EmailObbligatoria = value > 1;
            }
        }

        public int PFCampoPec
        {
            set
            {
                this.DettagliPf.PecVisible = value > 0;
                this.DettagliPf.PecObbligatoria = value > 1;
            }
        }

        public int PFCampoCorrispondenza
        {
            set
            {
                this.DettagliPf.CorrispondenzaVisibile = value > 0;
                this.DettagliPf.CorrispondenzaObbligatoria = value > 1;
            }
        }

        public int PGSedeLegale
        {
            set
            {
                this.DettagliPg.SedeLegaleVisibile = value > 0;
                this.DettagliPg.SedeLegaleObbligatoria = value > 1;
            }
        }

        public int PGDataCostituzione
        {
            set
            {
                this.DettagliPg.DataCostituzioneVisibile = value > 0;
                this.DettagliPg.DataCostituzioneObbligatoria = value > 1;
            }
        }

        public int PGTelefono
        {
            set
            {
                this.DettagliPg.TelefonoVisibile = value > 0;
                this.DettagliPg.TelefonoObbligatorio = value > 1;
            }
        }

        public int PGCellulare
        {
            set
            {
                this.DettagliPg.CellulareVisibile = value > 0;
                this.DettagliPg.CellulareObbligatorio = value > 1;
            }
        }

        public int PGCciaa
        {
            set
            {
                this.DettagliPg.CciaaVisibile = value > 0;
                this.DettagliPg.CciaaObbligatoria = value > 1;
            }
        }

        public int PGRegTrib
        {
            set
            {
                this.DettagliPg.RegTribVisibile = value > 0;
                this.DettagliPg.RegTribObbligatorio = value > 1;
            }
        }

        public int PGRea
        {
            set
            {
                this.DettagliPg.ReaVisibile = value > 0;
                this.DettagliPg.ReaObbligatoria = value > 1;
            }
        }

        public int PGInps
        {
            set
            {
                this.DettagliPg.InpsVisibile = value > 0;
                this.DettagliPg.InpsObbligatoria = value > 1;
            }
        }

        public int PGInail
        {
            set
            {
                this.DettagliPg.InailVisibile = value > 0;
                this.DettagliPg.InailObbligatoria = value > 1;
            }
        }

        public int PGEmail
        {
            set
            {
                this.DettagliPg.EmailVisibile = value > 0;
                this.DettagliPg.EmailObbligatoria = value > 1;
            }
        }

        public int PGPec
        {
            set
            {
                this.DettagliPg.PecVisibile = value > 0;
                this.DettagliPg.PecObbligatoria = value > 1;
            }
        }

        public int PGPartitaIva
        {
            set
            {
                this.DettagliPg.PartitaIvaVisibile = value > 0;
                this.DettagliPg.PartitaIvaObbligatoria = value > 1;
            }
        }

        public int PGCampoCorrispondenza
        {
            set
            {
                this.DettagliPg.CorrispondenzaVisibile = value > 0;
                this.DettagliPg.CorrispondenzaObbligatoria = value > 1;
            }
        }

        public string LimitaDatiAlbo
        {
            set
            {
                this.DettagliPf.LimitaDatiAlbo = value;
            }
        }

        public string PFTitoloBloccoIndirizzoCorrispondenza
        {
            get { return this.DettagliPf.TitoloBloccoIndirizzoCorrispondenza; }
            set { this.DettagliPf.TitoloBloccoIndirizzoCorrispondenza = value; }
        }

        public int PFCampoCittadinanza
        {
            set
            {
                this.DettagliPf.CittadinanzaVisible = value > 0;
                this.DettagliPf.CittadinanzaObbligatoria = value > 1;
            }
        }

        public int PFCampoTitolo
        {
            set
            {
                this.DettagliPf.TitoloVisibile = value > 0;
                this.DettagliPf.TitoloObbligatorio = value > 1;
            }
        }

        public bool GestioneSoggettoUnico
        {
            set
            {
                this.cmdNuovo.Visible = !value;
                this.DettagliPf.GestioneSoggettoUnico = !value;
            }
        }

        public int PFCampoResidenza
        {
            set
            {
                this.DettagliPf.ResidenzaVisible = value > 0;
                this.DettagliPf.ResidenzaObbligatoria = value > 1;
            }
        }

        public int PFCampoTelefono
        {
            set
            {
                this.DettagliPf.TelefonoVisible = value > 0;
                this.DettagliPf.TelefonoObbligatorio = value > 1;
            }
        }

        public int PFCampoCellulare
        {
            set
            {
                this.DettagliPf.CellulareVisible = value > 0;
                this.DettagliPf.CellulareObbligatorio = value > 1;
            }
        }
        #endregion

        protected override void OnInit(EventArgs e)
        {
            this.cmbTipoPersona.Inner.SelectedIndexChanged += (pippo, pluto) =>
            {
                this.BindTipoSoggetto();
            };
            base.OnInit(e);
        }

        private RuoloTipoSoggettoDomandaEnum? GetTipoSoggettoDaInserire()
        {
            List<AnagraficaDomanda> anagrafiche = new List<AnagraficaDomanda>();

            var soggettoLoggatoPresente = this.ReadFacade.Domanda.Anagrafiche.Anagrafiche.FirstOrDefault(x => x.Codicefiscale.Equals(this.UserAuthenticationResult.DatiUtente.Codicefiscale, StringComparison.InvariantCultureIgnoreCase));

            if (soggettoLoggatoPresente == null)
            {
                return RuoloTipoSoggettoDomandaEnum.Richiedente;
            }
            else if (soggettoLoggatoPresente.TipoSoggetto.RichiedeAnagraficaCollegata && !soggettoLoggatoPresente.IdAnagraficaCollegata.HasValue)
            {
                return (soggettoLoggatoPresente.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente) ? RuoloTipoSoggettoDomandaEnum.Azienda : RuoloTipoSoggettoDomandaEnum.Altro;
            }



            var richiedentePresente = this.ReadFacade.Domanda.Anagrafiche.Anagrafiche.Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente).Any();

            if (!richiedentePresente)
                return RuoloTipoSoggettoDomandaEnum.Richiedente;

            var aziendaRichiesta = this.ReadFacade.Domanda.Anagrafiche.Anagrafiche.Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente && x.TipoSoggetto.RichiedeAnagraficaCollegata).Any();

            if (!aziendaRichiesta)
                return null;

            var aziendaPresente = this.ReadFacade.Domanda.Anagrafiche.Anagrafiche.Where(x => x.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Azienda).Any();

            if (!aziendaPresente)
                return RuoloTipoSoggettoDomandaEnum.Azienda;

            return null;
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            this.TipoSoggetto.CodiceIntervento = this.CodiceIntervento;

            this._condizioneUscita.FlagVerificaPecObbligatoria = this.VerificaPecObbligatoria;
            this._condizioneUscita.MessaggioUtenteNonPresente = this.MessaggioUtenteNonPresente;

            this.InizializzaDettagliPFPG();

            if (!this.IsPostBack)
            {
                if (!this.UtenteLoggatoPresente)
                {
                    this.ImpostaDatiUtenteCorrente();
                }
                else
                {
                    this.DataBind();
                }

                this.cmbTipoPersona.Enabled = this.cmbTipoPersona.Visible = !this._configurazione.Parametri.RichiedenteSoloPersoneFisiche;

                this.BindTipoSoggetto();
            }

            this.SettaTestoSostitutivo();
        }

        protected void InizializzaDettagliPFPG()
        {
            this.DettagliPf.CancelEdit += new EventHandler(this.OnEndEdit);
            this.DettagliPf.AcceptEdit += new DettagliAnagraficaPf.OnAcceptEdit(this.OnAcceptEdit);
            this.DettagliPf.GetAnagrafeRow += new DettagliAnagraficaPf.OnGetAnagrafeRow(this.GetAnagrafeRow);
            this.DettagliPf.GetTipiSoggetto += new DettagliAnagraficaPf.OnGetTipiSoggettoPfDelegate(this.GetTipiSoggettoPf);
            this.DettagliPf.GetTipoSoggetto += new DettagliAnagraficaPf.OnGetTipoSoggetto(this.GetTipoSoggetto);
            this.DettagliPf.GetDatiComune += new DettagliAnagraficaPf.OnGetDatiComune(this.GetDatiComune);
            this.DettagliPf.GetDatiProvincia += new DettagliAnagraficaPf.OnGetDatiProvincia(this.GetDatiProvincia);
            this.DettagliPf.GetDatiCittadinanza += new DettagliAnagraficaPf.OnGetDatiCittadinanza(this.GetDatiCittadinanza);

            this.DettagliPg.CancelEdit += new EventHandler(this.OnEndEdit);
            this.DettagliPg.AcceptEdit += new DettagliAnagraficaPg.OnAcceptEdit(this.OnAcceptEdit);
            this.DettagliPg.GetAnagrafeRow += new DettagliAnagraficaPg.OnGetAnagrafeRow(this.GetAnagrafeRow);
            this.DettagliPg.GetDatiComune += new DettagliAnagraficaPg.OnGetDatiComune(this.GetDatiComune);
            this.DettagliPg.GetDatiProvincia += new DettagliAnagraficaPg.OnGetDatiProvincia(this.GetDatiProvincia);

            this.DettagliPf.ErroreInserimento += new ErrorDelegate(this.OnErroreInserimento);
            this.DettagliPg.ErroreInserimento += new ErrorDelegate(this.OnErroreInserimento);

            this.DettagliPf.CodiceIntervento = this.CodiceIntervento;
            this.DettagliPg.CodiceIntervento = this.CodiceIntervento;

            this.DettagliPf.EmailoPecObbligatori = this.EmailoPecObbligatori;
            this.DettagliPg.EmailoPecObbligatori = this.EmailoPecObbligatori;

            this.DettagliPf.TelefonooCellulareObbligatori = this.TelefonooCellulareObbligatori;
            this.DettagliPg.TelefonooCellulareObbligatori = this.TelefonooCellulareObbligatori;
        }

        #region ciclo di vita dello step

        public override void OnInitializeStep()
        {
            this._anagraficheDomandaService.SincronizzaFlagsTipiSoggetto(this.IdDomanda);
            this._anagraficheDomandaService.VerificaFlagsCittadiniExtracomunitari(this.IdDomanda);
        }

        public override bool CanEnterStep()
        {
            return this._condizioneIngresso.Verificata();
        }

        public override bool CanExitStep()
        {
            try
            {
                this._condizioneUscita.VerificaPresenzaUtenteLoggato = this.UtenteLoggatoPresente;

                return this._condizioneUscita.Verificata();
            }
            catch (StepException ex)
            {
                this.Errori.AddRange(ex.ErrorMessages);
            }

            return false;
        }

        #endregion


        private void OnAcceptEdit(AnagraficaDomanda row)
        {
            try
            {
                var idAnagrafica = this._anagraficheDomandaService.SalvaAnagrafica(this.IdDomanda, row);

                if (row.TipoPersona == TipoPersonaEnum.Giuridica)
                {
                    this.CercaECollegaPersonaFisica(idAnagrafica);
                }

                this.InizializzaRicercaUtenti();

                if (!this.GetTipoSoggettoDaInserire().HasValue)
                {
                    this.OnEndEdit(this, EventArgs.Empty);
                }

                this.SettaTestoSostitutivo();
            }
            catch (Exception ex)
            {
                this.m_logger.ErrorFormat("Errore in OnAcceptEdit: {0}", ex.ToString());

                this.Errori.Add(ex.Message);
            }
        }

        private void CercaECollegaPersonaFisica(int idAnagrafica)
        {
            var anagraficaCorrente = this._anagraficheDomandaService.GetById(this.IdDomanda, idAnagrafica);
            var anagraficaCollegabile = (anagraficaCorrente.TipoSoggetto.Ruolo == RuoloTipoSoggettoDomandaEnum.Altro) ? this._anagraficheDomandaService.GetTecnico(this.IdDomanda) : this._anagraficheDomandaService.GetRichiedente(this.IdDomanda);

            if (anagraficaCorrente.Id != anagraficaCollegabile.Id)
            {
                this._anagraficheDomandaService.CollegaAziendaAdAnagrafica(this.IdDomanda, anagraficaCollegabile.Id.Value, anagraficaCorrente.Id.Value);
            }
        }

        private void BindTipoSoggetto()
        {
            var tsdi = this.GetTipoSoggettoDaInserire();

            if (tsdi.HasValue)
                this.BindTipoSoggetto(tsdi.Value);
        }

        private void BindTipoSoggetto(RuoloTipoSoggettoDomandaEnum ruolo)
        {
            this.TipoSoggetto.TipoSoggetto = this.cmbTipoPersona.SelectedValue;
            this.TipoSoggetto.RuoloTipoSoggetto = ruolo;
            this.TipoSoggetto.DataBind();
        }

        private void OnErroreInserimento(string message)
        {
            this.Errori.Add(message);
        }

        public void OnEndEdit(object sender, EventArgs e)
        {
            this.multiview.ActiveViewIndex = PageViews.Lista;
            this.Master.MostraPaginatoreSteps = true;

            this.DataBind();

            this.SettaTestoSostitutivo();

        }

        private void InizializzaBottoneAggiungiSoggetto()
        {
            if (this.GetTipoSoggettoDaInserire() != null)
            {
                this.cmdNuovo.Visible = true;
                this.SettaTestoBottoneAggiungiSoggetto();
            }
            else
            {
                this.cmdNuovo.Visible = false;
            }
        }

        private void SettaTestoSostitutivo()
        {
            this.Master.MostraBottoneAvanti = false;

            if (!this.UtenteLoggatoPresente)
            {
                this.ltrTestoSostitutivo.Text = this.TestoInserimentoUtenteLoggato;
            }
            else
            {
                var t = this.GetTipoSoggettoDaInserire();

                if (t.HasValue)
                {
                    if (t.Value == RuoloTipoSoggettoDomandaEnum.Azienda)
                    {
                        this.ltrTestoSostitutivo.Text = this.TestoInserimentoAziendaRichiedente;
                    }
                    else if (t.Value == RuoloTipoSoggettoDomandaEnum.Richiedente)
                    {
                        this.ltrTestoSostitutivo.Text = this.TestoInserimentoRichiedente;
                    }
                    else if (t.Value == RuoloTipoSoggettoDomandaEnum.Altro)
                    {
                        this.ltrTestoSostitutivo.Text = this.TestoInserimentoAziendaIntermediario;
                    }
                }
                else
                {
                    this.ltrTestoSostitutivo.Text = this.TestoTuttiISoggettiPresenti;
                    this.Master.MostraBottoneAvanti = true;
                }
            }

            this.ltrTestoSostitutivo.Text = new SostituisciStringaResolver(this.ltrTestoSostitutivo.Text).Risolvi(this);
        }

        private void SettaTestoBottoneAggiungiSoggetto()
        {

            if (!this.UtenteLoggatoPresente)
            {
                this.cmdNuovo.Text = Constants.TestoAggiungiUtenteLoggato;
            }
            else
            {
                var t = this.GetTipoSoggettoDaInserire();

                if (t.HasValue)
                {
                    if (t.Value == RuoloTipoSoggettoDomandaEnum.Azienda)
                    {
                        this.cmdNuovo.Text = Constants.TestoAggiungiAzienda;
                    }
                    else if (t.Value == RuoloTipoSoggettoDomandaEnum.Richiedente)
                    {
                        this.cmdNuovo.Text = Constants.TestoAggiungiRichiedente;
                    }
                    else if (t.Value == RuoloTipoSoggettoDomandaEnum.Altro)
                    {
                        this.cmdNuovo.Text = Constants.TestoAggiungiAziendaUtenteLoggato;
                    }
                }
            }
        }

        public void cmdNuovo_Click(object sender, EventArgs e)
        {

            this.Master.MostraPaginatoreSteps = false;

            this.InizializzaRicercaUtenti();

            this.Master.MostraBottoneAvanti = false;
        }

        public void cmdCercaCf_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(this.txtCodiceFiscale.Text))
            {
                if (this.cmbTipoPersona.SelectedValue == "F")
                    this.Errori.Add("Inserire un codice fiscale");
                else
                    this.Errori.Add("Inserire un codice fiscale o una partita iva");
                return;
            }

            this.txtCodiceFiscale.Text = this.txtCodiceFiscale.Text.ToUpper();

            if (!Regex.IsMatch(this.txtCodiceFiscale.Text, "^[a-zA-Z0-9]+$"))
            {
                var messaggioErrore = new StringBuilder();

                messaggioErrore.Append(this.cmbTipoPersona.SelectedValue == "F" ? "Il codice fiscale immesso" : "La partita iva immessa");
                messaggioErrore.Append(" contiene caratteri non validi. Verificare i dati immessi.");

                this.Errori.Add(messaggioErrore.ToString());

                return;
            }

            try
            {
                var codiceFiscale = this.txtCodiceFiscale.Text;
                var tipoPersona = this.cmbTipoPersona.SelectedValue;
                var tipoPersonaEnum = tipoPersona == "F" ? TipoPersonaEnum.Fisica : TipoPersonaEnum.Giuridica;

                var anagrafe = this._ricercheService.RicercaAnagrafica(this.IdDomanda, tipoPersonaEnum, codiceFiscale, this.IgnoraRicercaBackofficePerPersoneFisiche);

                anagrafe.TipoSoggetto = this.GetTipoSoggetto(Convert.ToInt32(this.TipoSoggetto.SelectedValue)).ToTipoSoggettoDomanda();

                this.Edit(anagrafe);
            }
            catch (Exception ex) // Errore di comunicazione con il web service... Come lo gestiamo?
            {
                this.Errori.Add("Si è verificato un errore durante la ricerca dell'anagrafica");
                LogManager.GetLogger(this.GetType()).Error(ex.ToString());
                this.m_logger.ErrorFormat(ex.ToString());
            }
        }

        private void ImpostaDatiUtenteCorrente()
        {
            var newRow = new AnagrafeAdapter(this.UserAuthenticationResult.DatiUtente.ToWsAnagrafe(), this._comuniService).ToAnagrafeRow();

            this.Edit(AnagraficaDomanda.FromAnagrafeRow(newRow));
        }

        private void InizializzaRicercaUtenti()
        {
            if (!this.UtenteLoggatoPresente)
            {
                this.ImpostaDatiUtenteCorrente();
            }
            else
            {
                var tsdi = this.GetTipoSoggettoDaInserire();

                if (tsdi.HasValue)
                {
                    var tsd = new TipoSoggettoDomanda();
                    tsd.Ruolo = tsdi.Value;


                    this.cmbTipoPersona.SelectedValue = (tsd.Ruolo == RuoloTipoSoggettoDomandaEnum.Richiedente || tsd.Ruolo == RuoloTipoSoggettoDomandaEnum.Tecnico) ? "F" : "G";
                    this.BindTipoSoggetto(tsd.Ruolo);

                    if (this.TipoSoggetto.Items.Count == 1 && this.cmbTipoPersona.SelectedValue == "F" && !this._configurazione.Parametri.RichiedenteSoloPersoneFisiche)
                    {
                        this.cmbTipoPersona.SelectedValue = "G";
                        this.BindTipoSoggetto(tsd.Ruolo);
                    }

                    if (this.TipoSoggetto.Items.Count == 2)
                    {
                        this.TipoSoggetto.SelectedIndex = 1;
                    }

                    this.multiview.ActiveViewIndex = PageViews.NuovaAnagrafica;
                }
            }

        }

        #region gestione della modifica dati

        protected void Edit(AnagraficaDomanda row)
        {
            var nuovaAnagrafica = new NuovaAnagraficaSpecification().IsSatisfiedBy(row);

            var permettiModificheAdAnagrafiche = nuovaAnagrafica || (!nuovaAnagrafica && this.RendiModificabiliDatiAnagraficheEsistenti);

            if (row.TipoPersona == TipoPersonaEnum.Fisica) // PersonaFisica
            {
                this.multiview.ActiveViewIndex = PageViews.EditPersonaFisica;

                if (this.VerificaPecObbligatoria)
                    this.DettagliPf.MessaggioVerificaPec = this.MessaggioAvvertimentoVerificaPEC;

                this.DettagliPf.PermettiModificaDatiAnagrafici = permettiModificheAdAnagrafiche;
                this.DettagliPf.PermettiModificaTipoSoggetto = (String.IsNullOrEmpty(this.TipoSoggetto.SelectedValue) && row.TipoSoggetto.Id == -1);
                this.DettagliPf.DataSource = row.ToAnagrafeRow();
                this.DettagliPf.DataBind();

            }
            else
            {
                this.multiview.ActiveViewIndex = PageViews.EditPersonaGiuridica;

                this.DettagliPg.PermettiModificaDatiAnagrafici = permettiModificheAdAnagrafiche;
                this.DettagliPg.PermettiModificaTipoSoggetto = (String.IsNullOrEmpty(this.TipoSoggetto.SelectedValue) && row.TipoSoggetto.Id == -1);
                this.DettagliPg.DataSource = row.ToAnagrafeRow();
                this.DettagliPg.DataBind();
            }

            this.Master.MostraPaginatoreSteps = false;
        }

        #endregion

        #region gestione della griglia

        public class RichiedentiBindingItem
        {
            public int Id { get; set; }
            public string Nominativo { get; set; }
            public string InQualitaDi { get; set; }
            public string AziendaCollegata { get; set; }
            public string TestoLinkCollegaAzienda { get; set; }
            public bool MostraLinkCollegaAzienda { get; set; }
            public IEnumerable<KeyValuePair<int, string>> AziendeCollegabili { get; set; }
        }

        public override void DataBind()
        {
            var aziendeCollegabili = this.ReadFacade.Domanda
                                               .Anagrafiche
                                               .GetAnagraficheCollegabili()
                                               .Select(x => new KeyValuePair<int, string>(x.Id.Value, x.ToString()))
                                               .ToList();

            if (aziendeCollegabili.Count == 0)
                aziendeCollegabili.Add(new KeyValuePair<int, string>(-1, "Tra i soggetti dell'istanza non sono presenti aziende"));

            this.dgRichiedenti.DataSource = this.ReadFacade.Domanda
                                                 .Anagrafiche
                                                 .Anagrafiche
                                                 .OrderBy(x => x.TipoPersona)
                                                 .ThenBy(x => x.Nominativo)
                                                 .Select(x => new RichiedentiBindingItem
                                                 {
                                                     Id = x.Id.Value,
                                                     Nominativo = x.ToString(),
                                                     InQualitaDi = x.TipoSoggetto.ToString(),
                                                     AziendaCollegata = x.AnagraficaCollegata != null ? x.AnagraficaCollegata.ToString() : String.Empty,
                                                     MostraLinkCollegaAzienda = x.TipoSoggetto.RichiedeAnagraficaCollegata,
                                                     TestoLinkCollegaAzienda = x.IdAnagraficaCollegata.HasValue ? "Modifica collegamento" : "Collega azienda",
                                                     AziendeCollegabili = aziendeCollegabili
                                                 });
            this.dgRichiedenti.DataBind();

            this.InizializzaBottoneAggiungiSoggetto();

            this.BindTipiSoggetto();


            //this.Master.MostraBottoneAvanti = true;
        }

        protected void dgRichiedenti_CancelCommand(object source, GridViewCancelEditEventArgs e)
        {
            this.Master.MostraPaginatoreSteps = true;
            this.dgRichiedenti.EditIndex = -1;
            this.DataBind();
        }

        protected void dgRichiedenti_UpdateCommand(object source, GridViewUpdateEventArgs e)
        {
            var ddl = (DropDownList)this.dgRichiedenti.Rows[e.RowIndex].FindControl("ddlAziendeCollegabili");
            var idAziendaColl = Convert.ToInt32(ddl.SelectedValue);

            var key = Convert.ToInt32(this.dgRichiedenti.DataKeys[e.RowIndex].Value);

            this._anagraficheDomandaService.CollegaAziendaAdAnagrafica(this.IdDomanda, key, idAziendaColl);

            this.Master.MostraPaginatoreSteps = true;
            this.dgRichiedenti.EditIndex = -1;
            this.DataBind();

            this.SettaTestoSostitutivo();
        }

        public void dgRichiedenti_DeleteCommand(object source, GridViewDeleteEventArgs e)
        {
            int id = Convert.ToInt32(this.dgRichiedenti.DataKeys[e.RowIndex].Value);

            var anagrafica = this._anagraficheDomandaService.GetById(this.IdDomanda, id);

            if (anagrafica.IdAnagraficaCollegata.HasValue)
            {
                this._anagraficheDomandaService.RimuoviAnagrafica(this.IdDomanda, anagrafica.IdAnagraficaCollegata.Value);
            }

            this._anagraficheDomandaService.RimuoviAnagrafica(this.IdDomanda, id);

            this.DataBind();

            this.SettaTestoSostitutivo();
        }

        protected void dgRichiedenti_EditCommand(object source, GridViewEditEventArgs e)
        {
            this.Master.IgnoraSalvataggioDati = true;
            this.Master.MostraPaginatoreSteps = false;

            this.dgRichiedenti.EditIndex = e.NewEditIndex;
            this.DataBind();
        }

        public void dgRichiedenti_SelectedIndexChanged(object sender, EventArgs e)
        {
            int pk = Convert.ToInt32(this.dgRichiedenti.DataKeys[this.dgRichiedenti.SelectedIndex].Value);

            this.Edit(this.ReadFacade.Domanda.Anagrafiche.GetById(pk));
        }


        #endregion

        private void BindTipiSoggetto()
        {
            this.TipoSoggetto.DataBind();
        }

        public void multiview_ActiveViewChanged(object sender, EventArgs e)
        {
        }
    }
}