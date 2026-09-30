using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Configuration
{
    interface IUrlSecurityConfiguration
    {
        bool IsEnabled { get; }
        IEnumerable<string> Blacklist { get; }
        IEnumerable<string> EndpointDaIgnorare{ get; }
    }
}
