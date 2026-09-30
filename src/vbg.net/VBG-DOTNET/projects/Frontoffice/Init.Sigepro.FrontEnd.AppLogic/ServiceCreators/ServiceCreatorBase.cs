using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using log4net;
using System;
using System.Diagnostics;
using System.ServiceModel;
using System.Threading.Tasks;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
    public abstract class ServiceCreatorWithConfig<WsClientType, ConfigurationType>
        where WsClientType : ICommunicationObject
#if IGNORE
        ,IDisposable 
#endif

        where ConfigurationType : IParametriConfigurazione
    {
        private readonly IConfigurazione<ConfigurationType> _cfg;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IBindingFactory _bindingFactory;
        private readonly ILog _log = LogManager.GetLogger(typeof(ServiceCreatorWithConfig<WsClientType, ConfigurationType>));
        protected ServiceCreatorWithConfig(IConfigurazione<ConfigurationType> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory)
        {
            this._cfg = cfg;
            this._tokenApplicazioneService = tokenApplicazioneService;
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
            // NONE! Va letto dalla configurazione!
            // binding.MaxBufferSize = 1024000;
            // binding.MaxReceivedMessageSize = 1024000;
            Debug.WriteLine($"Inizializzazione del web service all'endpoint {this.EndpointUrl} utilizzando il binding {bindingName}");
            if (string.Equals(address.Uri.Scheme, "HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            var ws = this.CreateClient(address, binding);
            var token = this._tokenApplicazioneService.GetToken();
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
                    var value = callback(ws);

                    return value;
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore nella chiamata al web service {typeof(WsClientType).Name} utilizzando il binding {this.GetBindingName()} e l'endpoint {this.EndpointUrl} : {ex}");
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
                    var value = await callback(ws);

                    return value;
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore nella chiamata al web service {typeof(WsClientType).Name} utilizzando il binding {this.GetBindingName()} e l'endpoint {this.EndpointUrl} : {ex}");
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
                    this._log.Error($"Errore nella chiamata al web service {typeof(WsClientType)} utilizzando il binding {this.GetBindingName()} e l'endpoint {this.EndpointUrl} : {ex}");

                    throw;
                }
            }
        }
    }

    public abstract class ServiceCreatorBase<WsClientType> : ServiceCreatorWithConfig<WsClientType, ParametriSigeproSecurity>
        where WsClientType : ICommunicationObject
#if IGNORE
         ,IDisposable
#endif
    {
        protected ServiceCreatorBase(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }
    }
}