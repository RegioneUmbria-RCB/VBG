using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
    public class HmacCreator
    {
        readonly string _secret = "4r34r1s3rv4t4";

        public HmacCreator(string secret = null)
        {
            if (!String.IsNullOrEmpty(secret))
            {
                this._secret = secret;
            }
        }

        public string Encode(string plaintext)
        {
            var encoding = new System.Text.ASCIIEncoding();
            byte[] keyByte = encoding.GetBytes(this._secret);
            byte[] messageBytes = encoding.GetBytes(plaintext);

            using (var hmacsha256 = new HMACSHA256(keyByte))
            {
                byte[] hashmessage = hmacsha256.ComputeHash(messageBytes);
                return Convert.ToBase64String(hashmessage);
            }
        }

    }
}
