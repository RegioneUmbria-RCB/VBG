using Init.Sigepro.UrlSecurity.Configuration;
using log4net;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Runtime
{
    internal class UrlSecurityRuntime : IUrlSecurityRuntime
    {
        private readonly IUrlSecurityConfiguration _urlSecurityConfiguration;
        private readonly IUrlDecoder _urlDecoder;
        private readonly IEnumerable<IParameterValidator> _validators;
        private ILog _log = LogManager.GetLogger(typeof(UrlSecurityRuntime));

        public UrlSecurityRuntime(IUrlSecurityConfiguration urlSecurityConfiguration, IUrlDecoder urlDecoder, IParametersValidatorProvider parametersValidatorProvider)
        {
            _urlSecurityConfiguration = urlSecurityConfiguration;
            _urlDecoder = urlDecoder;

            _validators = parametersValidatorProvider.GetValidators();
        }

        public void CheckUrl(Uri currentUri, NameValueCollection parametersCollection)
        {
            if (!this._urlSecurityConfiguration.IsEnabled)
            {
                return;
            }

            try
            {
                var listaDaValidare = new List<ParametroDaValidare>();

                foreach (var key in parametersCollection.AllKeys)
                {
                    var val = new ParametroDaValidare(key, parametersCollection.Get(key), this._urlDecoder);

                    if (val.Valore.Length > 0)
                    {
                        listaDaValidare.Add(val);
                    }
                }

                foreach (var validator in this._validators)
                {
                    validator.Validate(listaDaValidare);
                }
            }
            catch (RequestValidationException ex)
            {
                this._log.Error($"Nell'url corrente sono stati trovati caratteri potenzialmente pericolosi: {ex.Message}. Url corrente: {currentUri}");

                throw;
            }

        }
    }
}
