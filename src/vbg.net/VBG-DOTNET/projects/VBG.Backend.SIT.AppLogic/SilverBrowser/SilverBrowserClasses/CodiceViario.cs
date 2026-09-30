using System;

namespace VBG.Backend.SIT.AppLogic.SilverBrowser.SilverBrowserClasses
{
    public class CodiceViario
    {
        private readonly Int64 _valoreIntero = 0;

        public CodiceViario(string codiceViario)
        {
            if (!Int64.TryParse(codiceViario, out this._valoreIntero))
            {
                this._valoreIntero = this.ElaboraStringa(codiceViario);
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
