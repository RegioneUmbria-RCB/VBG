using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;

namespace Init.SIGePro.Manager.Utils
{
    public class Md5Utils
    {
        public static string GetMd5(string text)
        {
			var pass = Encoding.UTF8.GetBytes(text);
            return GetMd5(pass);
        }

        public static string GetMd5(byte[] pass)
        {
            var md5 = new MD5CryptoServiceProvider();
            var bytes = md5.ComputeHash(pass);

            var sb = new StringBuilder();

            for (int i = 0; i < bytes.Length; i++)
                sb.Append(bytes[i].ToString("X2"));

            return sb.ToString().ToLower();
        }
    }
}
