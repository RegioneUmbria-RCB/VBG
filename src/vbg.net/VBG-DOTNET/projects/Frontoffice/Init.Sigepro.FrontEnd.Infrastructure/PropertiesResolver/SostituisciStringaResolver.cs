using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace Init.Sigepro.FrontEnd.Infrastructure.PropertiesResolver
{
    public class SostituisciStringaResolver
    {
        private string _pattern { get => "@[A-Za-z0-9\\.]+"; }
        private string _testoDaSostituire { get; set; }

        public SostituisciStringaResolver( string testoDaSostituire )
        {
            _testoDaSostituire = testoDaSostituire;
        }

        public string Risolvi( object oggettoValorizzato )
        {
            string retVal = this._testoDaSostituire;

            if(oggettoValorizzato != null && !String.IsNullOrEmpty(this._testoDaSostituire) )
            {

                foreach (Match variabile in Regex.Matches(this._testoDaSostituire, this._pattern))
                {
                    var oggettoDaVerificare = oggettoValorizzato;

                    if (variabile.Success && variabile.Groups.Count > 0)
                    {
                        var properties = variabile.Groups[0].Value.Substring(1).Split('.');

                        foreach (var p in properties)
                        {
                            oggettoDaVerificare = oggettoDaVerificare.GetType().GetProperty(p).GetValue(oggettoDaVerificare,null);
                        }

                        retVal = retVal.Replace(variabile.Value, oggettoDaVerificare.ToString());

                    }
                }
            }

            return retVal;
        }
    }
}
