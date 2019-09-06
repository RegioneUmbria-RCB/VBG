using System;
using System.Text;

namespace Rgls.Cig.Utility
{
	public class RC4
	{
		private int[] sarray;

		public string StringaCasuale
		{
			get
			{
				string str = "";
				Random rnd = new Random();
				for (int i = 0; i < 10; i++)
				{
					str += (char)rnd.Next(255);
				}
				return str;
			}
		}

		public RC4()
		{
			this.sarray = new int[256];
		}

		public void Rc4Initilize(string str)
		{
			int b = 0;
			int[] kep = new int[256];
			byte[] bdata = CBuf.ToByteArray(str);
			for (int a = 0; a < 256; a++)
			{
				if (b >= str.Length)
				{
					b = 0;
				}
				kep[a] = (int)bdata[b];
				b++;
			}
			for (int a = 0; a < 256; a++)
			{
				this.sarray[a] = a;
			}
			b = 0;
			for (int a = 0; a < 256; a++)
			{
				b = (b + this.sarray[a] + kep[a]) % 256;
				int temp = this.sarray[a];
				this.sarray[a] = this.sarray[b];
				this.sarray[b] = temp;
			}
		}

		public string ManagementRc4(string strin)
		{
			StringBuilder strout = new StringBuilder();
			int i = 0;
			int j = 0;
			for (int a = 0; a < strin.Length; a++)
			{
				i = (i + 1) % 256;
				j = (j + this.sarray[i]) % 256;
				int tmp = this.sarray[i];
				this.sarray[i] = this.sarray[j];
				this.sarray[j] = tmp;
				int k = this.sarray[(this.sarray[i] + this.sarray[j]) % 256];
				int l = (int)Convert.ToChar(strin.Substring(a, 1));
				int cipherby = l ^ k;
				strout.Append(Convert.ToChar(cipherby));
			}
			return strout.ToString();
		}

		public string Decode(string Buffer, string Key)
		{
			string result;
			if (Buffer == null || Buffer.Length == 0)
			{
				result = null;
			}
			else if (Key == null || Key.Length == 0)
			{
				result = null;
			}
			else
			{
				this.Rc4Initilize(Key);
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < Buffer.Length; i += 2)
				{
					string s = Buffer.Substring(i, 2);
					int ch = Convert.ToInt32(s, 16);
					str.Append((char)ch);
				}
				string strout = this.ManagementRc4(str.ToString());
				result = strout;
			}
			return result;
		}

		public string Encode(string Buffer, string Key)
		{
			string result;
			if (Buffer == null || Buffer.Length == 0)
			{
				result = null;
			}
			else if (Key == null || Key.Length == 0)
			{
				result = null;
			}
			else
			{
				this.Rc4Initilize(Key);
				string strout = this.ManagementRc4(Buffer);
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < strout.Length; i++)
				{
					int bin = (int)Convert.ToChar(strout.Substring(i, 1));
					string s = Convert.ToString(bin, 16);
					str.Append((s.Length == 1) ? ("0" + s) : s);
				}
				result = str.ToString();
			}
			return result;
		}
	}
}
