
using log4net;
using System;
using System.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.SIGePro.Manager.Utils
{
    public abstract class WebServiceProxyBase<TService> where TService : IDisposable, ICommunicationObject
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WebServiceProxyBase<TService>));
        private readonly IBindingFactory _bindingFactory;

        protected WebServiceProxyBase(IBindingFactory bindingFactory)
        {
            this._bindingFactory = bindingFactory;
        }

        protected abstract string WebServiceUrl { get; }
        protected virtual string BindingName => "defaultHttpBinding";
        protected abstract TService CreateService(BasicHttpBinding binding, EndpointAddress endpoint);

        protected TReturned CallService<TReturned>(Func<TService, TReturned> callback)
        {
            var serviceUrl = this.WebServiceUrl;
            var bindingName = this.BindingName;
            /*
            if (!String.IsNullOrEmpty(this._overrideWebServiceUrl))
            {
                serviceUrl = this._overrideWebServiceUrl;
            }
            */
            var endpoint = new EndpointAddress(serviceUrl);
            var binding = this._bindingFactory.CreateAndConfigure(bindingName);

            if (string.Equals(serviceUrl.Substring(0, 5), "https", StringComparison.CurrentCultureIgnoreCase))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            using (var ws = this.CreateService(binding, endpoint))
            {
                try
                {
                    return callback(ws);
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore durante la creazione del web service con url {serviceUrl} e binding {bindingName}: {ex}");

                    ws.Abort();

                    throw;
                }
            }
        }
    }
}
