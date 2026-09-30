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
    public class BlacklistValidator : ParameterValidatorBase
    {
        private readonly IUrlSecurityConfiguration _configuration;
        private readonly ILog _log = LogManager.GetLogger(typeof(BlacklistValidator)); 

        internal BlacklistValidator(IUrlSecurityConfiguration configuration)
        {
            _configuration = configuration;
        }

        protected override bool CheckRulesInternal(string paramKey, string paramValue)
        {
            var brokenRule = this._configuration.Blacklist.Where(x => x.Length <= paramValue.Length).Where(x => paramValue.IndexOf(x) > -1).FirstOrDefault();

            if (!String.IsNullOrEmpty(brokenRule))
            {
                this._log.Error($"Il valore del parametro {paramKey} ({paramValue}) è contrario alla regola di blacklist {brokenRule}");

                return false;
            }

            return true;
        }
    }
}
