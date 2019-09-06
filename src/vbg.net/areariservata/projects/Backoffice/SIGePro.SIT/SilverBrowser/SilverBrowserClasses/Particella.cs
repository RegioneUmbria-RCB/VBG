using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.SilverBrowser.SilverBrowserClasses
{
    public class Particella
    {
        string _foglio;
        string _numero;

        public string centerX { get; set; }
        public string centerY { get; set; }
        public string tipo { get; set; }
        public string numero
        {
            get { return this._numero; }
            set
            {

                if (value == "00000")
                {
                    this._numero = "0";
                    return;
                }

                this._numero = value.TrimStart('0');
            }
        }
        public string foglio
        {
            get { return this._foglio; }
            set
            {

                if (value == "0000")
                {
                    this._foglio = "0";
                    return;
                }

                this._foglio = value.TrimStart('0');
            }
        }
        public string sez { get; set; }
    }
}
