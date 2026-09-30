using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Runtime
{
    public class StringheEsadecimaliValidator : ParameterValidatorBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(StringheEsadecimaliValidator));

        private static class Constants
        {
            public const string Regex = @"\\x[0-9a-f]{2}";
        }
        
        protected override bool CheckRulesInternal(string paramKey, string paramValue)
        {
            var brokenRule = new Regex(Constants.Regex).IsMatch(paramValue);

            if (brokenRule)
            {
                this._log.Error($"Il valore del parametro {paramKey} ({paramValue}) è un match dell'espressione regolare {Constants.Regex}");

                return false;
            }

            return true;
        }
    }
}
