using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.UrlSecurity.Configuration
{
    internal class FileBasedSecurityConfiguration : IUrlSecurityConfiguration
    {
        public bool IsEnabled { get; private set; } = false;

        public IEnumerable<string> Blacklist { get; private set; } = new List<string>();

        public IEnumerable<string> EndpointDaIgnorare { get; private set; } = new List<string>();

        public FileBasedSecurityConfiguration(bool isEnabled, string blacklistSource, string endpointSource)
        {
            this.IsEnabled = isEnabled;

            if (!this.IsEnabled)
            {
                return;
            }

            this.Blacklist = this.ReadFromFile(blacklistSource);
            this.EndpointDaIgnorare = this.ReadFromFile(endpointSource);
        }

        private IEnumerable<string> ReadFromFile(string fileSource)
        {
            if (!File.Exists(fileSource))
            {
                throw new FileNotFoundException($"Il file di configurazione {fileSource} non esiste");
            }

            if (String.IsNullOrEmpty(fileSource))
            {
                return Enumerable.Empty<string>();
            }

            return File.ReadLines(fileSource)
                       .Where( x => !x.StartsWith("#"))
                       .Select(x => x.Trim().ToLower())     // Le blacklists sono sempre in minuscolo perché i parametri da validare saranno sempre minuscoli e url decoded
                       .Where(x => x.Length > 0)
                       .ToArray();
        }
    }
}
