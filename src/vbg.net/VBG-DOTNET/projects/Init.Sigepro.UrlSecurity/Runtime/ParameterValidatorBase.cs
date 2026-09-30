using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Runtime
{
    public abstract class ParameterValidatorBase : IParameterValidator
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ParameterValidatorBase));

        public void Validate(IEnumerable<ParametroDaValidare> collection)
        {
            foreach (var item in collection)
            {
                var key = item.Nome;
                var val = item.Valore;

                var isValid = this.CheckRulesInternal(key, val);

                if (!isValid)
                {
                    this._log.Error($"Il valore del parametro {key} ({val}) è stato bloccato da una o più regole");
                    throw new RequestValidationException($"La richiesta corrente contiene caratteri non validi");
                }
            }
        }

        protected abstract bool CheckRulesInternal(string paramKey, string paramValue);
    }
}
