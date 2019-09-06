using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.SilverBrowser.SilverBrowserClasses
{
    public class Foglio : Sezione
    {
        string _foglio;
        public string foglio
        {
            get { return this._foglio; }
            set {

                if (value == "0000")
                {
                    this._foglio = "0";
                    return;
                }

                this._foglio = value.TrimStart('0');            
            }
        }
    }
}
