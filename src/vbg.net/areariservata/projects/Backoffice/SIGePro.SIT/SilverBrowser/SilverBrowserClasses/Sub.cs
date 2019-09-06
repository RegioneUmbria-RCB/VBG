using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.SilverBrowser.SilverBrowserClasses
{
	public class Sub : Particella
	{
        string _sub;

		public string sub 
        {
            get { return this._sub; }
            set 
            {
                if (value == "0000")
                {
                    this._sub = "0";
                    return;
                }

                this._sub = value.TrimStart('0');
            } 
        }
	}
}
