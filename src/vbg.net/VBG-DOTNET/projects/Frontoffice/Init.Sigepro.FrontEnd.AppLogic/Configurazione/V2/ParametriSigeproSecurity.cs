using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriSigeproSecurity : IParametriConfigurazione
    {

        private static class Constants
        {
            public static class Net
            {
                public const string WS_DATI_COMUNI_SERVICE = "/WebServices/WsAreaRiservata/WcfServices/WsComuniService.svc";
                public const string WS_STRADARIO = "/WebServices/WsAreaRiservata/WcfServices/Stradario/WsStradarioService.svc";
                public const string WS_ISTANZE = "/WebServices/WsAreaRiservata/WcfServices/Istanze/WsIstanzeService.svc";
                // public const string WS_MODULISTICA = "/WebServices/WsSIGePro/WsModulistica.asmx";
                public const string WS_AUTORIZZAZIONI = "/WebServices/WsAreaRiservata/WcfServices/Autorizzazioni/WsAutorizzazioniService.svc";
                public const string WS_LETTURA_MOVIMENTI = "/WebServices/WsAreaRiservata/WcfServices/Scadenzario/WsScadenzarioService.svc";
                public const string WS_BOOKMARKS = "/WebServices/WsAreaRiservata/WcfServices/Bookmarks/WsBookmarksService.svc";
                public const string WS_ACCESSO_ATTI = "/WebServices/WsAreaRiservata/WcfServices/AccessoAtti/WsAccessoAttiService.svc";
                // public const string WS_TARES_BARI = "/WebServices/WsTaresBari/TaresService.asmx";
                // public const string WS_CONFIG_BARI = "/WebServices/WsBari/BariConfigService.asmx";
                public const string WS_SCRIVANIA_ENTI_TERZI = "/WebServices/WsAreaRiservata/WcfServices/EntiTerzi/WsEntiTerziService.svc";
                public const string WS_NODO_PAGAMENTI = "/WebServices/WsAreaRiservata/WcfServices/pagamenti/WsNodoPagamentiService.svc";
                public const string WS_URL_ACCESSO_CONSOLE = "/WebServices/WsAreaRiservata/WcfServices/Console/WsUrlAccessoConsoleService.svc";
                public const string WS_URL_ENDO_FRONTOFFICE = "/WebServices/WsAreaRiservata/WcfServices/EndoFrontoffice/WsEndoFrontofficeService.svc";
                public const string WS_URL_ENDO = "/WebServices/WsAreaRiservata/WcfServices/Endoprocedimenti/WsEndoprocedimenti.svc";
                public const string WS_URL_INTERVENTI = "/WebServices/WsAreaRiservata/WcfServices/Interventi/WsInterventi.svc";
                public const string WS_URL_CONFIGURAZIONE_AREA_RISERVATA = "/WebServices/WsAreaRiservata/WcfServices/Configurazione/WsConfigurazioneAreaRiservata.svc";
                public const string WS_URL_COMMISSIONI = "/WebServices/WsAreaRiservata/WcfServices/Commissioni/WsCommissioni.svc";
                public const string WS_URL_VOTAZIONE_COMMISSIONE = "/WebServices/WsAreaRiservata/WcfServices/Commissioni/WsVotazioniCommissione.svc";
                public const string WS_URL_PIN_COMMISSIONE = "/WebServices/WsAreaRiservata/WcfServices/Commissioni/WsCommissioniAccessoPIN.svc";
                public const string WS_URL_QUESTIONARIO_SODDISFAZIONE = "/WebServices/WsAreaRiservata/WcfServices/QuestionarioFo/WsQuestionarioFoService.svc";
                public const string WS_URL_ALLEGATI_DOMANDA = "/WebServices/WsAreaRiservata/WcfServices/AllegatiDomanda/WsAllegatiDomanda.svc";
                public const string WS_URL_ONERI = "/WebServices/WsAreaRiservata/WcfServices/Oneri/WsOneriService.svc";
                public const string WS_URL_CONTI = "/WebServices/WsAreaRiservata/WcfServices/Conti/WsContiService.svc";
                public const string WS_URL_TIPI_SOGGETTO = "/WebServices/WsAreaRiservata/WcfServices/TipiSoggetto/WsTipiSoggettoService.svc";
                public const string WS_URL_DATI_DOMANDA = "/WebServices/WsAreaRiservata/WcfServices/DatiDomanda/WsDatiDomandaService.svc";
                public const string WS_URL_CAMPI_RICERCA_PRATICHE = "/WebServices/WsAreaRiservata/WcfServices/Visura/WsRicercaVisuraService.svc";
                public const string WS_URL_PAGAMENTI_ESED = "/WebServices/WsAreaRiservata/WcfServices/PagamentiESED/WsPagamentiESEDService.svc";
                public const string WS_URL_SOGGETTI_FIRMATARI = "/WebServices/WsAreaRiservata/WcfServices/SoggettiFirmatari/WsSoggettiFirmatariService.svc";
                public const string WS_URL_TABELLE_DI_BASE = "/WebServices/WsAreaRiservata/WcfServices/TabelleDiBase/WsTabelleDiBaseService.svc";
                public const string WS_URL_MAPPATURE = "/WebServices/WsAreaRiservata/WcfServices/Mappature/WsMappatureService.svc";
                public const string WS_URL_RISORSE_TESTUALI = "/WebServices/WsAreaRiservata/WcfServices/RisorseTestuali/WsRisorseTestualiService.svc";
                public const string WS_URL_INTEGRAZIONE_LDP = "/WebServices/WsAreaRiservata/WcfServices/IntegrazioneLDP/WsIntegrazioneLDPService.svc";
                public const string WS_URL_CONFIGURAZIONE_CONTENUTI = "/WebServices/WsAreaRiservata/WcfServices/ConfigurazioneContenuti/WsConfigurazioneContenutiService.svc";
                public const string WS_URL_ANAGRAFICHE_VBG = "/WebServices/WsAreaRiservata/WcfServices/Anagrafiche/WsAnagraficheService.svc";
                public const string WS_DATI_DINAMICI = "/WebServices/WsAreaRiservata/WcfServices/DatiDinamici/WsDatiDinamici.svc";
            }

            public const string WS_API_REST_JAVA_AUTENTICATE = "/services/rest-auth-token";
            public const string WS_ALBO_PRETORIO = "/services/alboPretorio";
            public const string WS_OGGETTI_SERVICE = "/services/oggetti";
            public const string WS_CREAZIONE_ANAGRAFE = "/services/anagrafe";
            public const string WS_CREAZIONE_QRCODE = "/services/qrcode";
            public const string WS_AUTORIZZAZIONI_TRANSITI = "/services/autorizzazioniAccessi";
            public const string WS_SCARICA_ZIP_PRATICA = "/services/api-rest/pratiche/scarica-zip-pratica";
            public const string URL_BASE_SERVIZI_TOKEN = "v1/security/token/";
        }

        public string UrlPdfUtilsService { get; }
        public string UrlIstanzeService { get; }
        public string UrlAlboPretorioService { get; }
        public string UrlCreazioneAnagrafeService { get; }
        public string UrlConversioneFileService { get; }
        public string UrlVerificaFirmaRestService { get; }
        public string UrlOggettiService { get; }
        public string UrlWebServiceMovimenti { get; }
        public string UrlWebServiceBookmarks { get; }
        public int TokenTimeout { get; }
        public Uri UrlAutorizzazioniMercatiService { get; }
        // public string UrlServizioTares { get; }
        // public string UrlServizioConfigBari { get; }
        // public string UrlWsModulisticaFrontoffice { get; }
        public string UrlGenerazioneQrCode { get; }
        public string UrlServizioEntiTerzi { get; }
        public string UrlServizioAccessoAtti { get; }
        public string UrlServizioAutorizzazioniTransiti { get; }
        public string? UrlServizioDownloadDocumentiZIP { get; }
        public string UrlWsComuniService { get; }
        public string UrlWsStradario { get; }
        public string UrlWsNodoPagamenti { get; }
        public string UrlWsUrlAccessoConsoleService { get; }
        public string UrlWsEndoFrontoffice { get; }
        public string UrlWsEndoprocedimenti { get; }
        public string UrlWsInterventi { get; }
        public string LegacyAspNetBaseUrl { get; }
        public string UrlServizioCommissioni { get; }
        public string UrlServizioVotazioniCommissione { get; }
        public string UrlServizioPINCommissioni { get; }
        public string UrlQuestionarioSoddisfazione { get; }
        public string UrlAllegatiDomandaWs { get; }
        public string UrlOneriService { get; }
        public string UrlContiService { get; }
        public string UrlWsConfigurazioneAreaRiservata { get; }
        public string UrlTipiSoggettoService { get; }
        public string UrlWsDatiDomanda { get; }
        public string UrlCampiRicercaPraticheService { get; }
        public string UrlWsPagamentiESED { get; }
        public string UrlWsSoggettiFirmatari { get; }
        public string UrlTabelleDiBaseService { get; }
        public string UrlMappatureService { get; }
        public string UrlRisorseTestualiService { get; }
        public string UrlIntegrazioneLDP { get; }
        public string UrlConfigurazioneContenuti { get; }
        public string UrlAnagraficheVBG { get; }
        public string AspNetCoreBaseUrl { get; }
        public string UrlWsDatiDinamici { get; }
        public string? UrlApiRestJavaAutenticate { get; }
        public string UrlServizioCartografico { get; }
        public string UrlMailService { get; }
        public string UrlSIT { get; }

        /// <summary>
        /// Url base per i servizi REST dei metadati del token. Termina sempre con uno slash
        /// </summary>
        public string UrlBaseServiziToken { get; }
        public bool ServiziMetadataTokenAttivi => !String.IsNullOrEmpty(this.UrlBaseServiziToken);

        public string AspnetClearCacheUrl { get; }

        internal ParametriSigeproSecurity(string aspNetBaseUrl, string aspNetCoreBaseUrl, string javaBaseUrl,
                                          string apiBackendUrl, string conversioneFilesUrl,
                                          string verificaFirmaUrl,
                                          int tokenTimeout,
                                          string pdfUtilsServiceUrl,
                                          string urlBaseServiziRestSecurity,
                                          string apiCartograficoUrl,
                                          string urlMailService,
                                          string urlSIT,
                                          IAppConfigurationReader appConfigurationReader)
        {
            this.UrlConversioneFileService = conversioneFilesUrl;
            this.UrlVerificaFirmaRestService = verificaFirmaUrl;
            this.UrlPdfUtilsService = pdfUtilsServiceUrl;
            this.TokenTimeout = tokenTimeout;

            this.LegacyAspNetBaseUrl = aspNetBaseUrl;
            this.AspNetCoreBaseUrl = aspNetCoreBaseUrl;

            if (!String.IsNullOrEmpty(aspNetCoreBaseUrl))
            {
                aspNetBaseUrl = aspNetCoreBaseUrl;

                this.AspnetClearCacheUrl = $"{aspNetCoreBaseUrl}{(aspNetCoreBaseUrl.EndsWith("/") ? "" : "/")}clearcache";
            }

            // Servizi .net
            this.UrlIstanzeService = aspNetBaseUrl + Constants.Net.WS_ISTANZE;
            this.UrlWebServiceMovimenti = aspNetBaseUrl + Constants.Net.WS_LETTURA_MOVIMENTI;
            this.UrlAutorizzazioniMercatiService = new Uri(aspNetBaseUrl + Constants.Net.WS_AUTORIZZAZIONI);
            // this.UrlServizioTares = aspNetBaseUrl + Constants.Net.WS_TARES_BARI;
            // this.UrlServizioConfigBari = aspNetBaseUrl + Constants.Net.WS_CONFIG_BARI;

            this.UrlWebServiceBookmarks = aspNetBaseUrl + Constants.Net.WS_BOOKMARKS;
            // this.UrlWsModulisticaFrontoffice = aspNetBaseUrl + Constants.Net.WS_MODULISTICA;
            this.UrlServizioEntiTerzi = aspNetBaseUrl + Constants.Net.WS_SCRIVANIA_ENTI_TERZI;
            this.UrlServizioAccessoAtti = aspNetBaseUrl + Constants.Net.WS_ACCESSO_ATTI;
            this.UrlWsComuniService = aspNetBaseUrl + Constants.Net.WS_DATI_COMUNI_SERVICE;
            this.UrlWsStradario = aspNetBaseUrl + Constants.Net.WS_STRADARIO;
            this.UrlWsNodoPagamenti = aspNetBaseUrl + Constants.Net.WS_NODO_PAGAMENTI;
            this.UrlWsUrlAccessoConsoleService = aspNetBaseUrl + Constants.Net.WS_URL_ACCESSO_CONSOLE;
            this.UrlWsEndoFrontoffice = aspNetBaseUrl + Constants.Net.WS_URL_ENDO_FRONTOFFICE;
            this.UrlWsEndoprocedimenti = aspNetBaseUrl + Constants.Net.WS_URL_ENDO;
            this.UrlWsInterventi = aspNetBaseUrl + Constants.Net.WS_URL_INTERVENTI;
            this.UrlServizioCommissioni = aspNetBaseUrl + Constants.Net.WS_URL_COMMISSIONI;
            this.UrlServizioVotazioniCommissione = aspNetBaseUrl + Constants.Net.WS_URL_VOTAZIONE_COMMISSIONE;
            this.UrlServizioPINCommissioni = aspNetBaseUrl + Constants.Net.WS_URL_PIN_COMMISSIONE;
            this.UrlQuestionarioSoddisfazione = aspNetBaseUrl + Constants.Net.WS_URL_QUESTIONARIO_SODDISFAZIONE;
            this.UrlAllegatiDomandaWs = aspNetBaseUrl + Constants.Net.WS_URL_ALLEGATI_DOMANDA;
            this.UrlOneriService = aspNetBaseUrl + Constants.Net.WS_URL_ONERI;
            this.UrlContiService = aspNetBaseUrl + Constants.Net.WS_URL_CONTI;
            this.UrlWsConfigurazioneAreaRiservata = aspNetBaseUrl + Constants.Net.WS_URL_CONFIGURAZIONE_AREA_RISERVATA;
            this.UrlTipiSoggettoService = aspNetBaseUrl + Constants.Net.WS_URL_TIPI_SOGGETTO;
            this.UrlWsDatiDomanda = aspNetBaseUrl + Constants.Net.WS_URL_DATI_DOMANDA;
            this.UrlCampiRicercaPraticheService = aspNetBaseUrl + Constants.Net.WS_URL_CAMPI_RICERCA_PRATICHE;
            this.UrlWsPagamentiESED = aspNetBaseUrl + Constants.Net.WS_URL_PAGAMENTI_ESED;
            this.UrlWsSoggettiFirmatari = aspNetBaseUrl + Constants.Net.WS_URL_SOGGETTI_FIRMATARI;
            this.UrlTabelleDiBaseService = aspNetBaseUrl + Constants.Net.WS_URL_TABELLE_DI_BASE;
            this.UrlMappatureService = aspNetBaseUrl + Constants.Net.WS_URL_MAPPATURE;
            this.UrlRisorseTestualiService = aspNetBaseUrl + Constants.Net.WS_URL_RISORSE_TESTUALI;
            this.UrlIntegrazioneLDP = aspNetBaseUrl + Constants.Net.WS_URL_INTEGRAZIONE_LDP;
            this.UrlConfigurazioneContenuti = aspNetBaseUrl + Constants.Net.WS_URL_CONFIGURAZIONE_CONTENUTI;
            this.UrlAnagraficheVBG = aspNetBaseUrl + Constants.Net.WS_URL_ANAGRAFICHE_VBG;
            this.UrlWsDatiDinamici = aspNetBaseUrl + Constants.Net.WS_DATI_DINAMICI;


            // servizi java
            this.UrlServizioCartografico = apiCartograficoUrl;
            this.UrlApiRestJavaAutenticate = String.IsNullOrEmpty(apiBackendUrl) ? null : apiBackendUrl + Constants.WS_API_REST_JAVA_AUTENTICATE;
            this.UrlServizioDownloadDocumentiZIP = String.IsNullOrEmpty(apiBackendUrl) ? null : apiBackendUrl + Constants.WS_SCARICA_ZIP_PRATICA;
            this.UrlAlboPretorioService = javaBaseUrl + Constants.WS_ALBO_PRETORIO;
            this.UrlOggettiService = javaBaseUrl + Constants.WS_OGGETTI_SERVICE;
            this.UrlGenerazioneQrCode = javaBaseUrl + Constants.WS_CREAZIONE_QRCODE;
            this.UrlServizioAutorizzazioniTransiti = javaBaseUrl + Constants.WS_AUTORIZZAZIONI_TRANSITI;
            this.UrlCreazioneAnagrafeService = javaBaseUrl + Constants.WS_CREAZIONE_ANAGRAFE;
            this.UrlMailService = urlMailService;

            this.UrlSIT = urlSIT;

            this.UrlBaseServiziToken = "";

            if (!String.IsNullOrEmpty(urlBaseServiziRestSecurity))
            {
                this.UrlBaseServiziToken = $"{this.EnsureEndInTrailingSlash(urlBaseServiziRestSecurity)}{Constants.URL_BASE_SERVIZI_TOKEN}";
            }


            var overrideAnagrafeService = appConfigurationReader.GetSetting("overrideAnagrafeServiceUrl");

            if (!String.IsNullOrEmpty(overrideAnagrafeService))
                this.UrlCreazioneAnagrafeService = overrideAnagrafeService;

            // this.AspNetBaseUrl = aspNetBaseUrl;
        }

        private string EnsureEndInTrailingSlash(string urlBaseServiziRestSecurity)
        {
            if (urlBaseServiziRestSecurity.EndsWith("/"))
            {
                return urlBaseServiziRestSecurity;
            }

            return urlBaseServiziRestSecurity + "/";
        }
    }
}
