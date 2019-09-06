using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.BookmarksWebService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks
{
    public class BookmarksServiceClientCreator
    {
        IConfigurazione<ParametriSigeproSecurity> _config;
		ITokenApplicazioneService _tokenApplicazioneService;

        public BookmarksServiceClientCreator(IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService)
		{
			Condition.Requires(config, "config").IsNotNull();
			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();
			
			this._config = config;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}

        public ServiceInstance<BookmarksServiceSoapClient> CreateClient(string aliasComune)
		{
            ILog log = LogManager.GetLogger(typeof(BookmarksServiceClientCreator));

            log.DebugFormat("Inizializzazione del web service di gestione bookmarks all'endpoint {0} utilizzando il binding areaRiservataServiceBinding", this._config.Parametri.UrlWebServiceBookmarks);


			var endPoint = new EndpointAddress(this._config.Parametri.UrlWebServiceBookmarks);

			var binding = new BasicHttpBinding("areaRiservataServiceBinding");
            var ws = new BookmarksServiceSoapClient(binding, endPoint);

			var token = this._tokenApplicazioneService.GetToken(aliasComune);

            return new ServiceInstance<BookmarksServiceSoapClient>(ws, token);
		}
    }
}
