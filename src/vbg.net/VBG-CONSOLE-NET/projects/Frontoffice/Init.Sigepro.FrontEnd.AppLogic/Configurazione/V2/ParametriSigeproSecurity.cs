using System;
using System.Collections.Generic;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriSigeproSecurity : IParametriConfigurazione
    {
        private static class ConstantsNet
        {
            public const string WS_AREA_RISERVATA = "/WebServices/WsAreaRiservata/AreaRiservataService.asmx";
            public const string WS_DATI_COMUNI_SERVICE = "/WebServices/WsAreaRiservata/WcfServices/WsComuniService.svc";
            public const string WS_ISTANZE = "/WebServices/WsSIGePro/Istanze.asmx";
            public const string WS_MODULISTICA = "/WebServices/WsSIGePro/WsModulistica.asmx";
            public const string WS_AUTORIZZAZIONI = "/WebServices/wsautorizzazioni/autorizzazioniwebservice.asmx";
            public const string WS_LETTURA_MOVIMENTI = "/WebServices/WsAreaRiservata/WcfServices/Scadenzario/WsScadenzarioService.svc";
            public const string WS_BOOKMARKS = "/WebServices/WsAreaRiservata/BookmarksService.asmx";
            public const string WS_ACCESSO_ATTI = "/WebServices/accesso-atti/ws-accesso-atti.asmx";
            public const string WS_TARES_BARI = "/WebServices/WsTaresBari/TaresService.asmx";
            public const string WS_CONFIG_BARI = "/WebServices/WsBari/BariConfigService.asmx";
            public const string WS_SCRIVANIA_ENTI_TERZI = "/WebServices/enti-terzi/ws-enti-terzi.asmx";
            public const string WS_NODO_PAGAMENTI = "/WebServices/WsAreaRiservata/WcfServices/pagamenti/WsNodoPagamentiService.svc";
            public const string WS_URL_ACCESSO_CONSOLE = "/WebServices/WsAreaRiservata/WcfServices/Console/WsUrlAccessoConsoleService.svc";
            public const string WS_URL_ENDO_FRONTOFFICE = "/WebServices/WsAreaRiservata/WcfServices/EndoFrontoffice/WsEndoFrontofficeService.svc";
            public const string WS_URL_ENDO = "/WebServices/WsAreaRiservata/WcfServices/Endoprocedimenti/WsEndoprocedimenti.svc";
            public const string WS_URL_INTERVENTI = "/WebServices/WsAreaRiservata/WcfServices/Interventi/WsInterventi.svc";
            public const string WS_URL_COMMISSIONI = "/WebServices/WsAreaRiservata/WcfServices/Commissioni/WsCommissioni.svc";
            public const string WS_URL_VOTAZIONE_COMMISSIONE = "/WebServices/WsAreaRiservata/WcfServices/Commissioni/WsVotazioniCommissione.svc";
            public const string WS_URL_PIN_COMMISSIONE = "/WebServices/WsAreaRiservata/WcfServices/Commissioni/WsCommissioniAccessoPIN.svc";
            public const string WS_URL_QUESTIONARIO_SODDISFAZIONE = "/WebServices/WsAreaRiservata/WcfServices/QuestionarioFo/WsQuestionarioFoService.svc";
            public const string WS_URL_ONERI = "/WebServices/WsAreaRiservata/WcfServices/Oneri/WsOneriService.svc";
        }

        private static class Constants
        {
            public const string WS_ALBO_PRETORIO = "/services/alboPretorio";
            public const string WS_OGGETTI_SERVICE = "/services/oggetti";
            public const string WS_CREAZIONE_ANAGRAFE = "/services/anagrafe";
            public const string WS_CREAZIONE_QRCODE = "/services/qrcode";
            public const string WS_AUTORIZZAZIONI_TRANSITI = "/services/autorizzazioniAccessi";
            public const string WS_SCARICA_ZIP_PRATICA = "/services/api-rest/pratiche/scarica-zip-pratica";
        }

        public readonly string ServiceUrl;
        public readonly string Username;
        public readonly string Md5Password;
        public readonly string UrlPdfUtilsService;
        public readonly string UrlAreaRiservataService;
        public readonly string UrlIstanzeService;
        public readonly string UrlAlboPretorioService;
        public readonly string UrlCreazioneAnagrafeService;
        public readonly string UrlConversioneFileService;
        public readonly string UrlVerificaFirmaService;
        public readonly string UrlOggettiService;
        public readonly string UrlWebServiceMovimenti;
        public readonly string UrlWebServiceBookmarks;
        public readonly Dictionary<string, string> AltriParametri;
        public readonly int TokenTimeout;
        public readonly Uri UrlAutorizzazioniMercatiService;
        public readonly string UrlServizioTares;
        public readonly string UrlServizioConfigBari;
        public readonly string UrlWsModulisticaFrontoffice;
        public readonly string UrlGenerazioneQrCode;
        public readonly string UrlServizioEntiTerzi;
        public readonly string UrlServizioAccessoAttiSiena;
        public readonly string UrlServizioAutorizzazioniTransiti;
        public readonly string UrlServizioDownloadDocumentiZIP;
        public readonly string UrlWsComuniService;
        public readonly string UrlWsNodoPagamenti;
        public readonly string UrlWsUrlAccessoConsoleService;
        public readonly string UrlWsEndoFrontoffice;
        public readonly string UrlWsEndoprocedimenti;
        public readonly string UrlWsInterventi;
        public readonly string AspNetBaseUrl;
        public readonly string UrlServizioCommissioni;
        public readonly string UrlServizioVotazioniCommissione;
        public readonly string UrlServizioPINCommissioni;
        public readonly string UrlQuestionarioSoddisfazione;
        public readonly string UrlOneriService;

        public readonly string UrlVerificaFirmaRestService;

        internal ParametriSigeproSecurity(string aspNetBaseUrl, string javaBaseUrl,
                                          string apiBackendUrl, string conversioneFilesUrl,
                                          string verificaFirmaUrl,
                                          int tokenTimeout, Dictionary<string, string> altriParametri,
                                          string pdfUtilsServiceUrl, string urlVerificaFirmaRestService)
        {

            this.UrlConversioneFileService = conversioneFilesUrl;
            this.UrlVerificaFirmaService = verificaFirmaUrl;
            this.UrlPdfUtilsService = pdfUtilsServiceUrl;
            this.UrlVerificaFirmaRestService = urlVerificaFirmaRestService;
            this.AltriParametri = altriParametri;
            this.TokenTimeout = tokenTimeout;


            // Servizi .net
            this.UrlIstanzeService = aspNetBaseUrl + ConstantsNet.WS_ISTANZE;
            this.UrlAreaRiservataService = aspNetBaseUrl + ConstantsNet.WS_AREA_RISERVATA;
            this.UrlWebServiceMovimenti = aspNetBaseUrl + ConstantsNet.WS_LETTURA_MOVIMENTI;
            this.UrlAutorizzazioniMercatiService = new Uri(aspNetBaseUrl + ConstantsNet.WS_AUTORIZZAZIONI);
            this.UrlServizioTares = aspNetBaseUrl + ConstantsNet.WS_TARES_BARI;
            this.UrlServizioConfigBari = aspNetBaseUrl + ConstantsNet.WS_CONFIG_BARI;

            this.UrlWebServiceBookmarks = aspNetBaseUrl + ConstantsNet.WS_BOOKMARKS;
            this.UrlWsModulisticaFrontoffice = aspNetBaseUrl + ConstantsNet.WS_MODULISTICA;
            this.UrlServizioEntiTerzi = aspNetBaseUrl + ConstantsNet.WS_SCRIVANIA_ENTI_TERZI;
            this.UrlServizioAccessoAttiSiena = aspNetBaseUrl + ConstantsNet.WS_ACCESSO_ATTI;
            this.UrlWsComuniService = aspNetBaseUrl + ConstantsNet.WS_DATI_COMUNI_SERVICE;
            this.UrlWsNodoPagamenti = aspNetBaseUrl + ConstantsNet.WS_NODO_PAGAMENTI;
            this.UrlWsUrlAccessoConsoleService = aspNetBaseUrl + ConstantsNet.WS_URL_ACCESSO_CONSOLE;
            this.UrlWsEndoFrontoffice = aspNetBaseUrl + ConstantsNet.WS_URL_ENDO_FRONTOFFICE;
            this.UrlWsEndoprocedimenti = aspNetBaseUrl + ConstantsNet.WS_URL_ENDO;
            this.UrlWsInterventi = aspNetBaseUrl + ConstantsNet.WS_URL_INTERVENTI;
            this.UrlServizioCommissioni = aspNetBaseUrl + ConstantsNet.WS_URL_COMMISSIONI;
            this.UrlServizioVotazioniCommissione = aspNetBaseUrl + ConstantsNet.WS_URL_VOTAZIONE_COMMISSIONE;
            this.UrlServizioPINCommissioni = aspNetBaseUrl + ConstantsNet.WS_URL_PIN_COMMISSIONE;
            this.UrlQuestionarioSoddisfazione = aspNetBaseUrl + ConstantsNet.WS_URL_QUESTIONARIO_SODDISFAZIONE;
            this.UrlOneriService = aspNetBaseUrl + ConstantsNet.WS_URL_ONERI;

            // servizi java
            this.UrlServizioDownloadDocumentiZIP = String.IsNullOrEmpty(apiBackendUrl) ? null : apiBackendUrl + Constants.WS_SCARICA_ZIP_PRATICA;
            this.UrlAlboPretorioService = javaBaseUrl + Constants.WS_ALBO_PRETORIO;
            this.UrlOggettiService = javaBaseUrl + Constants.WS_OGGETTI_SERVICE;
            this.UrlGenerazioneQrCode = javaBaseUrl + Constants.WS_CREAZIONE_QRCODE;
            this.UrlServizioAutorizzazioniTransiti = javaBaseUrl + Constants.WS_AUTORIZZAZIONI_TRANSITI;
            this.UrlCreazioneAnagrafeService = javaBaseUrl + Constants.WS_CREAZIONE_ANAGRAFE;

            var overrideAnagrafeService = ConfigurationManager.AppSettings["overrideAnagrafeServiceUrl"];
            if (!String.IsNullOrEmpty(overrideAnagrafeService))
                this.UrlCreazioneAnagrafeService = overrideAnagrafeService;

            this.AspNetBaseUrl = aspNetBaseUrl;
        }

    }
}
