using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;

namespace Init.Sigepro.FrontEnd.Bari.TARES.Crypto
{
	internal class TaresEncoded
	{
		protected string Sha256(string password)
		{
			var crypt = new SHA256Managed();
			var hash = String.Empty;
			var crypto = crypt.ComputeHash(Encoding.UTF8.GetBytes(password), 0, Encoding.UTF8.GetByteCount(password));
			foreach (byte bit in crypto)
			{
				hash += bit.ToString("x2");
			}
			return hash;
		}
	}
}
