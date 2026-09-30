using System;
using System.Text;

namespace Rgls.Cig.Utility
{
	public class CBuf
	{
		public static string EncodeBase64(string str)
		{
			return Convert.ToBase64String(CBuf.ToByteArray(str));
		}

		public static string DecodeBase64(string str)
		{
			return CBuf.FromByteArray(Convert.FromBase64String(str));
		}

		public static byte[] ToByteArray(string str)
		{
			char[] charr = str.ToCharArray();
			byte[] data = new byte[charr.Length];
			for (int i = 0; i < charr.Length; i++)
			{
				data[i] = (byte)charr[i];
			}
			return data;
		}

		public static string FromByteArray(byte[] barray)
		{
			StringBuilder ret = new StringBuilder();
			for (int i = 0; i < barray.Length; i++)
			{
				ret.Append((char)barray[i]);
			}
			return ret.ToString();
		}

		public static sbyte[] ToSbyteArray(string str)
		{
			char[] charr = str.ToCharArray();
			sbyte[] data = new sbyte[charr.Length];
			for (int i = 0; i < charr.Length; i++)
			{
				data[i] = (sbyte)charr[i];
			}
			return data;
		}

		public static string ToBase16String(byte[] barray)
		{
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < barray.Length; i++)
			{
				string s = Convert.ToString(barray[i], 16);
				str.Append((s.Length == 1) ? ("0" + s) : s);
			}
			return str.ToString().ToUpper();
		}

    }
}
