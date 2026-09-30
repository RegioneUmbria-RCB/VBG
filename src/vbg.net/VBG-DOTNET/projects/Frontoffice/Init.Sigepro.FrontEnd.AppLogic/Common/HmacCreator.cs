using System;
using System.Security.Cryptography;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
    public class HmacCreator
    {
        private readonly string _secret = "4r34r1s3rv4t4";

        public HmacCreator(string secret = "")
        {
            if (!String.IsNullOrEmpty(secret))
            {
                this._secret = secret;
            }
        }

        public string Encode(string plaintext)
        {
            var encoding = new System.Text.ASCIIEncoding();
            var keyByte = encoding.GetBytes(this._secret);
            var messageBytes = encoding.GetBytes(plaintext);

            using (var hmacsha256 = new HMACSHA256(keyByte))
            {
                var hashmessage = hmacsha256.ComputeHash(messageBytes);
                return Convert.ToBase64String(hashmessage);
            }
        }

    }
}
