using System;
using System.Collections.Generic;
using VBG.Backend.SIT.AppLogic.Data;

namespace VBG.Backend.SIT.AppLogic.Genova
{
    internal class GenovaResultSet(List<string> valori)
    {
        private readonly List<string> _valori = valori;

        internal RetSit ToRetSit()
        {
            return new RetSit(this._valori.Count > 0, this._valori);
        }

        internal GenovaResultSet SortAsNumber()
        {
            this._valori.Sort((x, y) => this.TryParseToNumber(x).CompareTo(this.TryParseToNumber(y)));
            return this;
        }

        private int TryParseToNumber(string valore)
        {
            if (String.IsNullOrEmpty(valore))
            {
                return 0;
            }
            if (Int32.TryParse(valore, out var numero))
            {
                return numero;
            }
            return 0;
        }
    }
}
