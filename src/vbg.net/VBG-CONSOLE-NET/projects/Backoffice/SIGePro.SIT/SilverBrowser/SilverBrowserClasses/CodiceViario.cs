using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.SilverBrowser.SilverBrowserClasses
{
    public class CodiceViario
    {
        Int64 _valoreIntero = 0;

        public CodiceViario(string codiceViario)
        {
            if (!Int64.TryParse(codiceViario, out this._valoreIntero))
            {
                this._valoreIntero = ElaboraStringa(codiceViario);
            }
        }

        private long ElaboraStringa(string codiceViario)
        {
            codiceViario = codiceViario.Substring(4).TrimStart('0');

            return Int64.Parse(codiceViario);
        }

        public override string ToString()
        {
            return this._valoreIntero.ToString();
        }
    }
}
