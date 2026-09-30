using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Security.Cryptography.X509Certificates;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR
{
    internal class QuerystringCertificate
    {
        X509Certificate2 _certificato;

        internal QuerystringCertificate(X509Certificate2 certificato)
        {
            this._certificato = certificato;
        }

        internal string Sign(Querystring qs)
        {
            var hash = new QuerystringHash(qs).ToString();
            var provider = (RSACryptoServiceProvider)this._certificato.PrivateKey;

            var signedData = provider.SignData(Encoding.UTF8.GetBytes(hash), new SHA1CryptoServiceProvider());

            return Bas64ToUrlSafeString(signedData);
        }

        private static string Bas64ToUrlSafeString(byte[] bytes)
        {
            var base64 = Convert.ToBase64String(bytes);

            base64 = base64.Replace("+", "-");
            base64 = base64.Replace("/", "_");

            return base64.TrimEnd('=');
        }
    }
}
