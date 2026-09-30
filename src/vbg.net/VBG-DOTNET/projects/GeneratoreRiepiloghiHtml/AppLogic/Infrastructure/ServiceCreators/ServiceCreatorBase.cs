using GeneratoreRiepiloghiHtml.AppLogic.Authentication;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.Configuration;
using System.Diagnostics;
using System.ServiceModel;

namespace GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.ServiceCreators
{
    public abstract class ServiceCreatorWithConfig<WsClientType>
            where WsClientType : IDisposable, ICommunicationObject
    {
        private readonly ParametriSecurityService _cfgService;
        private readonly ITokenResolver _tokenResolver;
        private readonly BindingFactory _bindingFactory;
        private readonly ILogger _log;

        protected ServiceCreatorWithConfig(ParametriSecurityService cfgService, ITokenResolver tokenResolver, BindingFactory bindingFactory, ILogger logger)
        {
            this._cfgService = cfgService;
            this._tokenResolver = tokenResolver;
            this._bindingFactory = bindingFactory;
            this._log = logger;
        }

        protected string EndpointUrl => this.GetEndpointUrl(this._cfgService.GetEndpointUrl());

        /// <summary>
        /// Nome del binding da utilizzare per istanziare il web service. 
        /// Se non viene overridato nelle classi derivate utilizza areaRiservataServiceBinding
        /// </summary>
        /// <returns></returns>
        protected virtual string GetBindingName() => "riepiloghiServiceBinding";

        protected virtual void ConfigureBinding(BasicHttpBinding binding) { }

        /// <summary>
        /// Restituisce l'url del web service che dovrà essere istanziato
        /// </summary>
        /// <param name = "config"></param>
        /// <returns></returns>
        protected abstract string GetEndpointUrl(ConfigurazioneEndpointUrl config);

        /// <summary>
        /// Istanzia un'implementazione concreta del web service utilizzando l'endpoint e il binding passati
        /// </summary>
        protected abstract WsClientType CreateClient(EndpointAddress endpoint, BasicHttpBinding binding);

        /// <summary>
        /// Crea un istanza del web service specificato nel tipo
        /// </summary>
        /// <returns>Istanza del web service e del token applicativo da utilizzare per invocarlo</returns>
        private ServiceInstance<WsClientType> CreateClient()
        {
            var bindingName = this.GetBindingName();
            var address = new EndpointAddress(this.EndpointUrl);
            var binding = this._bindingFactory.CreateAndConfigure(bindingName);

            this.ConfigureBinding(binding);

            Debug.WriteLine($"Inizializzazione del web service all'endpoint {this.EndpointUrl} utilizzando il binding {bindingName}");

            if (string.Equals(address.Uri.Scheme, "HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            var ws = this.CreateClient(address, binding);
            var token = this._tokenResolver.Token;
            return new ServiceInstance<WsClientType>(ws, token);
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
                    this._log.LogError("Errore nella chiamata al web service {@serviceName} utilizzando il binding {@bindingName} e l'endpoint {@url} : {@exception}",
                        typeof(WsClientType).Name,
                        this.GetBindingName(),
                        this.EndpointUrl,
                        ex);

                    throw;
                }
                finally
                {
                    if (ws.Service.State == CommunicationState.Faulted)
                    {
                        ws.Service.Abort();
                    }

                    if (ws.Service.State != CommunicationState.Closed)
                    {
                        ws.Service.Close();
                    }
                }

            }
        }

        /// <summary>
        /// Istanza un oggetto che rappresenta il web service e utilizza la callback passata per 
        /// invocare un metodo che restituisce risultati
        /// </summary>
        /// <param name = "callback">Callback da invocare alla creazione del servizio web referenziato</param>
        public async Task<T> CallAsync<T>(Func<ServiceInstance<WsClientType>, Task<T>> callback)
        {
            using (var ws = this.CreateClient())
            {
                try
                {
                    T value = await callback(ws);

                    return value;
                }
                catch (Exception ex)
                {
                    this._log.LogError("Errore nella chiamata al web service {@serviceName} utilizzando il binding {@bindingName} e l'endpoint {@url} : {@exception}",
                          typeof(WsClientType).Name,
                          this.GetBindingName(),
                          this.EndpointUrl,
                          ex);
                    throw;
                }
                finally
                {
                    if (ws.Service.State == CommunicationState.Faulted)
                    {
                        ws.Service.Abort();
                    }

                    if (ws.Service.State != CommunicationState.Closed)
                    {
                        ws.Service.Close();
                    }
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
                    this._log.LogError("Errore nella chiamata al web service {@serviceName} utilizzando il binding {@bindingName} e l'endpoint {@url} : {@exception}",
                          typeof(WsClientType).Name,
                          this.GetBindingName(),
                          this.EndpointUrl,
                          ex);

                    throw;
                }
            }
        }
    }

    public abstract class ServiceCreatorBase<WsClientType> : ServiceCreatorWithConfig<WsClientType> where WsClientType : IDisposable, ICommunicationObject
    {
        protected ServiceCreatorBase(ParametriSecurityService cfgService, ITokenResolver tokenResolver, BindingFactory bindingFactory, ILogger logger) : base(cfgService, tokenResolver, bindingFactory, logger)
        {
        }
    }
}
