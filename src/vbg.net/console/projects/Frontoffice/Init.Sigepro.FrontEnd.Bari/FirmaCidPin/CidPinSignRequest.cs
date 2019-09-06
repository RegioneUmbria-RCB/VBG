using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;

namespace Init.Sigepro.FrontEnd.Bari.FirmaCidPin
{
	public class CidPinSignRequest
	{
		public readonly string Cid;
		public readonly string Pin;
		public readonly string FileName;
		public readonly byte[] FileContent;

		public CidPinSignRequest(string cid, string pin, string fileName, byte[] fileContent)
		{
			this.Cid = cid;
			this.Pin = pin;
			this.FileName = fileName;
			this.FileContent = fileContent;
		}

		public string GetCidPinHash()
		{
			using (var md5 = new MD5CryptoServiceProvider())
			{
				var rstr = String.Format("{0}\\/CORSETTI\\/{1}", this.Cid, this.Pin);

				byte[] retVal = md5.ComputeHash(Encoding.Default.GetBytes(rstr));
				return BitConverter.ToString(retVal).Replace("-", ""); // hex string
			}
		}
	}
}
