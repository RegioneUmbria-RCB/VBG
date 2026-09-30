using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Authentication.SoftwareAttivi
{
    public class SoftwareAttiviList
    {
        private readonly string[] _softwareBackoffice;
        private readonly string[] _softwareFrontoffice;

        public IEnumerable<string> SoftwareAttiviBackoffice => this._softwareBackoffice;

        public IEnumerable<string> SoftwareAttiviFrontoffice => this._softwareFrontoffice;

        public string StringaSoftwareAttiviBackoffice
        {
            get { return String.Join(",", this.SoftwareAttiviBackoffice.ToArray()); }
        }

        public string StringaSoftwareAttiviFrontoffice
        {
            get { return String.Join(",", this.SoftwareAttiviFrontoffice.ToArray()); }
        }

        internal SoftwareAttiviList(IEnumerable<SoftwareAttivo> l)
        {
            this._softwareFrontoffice = l.Where(sa => sa.AttivoFo.GetValueOrDefault(0) == 1).Select(x => x.FkSoftware).ToArray();
            this._softwareBackoffice = l.Select(x => x.FkSoftware).ToArray();
        }
    }
}