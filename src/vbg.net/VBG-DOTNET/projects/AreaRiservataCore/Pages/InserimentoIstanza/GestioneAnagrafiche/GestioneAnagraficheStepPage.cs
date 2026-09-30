using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.Infrastructure;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.DTO.TabelleDiBase;
using log4net;
using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneAnagrafiche
{
    public abstract class GestioneAnagraficheStepPage<TTipoSoggetto> : IstanzeStepPage
    {
        protected static class Constants
        {
            public const string MSG_ERRORE_UTENTE_NON_PRESENTE = "L'utente con cui si è effettuato l'accesso (Nominativo: {0}, Codice fiscale: {1}) non è presente nella lista dei soggetti coinvolti nella domanda";
            public const string TestoAggiungiRichiedente = "Aggiungi il beneficiario";
            public const string TestoAggiungiAzienda = "Aggiungi società/ditta individuale";
            public const string TestoAggiungiUtenteLoggato = "Aggiungi i tuoi dati";
            public const string TestoAggiungiAziendaUtenteLoggato = "Aggiungi l'ente o l'associazione a cui appartieni";
        }

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


        #region parametri letti dalla configurazione steps
        [StepProperty]
        public bool VerificaPecObbligatoria { get; set; } = false;

        [StepProperty]
        public string MessaggioAvvertimentoVerificaPEC { get; set; } = "";

        [StepProperty]
        public string MessaggioUtenteNonPresente { get; set; } = Constants.MSG_ERRORE_UTENTE_NON_PRESENTE;

        [StepProperty]
        public bool RendiModificabiliDatiAnagraficheEsistenti { get; set; } = true;

        [StepProperty]
        public bool IgnoraRicercaBackofficePerPersoneFisiche { get; set; } = false;

        [StepProperty]
        public string TestoDescrizioneSteps { get; set; } = String.Empty;

        [StepProperty]
        public int PFCampoTitolo
        {
            set
            {
                this.DettagliPf_TitoloVisibile = value > 0;
                this.DettagliPf_TitoloObbligatorio = value > 1;
            }
        }

        [StepProperty]
        public int PFCampoResidenza
        {
            set
            {
                this.DettagliPf_ResidenzaVisible = value > 0;
                this.DettagliPf_ResidenzaObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PFCampoTelefono
        {
            set
            {
                this.DettagliPf_TelefonoVisible = value > 0;
                this.DettagliPf_TelefonoObbligatorio = value > 1;
            }
        }

        [StepProperty]
        public int PFCampoCellulare
        {
            set
            {
                this.DettagliPf_CellulareVisible = value > 0;
                this.DettagliPf_CellulareObbligatorio = value > 1;
            }
        }

        public bool EmailoPecObbligatori { get; set; } = false;

        public bool TelefonooCellulareObbligatori { get; set; } = false;

        [StepProperty]
        public int PFCampoEmail
        {
            set
            {
                this.DettagliPf_EmailVisible = value > 0;
                this.DettagliPf_EmailObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PFCampoPec
        {
            set
            {
                this.DettagliPf_PecVisible = value > 0;
                this.DettagliPf_PecObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PFCampoCorrispondenza
        {
            set
            {
                this.DettagliPf_CorrispondenzaVisibile = value > 0;
                this.DettagliPf_CorrispondenzaObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public string PFTitoloBloccoIndirizzoCorrispondenza
        {
            get { return this.DettagliPf_TitoloBloccoIndirizzoCorrispondenza; }
            set { this.DettagliPf_TitoloBloccoIndirizzoCorrispondenza = value; }
        }

        [StepProperty]
        public int PFCampoCittadinanza
        {
            set
            {
                this.DettagliPf_CittadinanzaVisible = value > 0;
                this.DettagliPf_CittadinanzaObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PGSedeLegale
        {
            set
            {
                this.DettagliPg_SedeLegaleVisibile = value > 0;
                this.DettagliPg_SedeLegaleObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PGDataCostituzione
        {
            set
            {
                this.DettagliPg_DataCostituzioneVisibile = value > 0;
                this.DettagliPg_DataCostituzioneObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PGTelefono
        {
            set
            {
                this.DettagliPg_TelefonoVisibile = value > 0;
                this.DettagliPg_TelefonoObbligatorio = value > 1;
            }
        }

        [StepProperty]
        public int PGCellulare
        {
            set
            {
                this.DettagliPg_CellulareVisibile = value > 0;
                this.DettagliPg_CellulareObbligatorio = value > 1;
            }
        }

        [StepProperty]
        public int PGFax
        {
            set
            {
                this.DettagliPg_FaxVisibile = value > 0;
                this.DettagliPg_FaxObbligatorio = value > 1;
            }
        }

        [StepProperty]
        public int PGCciaa
        {
            set
            {
                this.DettagliPg_CciaaVisibile = value > 0;
                this.DettagliPg_CciaaObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PGRegTrib
        {
            set
            {
                this.DettagliPg_RegTribVisibile = value > 0;
                this.DettagliPg_RegTribObbligatorio = value > 1;
            }
        }

        [StepProperty]
        public int PGRea
        {
            set
            {
                this.DettagliPg_ReaVisibile = value > 0;
                this.DettagliPg_ReaObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PGInps
        {
            set
            {
                this.DettagliPg_InpsVisibile = value > 0;
                this.DettagliPg_InpsObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PGInail
        {
            set
            {
                this.DettagliPg_InailVisibile = value > 0;
                this.DettagliPg_InailObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PGEmail
        {
            set
            {
                this.DettagliPg_EmailVisibile = value > 0;
                this.DettagliPg_EmailObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PGPec
        {
            set
            {
                this.DettagliPg_PecVisibile = value > 0;
                this.DettagliPg_PecObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PGPartitaIva
        {
            set
            {
                this.DettagliPg_PartitaIvaVisibile = value > 0;
                this.DettagliPg_PartitaIvaObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public int PGCampoCorrispondenza
        {
            set
            {
                this.DettagliPg_CorrispondenzaVisibile = value > 0;
                this.DettagliPg_CorrispondenzaObbligatoria = value > 1;
            }
        }

        [StepProperty]
        public string LimitaDatiAlbo
        {
            set
            {
                this.DettagliPf_LimitaDatiAlbo = value;
            }
        }

        [StepProperty]
        public bool GestioneSoggettoUnico { get; set; } = false;

        #endregion


        #region proprietà dettagli anagrafiche


        public bool DettagliPf_TitoloVisibile { get; set; } = true;
        public bool DettagliPf_TitoloObbligatorio { get; set; } = false;
        public bool DettagliPf_ResidenzaVisible { get; set; } = true;
        public bool DettagliPf_ResidenzaObbligatoria { get; set; } = false;
        public bool DettagliPf_TelefonoVisible { get; set; } = true;
        public bool DettagliPf_TelefonoObbligatorio { get; set; } = false;
        public bool DettagliPf_CellulareVisible { get; set; } = true;
        public bool DettagliPf_CellulareObbligatorio { get; set; } = false;
        public bool DettagliPf_EmailVisible { get; set; } = true;
        public bool DettagliPf_EmailObbligatoria { get; set; } = false;
        public bool DettagliPf_PecVisible { get; set; } = true;
        public bool DettagliPf_PecObbligatoria { get; set; } = false;
        public bool DettagliPf_CorrispondenzaVisibile { get; set; } = true;
        public bool DettagliPf_CorrispondenzaObbligatoria { get; set; } = false;
        public string DettagliPf_TitoloBloccoIndirizzoCorrispondenza { get; set; }
        public bool DettagliPf_CittadinanzaVisible { get; set; } = true;
        public bool DettagliPf_CittadinanzaObbligatoria { get; set; } = false;
        public bool DettagliPg_SedeLegaleVisibile { get; set; } = true;
        public bool DettagliPg_SedeLegaleObbligatoria { get; set; } = false;
        public bool DettagliPg_DataCostituzioneVisibile { get; set; } = true;
        public bool DettagliPg_DataCostituzioneObbligatoria { get; set; } = false;
        public bool DettagliPg_TelefonoVisibile { get; set; } = true;
        public bool DettagliPg_TelefonoObbligatorio { get; set; } = false;
        public bool DettagliPg_CellulareVisibile { get; set; } = true;
        public bool DettagliPg_CellulareObbligatorio { get; set; } = false;
        public bool DettagliPg_FaxVisibile { get; set; } = true;
        public bool DettagliPg_FaxObbligatorio { get; set; } = false;
        public bool DettagliPg_CciaaVisibile { get; set; } = true;
        public bool DettagliPg_CciaaObbligatoria { get; set; } = false;
        public bool DettagliPg_RegTribVisibile { get; set; } = true;
        public bool DettagliPg_RegTribObbligatorio { get; set; } = false;
        public bool DettagliPg_ReaVisibile { get; set; } = true;
        public bool DettagliPg_ReaObbligatoria { get; set; } = false;
        public bool DettagliPg_InpsVisibile { get; set; } = true;
        public bool DettagliPg_InpsObbligatoria { get; set; } = false;
        public bool DettagliPg_InailVisibile { get; set; } = true;
        public bool DettagliPg_InailObbligatoria { get; set; } = false;
        public bool DettagliPg_EmailVisibile { get; set; } = true;
        public bool DettagliPg_EmailObbligatoria { get; set; } = false;
        public bool DettagliPg_PecVisibile { get; set; } = true;
        public bool DettagliPg_PecObbligatoria { get; set; } = false;
        public bool DettagliPg_PartitaIvaVisibile { get; set; } = true;
        public bool DettagliPg_PartitaIvaObbligatoria { get; set; } = false;
        public bool DettagliPg_CorrispondenzaVisibile { get; set; } = true;
        public bool DettagliPg_CorrispondenzaObbligatoria { get; set; } = false;
        public string DettagliPf_LimitaDatiAlbo { get; set; }

        #endregion

        [Inject]
        public ICittadinanzeService CittadinanzeService { get; set; } = default!;
        [Inject]
        public IAnagraficheService AnagraficheService { get; set; } = default!;
        [Inject]
        public IRicercheAnagraficheService RicercheAnagraficheService { get; set; } = default!;
        [Inject]
        public IConfigurazione<ParametriWorkflow> ConfigurazioneWorkflow { get; set; } = default!;
        [Inject]
        public IsUtenteAnonimoSpecification IsUtenteAnonimo { get; set; } = default!;
        [Inject]
        public IComuniService _comuniService { get; set; } = default!;
        [Inject]
        public IFormeGiuridicheRepository _formeGiuridicheRepository { get; set; } = default!;


        protected ILog m_logger = LogManager.GetLogger(typeof(GestioneAnagrafiche));

        public delegate void ErrorDelegate(string message);

        private int? _codiceIntervento = null;
        protected int? CodiceIntervento
        {
            get
            {
                if (this._codiceIntervento == null)
                {
                    this._codiceIntervento = this.DomandaCorrente.AltriDati.Intervento == null ? null : this.DomandaCorrente.AltriDati.Intervento.Codice;
                }

                return this._codiceIntervento;
            }
        }

        protected FormaGiuridicaDto? GetFormaGiuridica(int? idFormaGiuridica)
        {
            if (idFormaGiuridica == null)
                return null;

            return this._formeGiuridicheRepository.GetById(idFormaGiuridica.Value.ToString());
        }

        protected DatiProvinciaCompatto? GetDatiProvincia(string siglaProvincia)
        {
            if (String.IsNullOrEmpty(siglaProvincia))
                return null;

            return this._comuniService.GetDatiProvincia(siglaProvincia);
        }

        protected CittadinanzaCompatto? GetDatiCittadinanza(string strIdCittadinanza)
        {
            if (String.IsNullOrEmpty(strIdCittadinanza))
                return null;

            return this.CittadinanzeService.GetCittadinanzaDaId(Convert.ToInt32(strIdCittadinanza));
        }

        protected DatiComuneCompatto? GetDatiComune(string codiceComune)
        {
            if (String.IsNullOrEmpty(codiceComune))
            {
                return null;
            }

            return this._comuniService.GetByCodiceComune(codiceComune);
        }

        protected string GetComuneProvinciaFormattati(string codiceComune)
        {
            var comune = this.GetDatiComune(codiceComune);

            if (comune is null)
                return string.Empty;

            return $"{comune.Comune} ({comune.SiglaProvincia})";
        }

        protected abstract IEnumerable<TTipoSoggetto> GetTipiSoggettoPersFisica();

        protected abstract IEnumerable<TTipoSoggetto> GetTipiSoggettoPersGiuridica();

        protected abstract TTipoSoggetto? GetTipoSoggetto(int idTipoSoggetto);

        protected AnagraficaDomanda GetAnagrafeRow(int idAnagrafica)
        {
            var anagrafica = this.DomandaCorrente.Anagrafiche.GetById(idAnagrafica);

            if (anagrafica != null)
                return anagrafica;

            return AnagraficaDomanda.New(idAnagrafica);
        }

        protected bool VerificaRichiedenteAutomatico()
        {
            if (!this.ConfigurazioneWorkflow.Parametri.ImpostaAutomaticamenteAnagraficaUtenteCorrente)
            {
                return false;
            }

            if (this.DomandaCorrente.Anagrafiche.Anagrafiche.Count() > 0)
            {
                return false;
            }

            if (this.IsUtenteAnonimo.IsSatisfiedBy(this.UserAuthenticationResult))
            {
                return false;
            }

            return true;
        }
    }
}
