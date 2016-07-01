using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniIngressoSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Exceptions;
using log4net;
using Ninject;
using System.Text.RegularExpressions;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class DatiAnagrafici : IstanzeStepPage
    {
        public enum FlagVisualizzazioneCampo
        {
            Nascondi = 0,
            Mostra = 1,
            Obbligatorio = 2
        }

        public static class PageViews
        {
            public const int Lista = 0;
            public const int NuovaAnagrafica = 1;
            public const int EditPersonaFisica = 2;
            public const int EditPersonaGiuridica = 3;
        }

        public class NuovaAnagraficaSpecification : ISpecification<AnagraficaDomanda>
        {
            public bool IsSatisfiedBy(AnagraficaDomanda item)
            {
                return item.Nominativo == null || String.IsNullOrEmpty(item.Nominativo.Trim());
            }
        }


        const string MSG_ERRORE_UTENTE_NON_PRESENTE = "L'utente con cui si è effettuato l'accesso (Nominativo: {0}, Codice fiscale: {1}) non è presente nella lista dei soggetti coinvolti nella domanda";

        [Inject]
        public CondizioneIngressoStepSempreVera _condizioneIngresso { get; set; }
        [Inject]
        public CondizioniUscitaGestioneAnagrafiche _condizioneUscita { get; set; }
        [Inject]
        public IAnagraficheService AnagraficheService { get; set; }
        [Inject]
        public IConfigurazione<ParametriWorkflow> ConfigurazioneWorkflow { get; set; }


        ILog m_logger = LogManager.GetLogger(typeof(DatiAnagrafici));


        public delegate void ErrorDelegate(string message);

        #region parametri letti dalla configurazione steps
        public bool VerificaPecObbligatoria
        {
            get { object o = this.ViewState["VerificaPecObbligatoria"]; return o == null ? false : (bool)o; }
            set { this.ViewState["VerificaPecObbligatoria"] = value; }
        }

        public string MessaggioAvvertimentoVerificaPEC
        {
            get { object o = this.ViewState["MessaggioAvvertimentoVerificaPEC"]; return o == null ? String.Empty : o.ToString(); }
            set { this.ViewState["MessaggioAvvertimentoVerificaPEC"] = value; }
        }

        public string MessaggioUtenteNonPresente
        {
            get { object o = this.ViewState["MessaggioUtenteNonPresente"]; return o == null ? MSG_ERRORE_UTENTE_NON_PRESENTE : (string)o; }
            set { this.ViewState["MessaggioUtenteNonPresente"] = value; }
        }

        public bool RendiModificabiliDatiAnagraficheEsistenti
        {
            get { object o = this.ViewState["RendiModificabiliDatiAnagraficheEsistenti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["RendiModificabiliDatiAnagraficheEsistenti"] = value; }
        }

        public bool IgnoraRicercaBackofficePerPersoneFisiche
        {
            get { object o = this.ViewState["IgnoraRicercaBackofficePerPersoneFisiche"]; return o == null ? false : (bool)o; }
            set { this.ViewState["IgnoraRicercaBackofficePerPersoneFisiche"] = value; }
        }

        public bool VerificaPresenzaUtenteLoggato
        {
            get { object o = this.ViewState["VerificaPresenzaUtenteLoggato"]; return o == null ? true : (bool)o; }
            set { this.ViewState["VerificaPresenzaUtenteLoggato"] = value; }
        }

        public int PFCampoCittadinanza
        {
            set
            {
                this.DettagliPf.CittadinanzaVisible = value > 0;
                this.DettagliPf.CittadinanzaObbligatoria = value > 1;
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

        public int PFCampoFax
        {
            set
            {
                this.DettagliPf.FaxVisible = value > 0;
                this.DettagliPf.FaxObbligatorio = value > 1;
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

        /*
            Controllo dei campi delle persone giuridiche
         */
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

        public int PGFax
        {
            set
            {
                this.DettagliPg.FaxVisibile = value > 0;
                this.DettagliPg.FaxObbligatorio = value > 1;
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
                this.DettagliPg.PecObbligatoria = value > 1;
            }
        }

        #endregion

        private int? _codiceIntervento = null;
        protected int? CodiceIntervento
        {
            get
            {
                if (this._codiceIntervento == null)
                {
                    this._codiceIntervento = ReadFacade.Domanda.AltriDati.Intervento == null ? (int?)null : ReadFacade.Domanda.AltriDati.Intervento.Codice;
                }

                return this._codiceIntervento;
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            _condizioneUscita.FlagVerificaPecObbligatoria = VerificaPecObbligatoria;
            _condizioneUscita.MessaggioUtenteNonPresente = MessaggioUtenteNonPresente;

            DettagliPf.CancelEdit += new EventHandler(OnEndEdit);
            DettagliPf.AcceptEdit += new DettagliAnagraficaPf.OnAcceptEdit(OnAcceptEdit);
            DettagliPf.GetAnagrafeRow += new DettagliAnagraficaPf.OnGetAnagrafeRow(GetAnagrafeRow);
            DettagliPf.GetTipiSoggetto += new DettagliAnagraficaPf.OnGetTipiSoggettoPfDelegate(GetTipiSoggettoPf);
            DettagliPf.GetTipoSoggetto += new DettagliAnagraficaPf.OnGetTipoSoggetto(GetTipoSoggetto);
            DettagliPf.GetDatiComune += new DettagliAnagraficaPf.OnGetDatiComune(GetDatiComune);
            DettagliPf.GetDatiProvincia += new DettagliAnagraficaPf.OnGetDatiProvincia(GetDatiProvincia);
            DettagliPf.GetDatiCittadinanza += new DettagliAnagraficaPf.OnGetDatiCittadinanza(GetDatiCittadinanza);

            DettagliPg.CancelEdit += new EventHandler(OnEndEdit);
            DettagliPg.AcceptEdit += new DettagliAnagraficaPg.OnAcceptEdit(OnAcceptEdit);
            DettagliPg.GetAnagrafeRow += new DettagliAnagraficaPg.OnGetAnagrafeRow(GetAnagrafeRow);
            DettagliPg.GetDatiComune += new DettagliAnagraficaPg.OnGetDatiComune(GetDatiComune);
            DettagliPg.GetDatiProvincia += new DettagliAnagraficaPg.OnGetDatiProvincia(GetDatiProvincia);

            DettagliPf.ErroreInserimento += new ErrorDelegate(OnErroreInserimento);
            DettagliPg.ErroreInserimento += new ErrorDelegate(OnErroreInserimento);

            DettagliPf.CodiceIntervento = this.CodiceIntervento;
            DettagliPg.CodiceIntervento = this.CodiceIntervento;

            if (!IsPostBack)
            {
                if (VerificaRichiedenteAutomatico())
                    ImpostaDatiUtenteCorrente();
                else
                    DataBind();
            }
        }





        #region ciclo di vita dello step

        public override void OnInitializeStep()
        {
            AnagraficheService.SincronizzaFlagsTipiSoggetto(this.IdDomanda);
            AnagraficheService.VerificaFlagsCittadiniExtracomunitari(this.IdDomanda);
        }

        public override bool CanEnterStep()
        {
            return _condizioneIngresso.Verificata();
        }

        public override bool CanExitStep()
        {
            try
            {
                this._condizioneUscita.VerificaPresenzaUtenteLoggato = this.VerificaPresenzaUtenteLoggato;

                return _condizioneUscita.Verificata();
            }
            catch (StepException ex)
            {
                Errori.AddRange(ex.ErrorMessages);
            }

            return false;
        }

        #endregion

        DatiProvinciaCompatto GetDatiProvincia(string siglaProvincia)
        {
            return ReadFacade.Comuni.GetDatiProvincia(siglaProvincia);
        }

        Cittadinanza GetDatiCittadinanza(string strIdCittadinanza)
        {
            if (String.IsNullOrEmpty(strIdCittadinanza))
            {
                return null;
            }

            return ReadFacade.Cittadinanze.GetCittadinanzaDaId(Convert.ToInt32(strIdCittadinanza));
        }

        DatiComuneCompatto GetDatiComune(string codiceComune)
        {
            if (String.IsNullOrEmpty(codiceComune))
            {
                return null;
            }

            return ReadFacade.Comuni.GetDatiComune(codiceComune);
        }

        TipoSoggetto GetTipoSoggetto(int idTipoSoggetto)
        {
            return ReadFacade.TipiSoggetto.GetById(idTipoSoggetto);
        }

        AnagraficaDomanda GetAnagrafeRow(int idAnagrafica)
        {
            var anagrafica = ReadFacade.Domanda.Anagrafiche.GetById(idAnagrafica);

            if (anagrafica != null)
                return anagrafica;

            return AnagraficaDomanda.New(idAnagrafica);
        }

        IEnumerable<TipoSoggetto> GetTipiSoggettoPf()
        {
            return ReadFacade.TipiSoggetto.GetTipiSoggettoPersonaFisica(this.CodiceIntervento);
        }

        void OnAcceptEdit(AnagraficaDomanda row)
        {
            try
            {
                AnagraficheService.SalvaAnagrafica(IdDomanda, row);

                OnEndEdit(this, EventArgs.Empty);
            }
            catch (Exception ex)
            {
                m_logger.ErrorFormat("Errore in OnAcceptEdit: {0}", ex.ToString());

                Errori.Add(ex.Message);
            }
        }

        void OnErroreInserimento(string message)
        {
            Errori.Add(message);
        }

        public void OnEndEdit(object sender, EventArgs e)
        {
            multiview.ActiveViewIndex = PageViews.Lista;

            DataBind();
        }

        public void cmdNuovo_Click(object sender, EventArgs e)
        {
            multiview.ActiveViewIndex = PageViews.NuovaAnagrafica;
            cmbTipoPersona.SelectedValue = "F";
            txtCodiceFiscale.Text = "";

            this.Master.MostraBottoneAvanti = false;
        }

        public void cmdCercaCf_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(txtCodiceFiscale.Text))
            {
                if (cmbTipoPersona.SelectedValue == "F")
                    Errori.Add("Inserire un codice fiscale");
                else
                    Errori.Add("Inserire un codice fiscale o una partita iva");
                return;
            }

            if (!Regex.IsMatch(txtCodiceFiscale.Text, "^[a-zA-Z0-9]+$"))
            {
                var messaggioErrore = new StringBuilder();

                messaggioErrore.Append(cmbTipoPersona.SelectedValue == "F" ? "Il codice fiscale immesso" : "La partita iva immessa");
                messaggioErrore.Append(" contiene caratteri non validi. Verificare i dati immessi.");

                Errori.Add(messaggioErrore.ToString());

                return;
            }



            /*
            if (cmbTipoPersona.SelectedValue == "F")
            {
                if (txtCodiceFiscale.Text.Length == 0)
                {
                    Errori.Add("Codice fiscale non valido. Verificare i dati immessi e riprovare");
                    return;
                }
            }

            if (cmbTipoPersona.SelectedValue != "F")
            {
                if (txtCodiceFiscale.Text.Length != 16 && txtCodiceFiscale.Text.Length != 11)
                {
                    Errori.Add("Codice fiscale o partita iva non valido. Verificare i dati immessi e riprovare");
                    return;
                }
            }
            */
            try
            {
                var codiceFiscale = txtCodiceFiscale.Text;
                var tipoPersona = cmbTipoPersona.SelectedValue;
                var tipoPersonaEnum = tipoPersona == "F" ? TipoPersonaEnum.Fisica : TipoPersonaEnum.Giuridica;

                AnagraficheService.IgnoraRicercaBackofficePerPersoneFisiche = this.IgnoraRicercaBackofficePerPersoneFisiche;

                var anagrafe = AnagraficheService.RicercaAnagrafica(IdDomanda, tipoPersonaEnum, codiceFiscale);

                Edit(anagrafe);
            }
            catch (Exception ex) // Errore di comunicazione con il web service... Come lo gestiamo?
            {
                Errori.Add("Si è verificato un errore durante la ricerca dell'anagrafica");
                LogManager.GetLogger(this.GetType()).Error(ex.ToString());
                m_logger.ErrorFormat(ex.ToString());
            }
        }

        private void ImpostaDatiUtenteCorrente()
        {
            var newRow = new AnagrafeAdapter(UserAuthenticationResult.DatiUtente.ToWsAnagrafe()).ToAnagrafeRow();

            Edit(AnagraficaDomanda.FromAnagrafeRow(newRow));
        }

        private bool VerificaRichiedenteAutomatico()
        {
            return ConfigurazioneWorkflow.Parametri.ImpostaAutomaticamenteAnagraficaUtenteCorrente && ReadFacade.Domanda.Anagrafiche.Anagrafiche.Count() == 0;
        }

        #region gestione della modifica dati

        protected void Edit(AnagraficaDomanda row)
        {
            var nuovaAnagrafica = new NuovaAnagraficaSpecification().IsSatisfiedBy(row);

            var permettiModificheAdAnagrafiche = nuovaAnagrafica || (!nuovaAnagrafica && RendiModificabiliDatiAnagraficheEsistenti);

            if (row.TipoPersona == TipoPersonaEnum.Fisica) // PersonaFisica
            {
                multiview.ActiveViewIndex = PageViews.EditPersonaFisica;

                if (VerificaPecObbligatoria)
                    DettagliPf.MessaggioVerificaPec = MessaggioAvvertimentoVerificaPEC;

                DettagliPf.Token = UserAuthenticationResult.Token;
                DettagliPf.PermettiModificaDatiAnagrafici = permettiModificheAdAnagrafiche;
                DettagliPf.DataSource = row.ToAnagrafeRow();
                DettagliPf.DataBind();

            }
            else
            {
                multiview.ActiveViewIndex = PageViews.EditPersonaGiuridica;

                DettagliPg.Token = UserAuthenticationResult.Token;
                DettagliPg.PermettiModificaDatiAnagrafici = permettiModificheAdAnagrafiche;
                DettagliPg.DataSource = row.ToAnagrafeRow();
                DettagliPg.DataBind();
            }

            Master.MostraBottoneAvanti = false;
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
            var aziendeCollegabili = ReadFacade.Domanda
                                               .Anagrafiche
                                               .GetAnagraficheCollegabili()
                                               .Select(x => new KeyValuePair<int, string>(x.Id.Value, x.ToString()))
                                               .ToList();

            if (aziendeCollegabili.Count == 0)
                aziendeCollegabili.Add(new KeyValuePair<int, string>(-1, "Tra i soggetti dell'istanza non sono presenti aziende"));

            dgRichiedenti.DataSource = ReadFacade.Domanda
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
            dgRichiedenti.DataBind();

            this.Master.MostraBottoneAvanti = true;
        }

        protected void dgRichiedenti_CancelCommand(object source, GridViewCancelEditEventArgs e)
        {
            dgRichiedenti.EditIndex = -1;
            DataBind();
        }

        protected void dgRichiedenti_UpdateCommand(object source, GridViewUpdateEventArgs e)
        {
            var ddl = (DropDownList)dgRichiedenti.Rows[e.RowIndex].FindControl("ddlAziendeCollegabili");
            var idAziendaColl = Convert.ToInt32(ddl.SelectedValue);

            var key = Convert.ToInt32(dgRichiedenti.DataKeys[e.RowIndex].Value);

            AnagraficheService.CollegaAziendaAdAnagrafica(IdDomanda, key, idAziendaColl);

            dgRichiedenti.EditIndex = -1;
            DataBind();
        }

        public void dgRichiedenti_DeleteCommand(object source, GridViewDeleteEventArgs e)
        {
            int id = Convert.ToInt32(dgRichiedenti.DataKeys[e.RowIndex].Value);

            AnagraficheService.RimuoviAnagrafica(IdDomanda, id);

            DataBind();
        }

        protected void dgRichiedenti_EditCommand(object source, GridViewEditEventArgs e)
        {
            Master.IgnoraSalvataggioDati = true;

            dgRichiedenti.EditIndex = e.NewEditIndex;
            DataBind();
        }

        public void dgRichiedenti_SelectedIndexChanged(object sender, EventArgs e)
        {
            int pk = Convert.ToInt32(dgRichiedenti.DataKeys[dgRichiedenti.SelectedIndex].Value);

            Edit(ReadFacade.Domanda.Anagrafiche.GetById(pk));
        }


        #endregion


        public void multiview_ActiveViewChanged(object sender, EventArgs e)
        {
        }
    }
}
