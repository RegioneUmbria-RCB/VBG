using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.ServiceModel;
using log4net;
using System;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
    public abstract class ServiceCreatorWithConfig<WsClientType, ConfigurationType>
            where WsClientType : IDisposable, ICommunicationObject where ConfigurationType : IParametriConfigurazione
    {
        private readonly IConfigurazione<ConfigurationType> _cfg;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IAliasResolver _aliasResolver;
        private readonly IBindingFactory _bindingFactory;
        private readonly ILog _log = LogManager.GetLogger(typeof(ServiceCreatorWithConfig<WsClientType, ConfigurationType>));
        protected ServiceCreatorWithConfig(IConfigurazione<ConfigurationType> cfg, ITokenApplicazioneService tokenApplicazioneService, IAliasResolver aliasResolver, IBindingFactory bindingFactory)
        {
            this._cfg = cfg;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._aliasResolver = aliasResolver;
            this._bindingFactory = bindingFactory;
        }

        protected string EndpointUrl => this.GetEndpointUrl(this._cfg.Parametri);

        /// <summary>
        /// Nome del binding da utilizzare per istanziare il web service. 
        /// Se non viene overridato nelle classi derivate utilizza areaRiservataServiceBinding
        /// </summary>
        /// <returns></returns>
        protected virtual string GetBindingName() => "areaRiservataServiceBinding";
        /// <summary>
        /// Restituisce l'url del web service che dovrà essere istanziato
        /// </summary>
        /// <param name = "config"></param>
        /// <returns></returns>
        protected abstract string GetEndpointUrl(ConfigurationType config);
        /// <summary>
        /// Istanzia un'implementazione concreta del web service utilizzando l'endpoint e il binding passati
        /// </summary>
        /// <param name = "address"></param>
        /// <param name = "binding"></param>
        /// <returns></returns>
        protected abstract WsClientType CreateClient(EndpointAddress endpoint, BasicHttpBinding binding);
        /// <summary>
        /// Crea un istanza del web service specificato nel tipo
        /// </summary>
        /// <returns>Istanza del web service e del token applicativo da utilizzare per invocarlo</returns>
        public ServiceInstance<WsClientType> CreateClient()
        {
            var bindingName = this.GetBindingName();
            var address = new EndpointAddress(this.EndpointUrl);
            var binding = this._bindingFactory.CreateAndConfigure(bindingName);
            // binding.MaxReceivedMessageSize = Int32.MaxValue;
            this._log.Debug($"Inizializzazione del web service all'endpoint {this.EndpointUrl} utilizzando il binding {bindingName}");
            if (string.Equals(address.Uri.Scheme, "HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            var ws = this.CreateClient(address, binding);
            var token = this._tokenApplicazioneService.GetToken(this.ResolveServiceAlias());
            return new ServiceInstance<WsClientType>(ws, token);
        }

        protected virtual string ResolveServiceAlias()
        {
            return this._aliasResolver.AliasComune;
        }

        public T Call<T>(Func<WsClientType, string, T> callback)
        {
            return this.Call((ws) => callback(ws.Service, ws.Token));
        }

        /// <summary>
        /// Istanza un oggetto che rappresenta il web service e utilizza la callback passata per 
        /// invocare un metodo che restituisce risultati
        /// </summary>
        /// <param name = "callback">Callback da invocare alla creazione del servizio web referenziato</param>
        public T Call<T>(Func<ServiceInstance<WsClientType>, T> callback)
        {
            using (var ws = this.CreateClient())
            {
                try
                {
                    T value = callback(ws);

                    return value;
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore nella chiamata al web service {typeof(WsClientType).Name} utilizzando il binding {this.GetBindingName()} e l'endpoint {this.EndpointUrl} : {ex}");

                    throw;
                }
            }
        }

        /// <summary>
        /// Istanza un oggetto che rappresenta il web service e utilizza la callback passata per 
        /// invocare un metodo che non restituisce risultati
        /// </summary>
        /// <param name = "callback">Callback da invocare alla creazione del servizio web referenziato</param>
        public void CallVoid(Action<ServiceInstance<WsClientType>> callback)
        {
            using (var ws = this.CreateClient())
            {
                try
                {
                    callback(ws);
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore nella chiamata al web service {typeof(WsClientType)} utilizzando il binding {this.GetBindingName()} e l'endpoint {this.EndpointUrl} : {ex}");

                    throw;
                }
            }
        }
    }

    public abstract class ServiceCreatorBase<WsClientType> : ServiceCreatorWithConfig<WsClientType, ParametriSigeproSecurity> where WsClientType : IDisposable, ICommunicationObject
    {
        protected ServiceCreatorBase(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IAliasResolver aliasResolver, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, aliasResolver, bindingFactory)
        {
        }
    }
}
