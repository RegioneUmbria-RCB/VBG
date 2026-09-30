using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Configuration
{
    public class StaticUrlSecurityConfiguration : IUrlSecurityConfiguration
    {
        public bool IsEnabled { get; private set; }
        public IEnumerable<string> Blacklist { get; private set; }
        public IEnumerable<string> EndpointDaIgnorare { get; private set; }

        public StaticUrlSecurityConfiguration(bool isEnabled, IEnumerable<string> blacklist, IEnumerable<string> endpoints)
        {
            IsEnabled = isEnabled;
            Blacklist = blacklist;
            EndpointDaIgnorare = endpoints;
        }
    }
}
