using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizazioni.Legacy
{
    public class VerticalizzazioneProtocolloAcaris : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_ACARIS";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        public string AppKey
        {
            get { return this.GetString("APPKEY"); }
            set { this.SetString("APPKEY", value); }
        }

        public string Repository
        {
            get { return this.GetString("REPOSITORY"); }
            set { this.SetString("REPOSITORY", value); }
        }
        public int? IdAOO
        {
            get { return this.GetInt("IDAOO"); }
            set { this.SetInt("IDAOO", value); }
        }
        public int? AnniConservazioneCorrente
        {
            get { return this.GetInt("ANNI_CONSERVAZIONE_CORRENTE"); }
            set { this.SetInt("ANNI_CONSERVAZIONE_CORRENTE", value); }
        }
        public int? AnniConservazioneGenerale
        {
            get { return this.GetInt("ANNI_CONSERVAZIONE_GENERALE"); }
            set { this.SetInt("ANNI_CONSERVAZIONE_GENERALE", value); }
        }
        public int? IdGradoVitalita
        {
            get { return this.GetInt("GRADO_VITALITA"); }
            set { this.SetInt("GRADO_VITALITA", value); }
        }

        public int? IdTitolario
        {
            get { return this.GetInt("TITOLARIO"); }
            set { this.SetInt("TITOLARIO", value); }
        }

        public string SerieFascicoli
        {
            get { return this.GetString("SERIEFASCICOLI"); }
            set { this.SetString("SERIEFASCICOLI", value); }
        }

        public string TipoFascicolazione
        {
            get { return this.GetString("TIPO_FASCICOLAZIONE"); }
            set { this.SetString("TIPO_FASCICOLAZIONE", value); }
        }

        public string DescrizioneFascicolo
        {
            get { return this.GetString("DESCRIZIONE_FASCICOLO"); }
            set { this.SetString("DESCRIZIONE_FASCICOLO", value); }
        }

        public string SerieDossier
        {
            get { return this.GetString("SERIE_DOSSIER"); }
            set { this.SetString("SERIE_DOSSIER", value); }
        }

        public string TemplateDescrizioneDossier
        {
            get { return this.GetString("TEMPLATE_DESCRIZIONE_DOSSIER"); }
            set { this.SetString("TEMPLATE_DESCRIZIONE_DOSSIER", value); }
        }

        public int? CodiceDossierMin
        {
            get { return this.GetInt("CODICE_DOSSIER_MIN"); }
            set { this.SetInt("CODICE_DOSSIER_MIN", value); }
        }

        public int? CodiceDossierMax
        {
            get { return this.GetInt("CODICE_DOSSIER_MAX"); }
            set { this.SetInt("CODICE_DOSSIER_MAX", value); }
        }
        public int? CodiceDossierLength
        {
            get { return this.GetInt("CODICE_DOSSIER_LENGTH"); }
            set { this.SetInt("CODICE_DOSSIER_LENGTH", value); }
        }

        public string UrlAutenticazione
        {
            get { return this.GetString("URL_AUTENTICAZIONE"); }
            set { this.SetString("URL_AUTENTICAZIONE", value); }
        }
        public string UrlBackofficeServices
        {
            get { return this.GetString("URL_BACKOFFICE_SERVICES"); }
            set { this.SetString("URL_BACKOFFICE_SERVICES", value); }
        }

        public string UrlDocumentServices
        {
            get { return this.GetString("URL_DOCUMENT_SERVICES"); }
            set { this.SetString("URL_DOCUMENT_SERVICES", value); }
        }

        public string UrlManagementServices
        {
            get { return this.GetString("URL_MANAGEMENT_SERVICES"); }
            set { this.SetString("URL_MANAGEMENT_SERVICES", value); }
        }

        public string UrlNavigationServices
        {
            get { return this.GetString("URL_NAVIGATION_SERVICES"); }
            set { this.SetString("URL_NAVIGATION_SERVICES", value); }
        }
        public string UrlObjectServices
        {
            get { return this.GetString("URL_OBJECT_SERVICES"); }
            set { this.SetString("URL_OBJECT_SERVICES", value); }
        }
        public string UrlOfficialBookServices
        {
            get { return this.GetString("URL_OFFICIALBOOK_SERVICES"); }
            set { this.SetString("URL_OFFICIALBOOK_SERVICES", value); }
        }
        public string UrlRelationshipServices
        {
            get { return this.GetString("URL_RELATIONSHIP_SERVICES"); }
            set { this.SetString("URL_RELATIONSHIP_SERVICES", value); }
        }
        public string UrlRepositoryServices
        {
            get { return this.GetString("URL_REPOSITORY_SERVICES"); }
            set { this.SetString("URL_REPOSITORY_SERVICES", value); }
        }
        public string ConsumerKey
        {
            get { return this.GetString("CONSUMER_KEY"); }
            set { this.SetString("CONSUMER_KEY", value); }
        }
        public string ConsumerSecret
        {
            get { return this.GetString("CONSUMER_SECRET"); }
            set { this.SetString("CONSUMER_SECRET", value); }
        }

        public string TipoFascicolo
        {
            get { return this.GetString("TIPO_FASCICOLO"); }
            set { this.SetString("TIPO_FASCICOLO", value); }
        }

        public string ApplicativoAlimentante
        {
            get { return this.GetString("APPLICATIVO_ALIMENTANTE"); }
            set { this.SetString("APPLICATIVO_ALIMENTANTE", value); }
        }

        public bool GestioneSedi
        {
            get { return this.GetBool("GESTIONE_SEDI"); }
        }

        public bool RicercaFascicoloPerDescrizione
        {
            get { return this.GetBool("RICERCA_FASCICOLO_PER_OGGETTO"); }
        }

        public string TemplateNumeroFascicolo
        {
            get
            {
                var template = this.GetString("NUMERO_FASCICOLO");
                if (string.IsNullOrEmpty(template))
                {
                    template = "[NUMEROISTANZA]";
                }
                return template;
            }
        }

        public bool InviaCopiaCortesia
        {
            get { return this.GetBool("INVIA_COPIA_CORTESIA"); }
        }

        public bool GestisciSottofascicolo
        {
            get { return this.GetBool("GESTISCI_SOTTOFASCICOLO"); }
        }

        public string DescrizioneSottofascicolo
        {
            get { return this.GetString("DESCRIZIONE_SOTTOFASCICOLO"); }
            set { this.SetString("DESCRIZIONE_SOTTOFASCICOLO", value); }
        }

        public bool ValorizzaSoggettoFascicolo
        {
            get { return this.GetBool("VALORIZZA_SOGGETTO_FASCICOLO", true); }
        }

        public bool AnnotaAllegatoPrincipale
        {
            get { return this.GetBool("ANNOTA_ALLEGATO_PRINCIPALE", true); }
        }

        public bool AnnotaAllegatoSecondario
        {
            get { return this.GetBool("ANNOTA_ALLEGATO_SECONDARIO", true); }
        }

        public VerticalizzazioneProtocolloAcaris()
        {
            
        }
        public VerticalizzazioneProtocolloAcaris(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }
    }
}
