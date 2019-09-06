using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR
{
    internal class QuerystringHash
    {
        Querystring _qs;

        internal QuerystringHash(Querystring qs)
        {
            this._qs = qs;
        }

        public override string ToString()
        {
            return HashToHexString(SHA256Managed.Create().ComputeHash(Encoding.UTF8.GetBytes(this._qs.ToString())));
        }

        private string HashToHexString(byte[] ba)
        {
            string hex = BitConverter.ToString(ba);
            return hex.Replace("-", "").ToLower();
        }
    }
}
