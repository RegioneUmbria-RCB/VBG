using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti;
using log4net;
using System.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg.Zip
{
    public class ZipAccessoAttiProxy : ServiceCreatorBase<WsAccessoAttiServiceClient>, IZipAccessoAttiProxy
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ZipAccessoAttiProxy));

        public ZipAccessoAttiProxy(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        protected override WsAccessoAttiServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsAccessoAttiServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlServizioAccessoAtti;
        }


        public string GetNomeFileZipPerDownloadDocumenti(int idAccessoAtti, string uuidPratica)
        {
            return this.Call(ws =>
            {
                this._log.Debug($"Recupero nome zip file per la pratica con idAccessoAtti: {idAccessoAtti}, codiceIStanza: {uuidPratica}");
                return ws.Service.GetNomeFileZipPerDownloadDocumenti(ws.Token, idAccessoAtti, uuidPratica);
            });
        }
    }
}
