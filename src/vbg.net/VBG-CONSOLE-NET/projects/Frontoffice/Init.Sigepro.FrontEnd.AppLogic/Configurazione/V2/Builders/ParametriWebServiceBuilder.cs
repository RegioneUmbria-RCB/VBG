//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;
//using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ConfigurazioneSigeproSecurity;
//using System.Configuration;

//namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
//{
//    public class ParametriWebServiceBuilder : IBuilder<ParametriWebServices>
//    {
//        private static class Constants
//        {
//            public const string WS_AREA_RISERVATA = "/WebServices/WsAreaRiservata/AreaRiservataService.asmx";
//            public const string WS_ISTANZE = "/WebServices/WsSIGePro/Istanze.asmx";
//            public const string WS_ALBO_PRETORIO = "/services/alboPretorioWSDL.wsdl";
//            public const string WS_OGGETTI_SERVICE = "/services/";
//            public const string WS_CREAZIONE_ANAGRAFE = "/services/anagrafeWS.wsdl";
//        }

//        #region IBuilder<ParametriWebServices> Members

//        Dictionary<string,string> _cacheParametri;

//        public ParametriWebServices Build()
//        {
//            _cacheParametri = ApplicationInfoCache.Get;

//            var sigeproAspNetBaseUrl = GetValoreCache(ApplicationInfoCache.Constants.WSHOSTURL_ASPNET);

//            var overrideSettings = ConfigurationManager.AppSettings["overrideAspNetBaseUrl"];

//            if (!string.IsNullOrEmpty(overrideSettings))
//                sigeproAspNetBaseUrl = overrideSettings;

//            var urlAreaRiservataService = sigeproAspNetBaseUrl + Constants.WS_AREA_RISERVATA;
//            var urlIstanzeService = sigeproAspNetBaseUrl + Constants.WS_ISTANZE;

//            var urlConversioneFileService = GetValoreCache(ApplicationInfoCache.Constants.WSHOSTURL_FILECONVERTER);
//            var urlVerificaFirmaService = GetValoreCache(ApplicationInfoCache.Constants.WSHOSTURL_FIRMADIGITALE);


//            var urlAlboPretorioService	= GetValoreCache(ApplicationInfoCache.Constants.WSHOSTURL_JAVA) + Constants.WS_ALBO_PRETORIO;
//            var urlOggettiService		= GetValoreCache(ApplicationInfoCache.Constants.WSHOSTURL_JAVA) + Constants.WS_OGGETTI_SERVICE;

//            var overrideAnagrafeService = ConfigurationManager.AppSettings["overrideAnagrafeServiceUrl"];

//            var urlCreazioneAnagrafeService = GetValoreCache(ApplicationInfoCache.Constants.WSHOSTURL_JAVA) + Constants.WS_CREAZIONE_ANAGRAFE;

//            if (!String.IsNullOrEmpty(overrideAnagrafeService))
//                urlCreazioneAnagrafeService = overrideAnagrafeService;

//            return new ParametriWebServices(urlAreaRiservataService, urlIstanzeService, urlAlboPretorioService,
//                                             urlCreazioneAnagrafeService, urlConversioneFileService, urlVerificaFirmaService,
//                                             urlOggettiService);
//        }

//        #endregion

//        private string GetValoreCache(string nomeParametro)
//        {
//            if (!_cacheParametri.ContainsKey(nomeParametro))
//                throw new ConfigurationErrorsException("IBCSECURITY non contiene il parametro " + nomeParametro);

//            var valore = _cacheParametri[nomeParametro];

//            if(String.IsNullOrEmpty(valore))
//                throw new ConfigurationErrorsException("IBCSECURITY non ha un valore per il parametro " + nomeParametro);

//            return valore;
//        }
//    }
//}
