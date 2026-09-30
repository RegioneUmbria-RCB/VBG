using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti;
using log4net;
using System.Collections.Generic;
using System.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg
{
    public class VbgAccessoAttiProxy : ServiceCreatorBase<WsAccessoAttiServiceClient>, IVbgAccessoAttiProxy
    {
        private readonly ISoftwareResolver _softwareResolver;
        private readonly ILog _log = LogManager.GetLogger(typeof(VbgAccessoAttiProxy));
        public VbgAccessoAttiProxy(IConfigurazione<ParametriSigeproSecurity> cfg, ISoftwareResolver softwareResolver, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
            this._softwareResolver = softwareResolver;
        }

        public IEnumerable<PraticaAccessoAtti> GetListaPratiche(int codiceAnagrafe)
        {
            return this.Call(ws =>
            {
                this._log.Info($"Richiesta lista atti accessibili dall'anagrafica {codiceAnagrafe}");
                return ws.Service.GetListaAtti(ws.Token, codiceAnagrafe, this._softwareResolver.Software);
            });
        }

        public void LogAccessoPratica(int codiceAnagrafe, int idAccessoAtti, string uuidIstanza)
        {
            this.CallVoid(ws =>
            {
                this._log.Info($"Log accesso atti per la pratica con idAccessoAtti: {idAccessoAtti}, codiceAnagrafe: {codiceAnagrafe}, uuidIstanza: {uuidIstanza}");
                ws.Service.LogAccessoAtti(ws.Token, idAccessoAtti, codiceAnagrafe, uuidIstanza);
            });
        }

        public int GetLivelloAccessoDocumenti(int idAccessoAtti, string uuidIstanza)
        {
            return this.Call(ws =>
            {
                this._log.Debug($"Lettura livello di accesso atti per la pratica con idAccessoAtti: {idAccessoAtti}, codiceIStanza: {uuidIstanza}");
                return ws.Service.GetLivelloAccessoDocumenti(ws.Token, idAccessoAtti, uuidIstanza);
            });
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlServizioAccessoAtti;
        }

        protected override WsAccessoAttiServiceClient CreateClient(EndpointAddress address, BasicHttpBinding binding)
        {
            return new WsAccessoAttiServiceClient(binding, address);
        }
    }
}