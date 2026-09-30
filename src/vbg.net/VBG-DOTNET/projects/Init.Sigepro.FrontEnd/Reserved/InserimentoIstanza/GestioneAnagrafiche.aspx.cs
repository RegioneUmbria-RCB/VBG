using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
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
    public partial class DatiAnagrafici : GestioneAnagraficheStepPage
    {
        [Inject]
        public CondizioniUscitaGestioneAnagrafiche _condizioneUscita { get; set; }

        public string ComboRicercaTestoElementoSocieta
        {
            get { object o = this.ViewState["ComboRicercaTestoElementoSocieta"]; return o == null ? "Società" : (string)o; }
            set { this.ViewState["ComboRicercaTestoElementoSocieta"] = value; }
        }


        public bool VerificaPresenzaUtenteLoggato
        {
            get { object o = this.ViewState["VerificaPresenzaUtenteLoggato"]; return o == null ? true : (bool)o; }
            set { this.ViewState["VerificaPresenzaUtenteLoggato"] = value; }
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
            get { object o = this.ViewState["GestioneSoggettoUnico"]; return o == null ? false : (bool)o; }
            set { this.ViewState["GestioneSoggettoUnico"] = value; }
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

        public bool EmailoPecObbligatori
        {
            get { object o = this.ViewState["EmailoPecObbligatori"]; return o == null ? false : (bool)o; }
            set { this.ViewState["EmailoPecObbligatori"] = value; }
        }

        public bool TelefonooCellulareObbligatori
        {
            get { object o = this.ViewState["TelefonooCellulareObbligatori"]; return o == null ? false : (bool)o; }
            set { this.ViewState["TelefonooCellulareObbligatori"] = value; }
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


        protected override void OnPreRender(EventArgs e)
        {
            this.cmdNuovo.Visible = true;
            this.DettagliPf.GestioneSoggettoUnico = true;

            if (this.GestioneSoggettoUnico && this.ReadFacade.Domanda.Anagrafiche.Anagrafiche.Any())
            {
                this.cmdNuovo.Visible = false;
                this.DettagliPf.GestioneSoggettoUnico = false;
            }

            base.OnPreRender(e);
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            this._condizioneUscita.FlagVerificaPecObbligatoria = this.VerificaPecObbligatoria;
            this._condizioneUscita.MessaggioUtenteNonPresente = this.MessaggioUtenteNonPresente;

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

            if (!this.IsPostBack)
            {
                if (this.VerificaRichiedenteAutomatico())
                {
                    this.ImpostaDatiUtenteCorrente();
                }
                else
                {
                    this.DataBind();
                }

                this.cmbTipoPersona.Items[1].Text = this.ComboRicercaTestoElementoSocieta;

            }
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
                this._condizioneUscita.VerificaPresenzaUtenteLoggato = this.VerificaPresenzaUtenteLoggato;

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
                this._anagraficheDomandaService.SalvaAnagrafica(this.IdDomanda, row);

                this.OnEndEdit(this, EventArgs.Empty);
            }
            catch (Exception ex)
            {
                this.m_logger.ErrorFormat("Errore in OnAcceptEdit: {0}", ex.ToString());

                this.Errori.Add(ex.Message);
            }
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
        }

        public void cmdNuovo_Click(object sender, EventArgs e)
        {
            this.multiview.ActiveViewIndex = PageViews.NuovaAnagrafica;
            this.Master.MostraPaginatoreSteps = false;
            this.cmbTipoPersona.SelectedValue = "F";
            this.txtCodiceFiscale.Text = "";

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
                this.DettagliPf.DataSource = row.ToAnagrafeRow();
                this.DettagliPf.DataBind();

            }
            else
            {
                this.multiview.ActiveViewIndex = PageViews.EditPersonaGiuridica;

                this.DettagliPg.PermettiModificaDatiAnagrafici = permettiModificheAdAnagrafiche;
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

            this.Master.MostraBottoneAvanti = true;
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
        }

        public void dgRichiedenti_DeleteCommand(object source, GridViewDeleteEventArgs e)
        {
            int id = Convert.ToInt32(this.dgRichiedenti.DataKeys[e.RowIndex].Value);

            this._anagraficheDomandaService.RimuoviAnagrafica(this.IdDomanda, id);

            this.DataBind();
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


        public void multiview_ActiveViewChanged(object sender, EventArgs e)
        {
        }
    }
}
