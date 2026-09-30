using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Runtime
{
    public interface IParameterValidator
    {
        void Validate(IEnumerable<ParametroDaValidare> collection);
    }
}
