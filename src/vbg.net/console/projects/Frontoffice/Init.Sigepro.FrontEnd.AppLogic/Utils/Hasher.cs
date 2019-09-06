using System;
using System.Security.Cryptography;

namespace Init.Sigepro.FrontEnd.AppLogic.Utils
{
	public class Hasher
	{
		public string ComputeHash(byte[] buffer)
		{
			using (var md5 = new MD5CryptoServiceProvider())
			{
				byte[] retVal = md5.ComputeHash(buffer);
				return BitConverter.ToString(retVal).Replace("-", ""); // hex string
			}
		}
	}
}
