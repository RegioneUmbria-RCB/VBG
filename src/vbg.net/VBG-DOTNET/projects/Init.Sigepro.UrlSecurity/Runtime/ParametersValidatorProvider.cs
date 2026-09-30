using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Runtime
{
    public class ParametersValidatorProvider : IParametersValidatorProvider
    {
        private readonly BlacklistValidator _blacklistValidator;
        private readonly EntitaXmlValidator _entitaXmlValidator;
        private readonly StringheEsadecimaliValidator _stringheEsadecimaliValidator;

        public ParametersValidatorProvider(BlacklistValidator blacklistValidator, EntitaXmlValidator entitaXmlValidator, StringheEsadecimaliValidator stringheEsadecimaliValidator)
        {
            _blacklistValidator = blacklistValidator;
            _entitaXmlValidator = entitaXmlValidator;
            _stringheEsadecimaliValidator = stringheEsadecimaliValidator;
        }
        public IEnumerable<IParameterValidator> GetValidators()
        {
            return new IParameterValidator[]{
                _stringheEsadecimaliValidator,
                _entitaXmlValidator,
                _blacklistValidator,
            };
        }
    }
}
