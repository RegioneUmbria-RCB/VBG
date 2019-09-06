using Rgls.Cig.Utility;
using System;

namespace Rgls.Cig.SecretCode
{
	internal class TrasfObj
	{
		private string sPWD;

		private byte[] xxx = new byte[]
		{
			82,
			101,
			103,
			117,
			108,
			117,
			115
		};

		internal TrasfObj()
		{
			this.sPWD = CBuf.FromByteArray(this.xxx);
		}

		internal string Cripta(string sTesto)
		{
			RC4 objRc4 = new RC4();
			string sc = objRc4.StringaCasuale;
			objRc4.Rc4Initilize(this.sPWD + sc);
			string s = sc + objRc4.ManagementRc4(sTesto);
			return Convert.ToBase64String(CBuf.ToByteArray(s));
		}

		internal string Decripta(string sTesto)
		{
			RC4 objRc4 = new RC4();
			string stmp = CBuf.FromByteArray(Convert.FromBase64String(sTesto));
			string sc = CStr.Left(stmp, 10);
			stmp = stmp.Substring(10);
			objRc4.Rc4Initilize(this.sPWD + sc);
			return objRc4.ManagementRc4(stmp);
		}

		internal string TagOrario()
		{
			DateTime dt = DateTime.Now;
			return string.Format("{0:D4}{1:D2}{2:D2}{3:D2}{4:D2}", new object[]
			{
				dt.Year,
				dt.Month,
				dt.Day,
				dt.Hour,
				dt.Minute
			});
		}

		internal string TagOrarioShort(string sTag)
		{
			string result;
			if (sTag == null)
			{
				result = this.TagOrario();
			}
			else if (sTag.Length != 12)
			{
				result = "";
			}
			else
			{
				result = sTag.Substring(6, 2) + sTag.Substring(4, 2) + sTag.Substring(0, 4);
			}
			return result;
		}
	}
}
