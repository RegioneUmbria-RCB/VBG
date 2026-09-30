using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR
{
    internal class Querystring
    {
        string _cf;
        string _nome;
        string _cognome;
        string _sesso;
        string _time;

        internal Querystring(string cf, string nome, string cognome, string sesso, DateTime? time = null)
        {
            this._cf = cf;
            this._nome = nome;
            this._cognome = cognome;
            this._sesso = sesso;
            this._time = time.HasValue ? time.Value.ToString("yyyyMMddHHmmssffff") : DateTime.Now.ToString("yyyyMMddHHmmssffff");
        }


        internal string SignWithCertificate(QuerystringCertificate certificato)
        {
            var signedPart = certificato.Sign(this);
            return this.ToString() + "&tokenPartnerApp=" + signedPart;
        }

        public override string ToString()
        {
            var querystringFmtString = "cf={0}&nome={1}&cognome={2}&sesso={3}&time={4}";
            var qs = String.Format(querystringFmtString, this._cf, this._nome, this._cognome, this._sesso, this._time);

            return qs;
        }
    }
}
