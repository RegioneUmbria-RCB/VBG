using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public class DownloadDomandaZIPService
    {
        private readonly IConfigurazione<ParametriSigeproSecurity> _config;
        private readonly IAliasResolver _aliasResolver;

        public DownloadDomandaZIPService(IConfigurazione<ParametriSigeproSecurity> config, IAliasResolver aliasResolver )
        {
            this._config = config;
            this._aliasResolver = aliasResolver;

            //this._url = @"http://10.10.45.64:8080/api-backend/services/api-rest/pratiche/scarica-zip-pratica/E256/9EA0F5FEE9A04B4DACDA2E8B1F29001C";
        }

        public BinaryFile RecuperaZIPDaUUIDDomanda( string uuid )
        {
            
            if (String.IsNullOrEmpty(uuid))
                throw new InvalidOperationException("Non è possibile invocare il servizio RecuperaZIPDaUUIDDomanda senza passare un UUID valorizzato");

            using (var wc = new WebClient())
            {
                var apiUrl = this._config.Parametri.UrlServizioDownloadDocumentiZIP;
                var alias = this._aliasResolver.AliasComune;

                var url = $"{apiUrl}/{alias}/{uuid}";

                var fileContent = wc.DownloadData(url);

                var nomeFile = $"{uuid}.zip";

                return new BinaryFile(nomeFile, "application/zip", fileContent);
            }
        }

        public bool ServizioConfigurato()
        {
            return !String.IsNullOrEmpty(this._config.Parametri.UrlServizioDownloadDocumentiZIP); 
        }
    }
}
