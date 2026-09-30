using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Configuration;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneIstanze.Documenti
{
    public class DocumentiIstanzaServiceFactory
    {
        ILog _log = LogManager.GetLogger(typeof(DocumentiIstanzaServiceFactory));
        private readonly string _baseApiUrl;

        public DocumentiIstanzaServiceFactory()
        {
            this._baseApiUrl = ParametriConfigurazione.Get.WsHostUrlApiBackend;
        }

        public IDocumentiIstanzaService CreateService(AuthenticationInfo authenticationInfo)
        {
            var apiUrl = new DocumentiIstanzaRestUrl(this._baseApiUrl);

            this._log.Debug($"Creazione del servizio DocumentiIstanzaService verso l'endpoint {this._baseApiUrl}");

            var uploader = new WebApiDocumentiIstanzaUploader(apiUrl, authenticationInfo);
            return new DocumentiIstanzaService(uploader);
        }
    }
}
