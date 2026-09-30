using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using System.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators
{
    public abstract class ServiceCreatorBase<WsClientType> where WsClientType : class, IDisposable, ICommunicationObject
    {
        private readonly IBindingFactory _bindingFactory;
        private readonly ILog _log;

        protected ServiceCreatorBase(ILog logger, IBindingFactory bindingFactory)
        {
            this._log = logger;
            this._bindingFactory = bindingFactory;
        }

        protected virtual string GetBindingName() => "defaultHttpBinding";

        public abstract string GetEndpointUrl();

        protected abstract WsClientType CreateClient(EndpointAddress endpoint, BasicHttpBinding binding);

        public ServiceInstance<WsClientType> CreateClient()
        {
            var bindingName = this.GetBindingName();
            var address = new EndpointAddress(this.GetEndpointUrl());
            var binding = this._bindingFactory.CreateAndConfigure(bindingName);
            this._log.Debug($"Inizializzazione del web service all'endpoint {this.GetEndpointUrl()} utilizzando il binding {bindingName}");
            if (string.Equals(address.Uri.Scheme, "HTTPS", StringComparison.OrdinalIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            var ws = this.CreateClient(address, binding);

            return new ServiceInstance<WsClientType>(ws);
        }

        public T Call<T>(Func<WsClientType, T> callback)
        {
            return this.Call((ws) => callback(ws.Service));
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
                    return callback(ws);
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore nella chiamata al web service {typeof(WsClientType).Name} utilizzando il binding {this.GetBindingName()} e l'endpoint {this.GetEndpointUrl()} : {ex}");

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
                    this._log.Error($"Errore nella chiamata al web service {typeof(WsClientType)} utilizzando il binding {this.GetBindingName()} e l'endpoint {this.GetEndpointUrl()} : {ex}");

                    throw;
                }
            }
        }
    }
}
