using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsNodoPagamenti;
using VBG.Shared.Infrastructure.Caching;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Configurazione
{
    public class ConfigurazioneNodoPagamentiRepository : ServiceCreatorBase<WsNodoPagamentiServiceClient>, IConfigurazioneNodoPagamentiRepository
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ConfigurazioneNodoPagamentiRepository));
        private readonly ISoftwareResolver _softwareResolver;
        private readonly IContextCache _contextCache;

        public ConfigurazioneNodoPagamentiRepository(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, ISoftwareResolver softwareResolver, IBindingFactory bindingFactory, IContextCache contextCache) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
            this._softwareResolver = softwareResolver;
            this._contextCache = contextCache;
        }

        public ConfigurazioneNodoPagamenti GetConfigurazione(string codiceComune)
        {
            var key = $"configurazione.nodo.pagamenti.{codiceComune}.{this._softwareResolver.Software}";

            return this._contextCache.GetOrAdd(key, () =>
            {
                return this.Call(ws =>
                {
                    this._log.Debug($"Chiamata a GetConfigurazione del nodo pagamenti con software={this._softwareResolver.Software} e codiceComune={codiceComune}");
                    var result = ws.Service.GetConfigurazione(ws.Token, this._softwareResolver.Software, codiceComune);
                    if (this._log.IsDebugEnabled)
                    {
                        this._log.Debug($"Esito della chiamata {result.ToXmlString()}");
                    }

                    return new ConfigurazioneNodoPagamenti(codiceComune, result.NodoPagamentiAttivo, result.PagoDopoAttivo, result.UrlWs, result.CodiceFiscaleEnteCreditore, result.UrlRitorno, result.IdModalitaPagamento, result.SoggettoPendenza, result.PagoDopoGGScadenza);
                });
            });
        }

        protected override string GetBindingName() => "defaultServiceBinding";
        protected override WsNodoPagamentiServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsNodoPagamentiServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlWsNodoPagamenti;
        }
    }
}