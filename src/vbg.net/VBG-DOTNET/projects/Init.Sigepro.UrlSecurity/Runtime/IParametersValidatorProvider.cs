using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Runtime
{
    public interface IParametersValidatorProvider
    {
        IEnumerable<IParameterValidator> GetValidators();
    }
}
