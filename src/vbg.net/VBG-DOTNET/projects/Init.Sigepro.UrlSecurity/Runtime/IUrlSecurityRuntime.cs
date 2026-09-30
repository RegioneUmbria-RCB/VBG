using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Runtime
{
    public interface IUrlSecurityRuntime
    {
        /// <summary>
        /// Effettua la validazione di una richiesta http ad una pagina
        /// </summary>
        /// <param name="currentUri">URI corrente</param>
        /// <param name="parametersCollection">Collection di parametri della request</param>
        void CheckUrl(Uri currentUri, NameValueCollection parametersCollection);
    }
}
