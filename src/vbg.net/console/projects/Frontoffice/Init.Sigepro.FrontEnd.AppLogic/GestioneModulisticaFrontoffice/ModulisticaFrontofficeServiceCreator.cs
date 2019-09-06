using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsModulisticaFrontoffice;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneModulisticaFrontoffice
{
    public class ModulisticaFrontofficeServiceCreator
    {
        IConfigurazione<ParametriSigeproSecurity> _configurazioneUrl;
        ITokenApplicazioneService _tokenApplicazioneService;
        IAliasResolver _aliasResolver;

        public ModulisticaFrontofficeServiceCreator(IConfigurazione<ParametriSigeproSecurity> configurazioneUrl, ITokenApplicazioneService tokenApplicazioneService, IAliasResolver aliasResolver)
        {
            this._aliasResolver = aliasResolver;
            this._configurazioneUrl = configurazioneUrl;
            this._tokenApplicazioneService = tokenApplicazioneService;
        }

        public ServiceInstance<WsModulisticaSoapClient> CreateService()
        {
            var remoteUrl = new EndpointAddress(this._configurazioneUrl.Parametri.UrlWsModulisticaFrontoffice);
            var binding = new BasicHttpBinding("defaultServiceBinding");
            var ws = new WsModulisticaSoapClient(binding, remoteUrl);
            var token = this._tokenApplicazioneService.GetToken(this._aliasResolver.AliasComune);

            return new ServiceInstance<WsModulisticaSoapClient>(ws, token);
        }
    }
}
