using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.BackendQrCodeWs;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
    public class QrCodeServiceCreator
    {
        readonly IConfigurazione<ParametriSigeproSecurity> _configurazione;
        readonly ITokenApplicazioneService _tokenApplicazioneService;
        readonly IAliasResolver _aliasResolver;

        public QrCodeServiceCreator(IConfigurazione<ParametriSigeproSecurity> configurazione, ITokenApplicazioneService tokenApplicazioneService, IAliasResolver aliasResolver)
        {
            this._configurazione = configurazione;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._aliasResolver = aliasResolver;
        }

        internal ServiceInstance<QrcodeClient> CreateService()
        {
            var endPoint = new EndpointAddress(this._configurazione.Parametri.UrlGenerazioneQrCode);
            var binding = new BasicHttpBinding("defaultServiceBinding");

            var ws = new QrcodeClient(binding, endPoint);

            var token = this._tokenApplicazioneService.GetToken(this._aliasResolver.AliasComune);

            return new ServiceInstance<QrcodeClient>(ws, token);
        }
    }
}
