using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Data
{
    public class SoftwareAttiviList
    {
        public List<SoftwareAttivi> SoftwareAttivi { get; protected set; }

        public List<string> SoftwareAttiviBackoffice
        {
            get
            {
                var rVal = new List<string>();

                SoftwareAttivi.ForEach(sa => rVal.Add(sa.FkSoftware));

                return rVal;
            }
        }

        public List<string> SoftwareAttiviFrontoffice
        {
            get
            {
                var rVal = new List<string>();

                SoftwareAttivi.ForEach(sa => { if (sa.AttivoFo.GetValueOrDefault(0) == 1)rVal.Add(sa.FkSoftware); });

                return rVal;
            }
        }

        public string StringaSoftwareAttiviBackoffice
        {
            get { return String.Join(",", SoftwareAttiviBackoffice.ToArray()); }
        }

        public string StringaSoftwareAttiviFrontoffice
        {
            get { return String.Join(",", SoftwareAttiviFrontoffice.ToArray()); }
        }

        public SoftwareAttiviList(List<SoftwareAttivi> l)
        {
            SoftwareAttivi = l;
        }
    }
}
