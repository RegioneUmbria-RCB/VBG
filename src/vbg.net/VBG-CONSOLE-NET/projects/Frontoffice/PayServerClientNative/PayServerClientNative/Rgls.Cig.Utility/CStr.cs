using System;
using System.IO;

namespace Rgls.Cig.Utility
{
	public class CStr
	{
		public static int CountLetterChar(string src)
		{
			int cnt = 0;
			string text = src.Trim();
			for (int i = 0; i < text.Length; i++)
			{
				char ch = text[i];
				if (char.IsLetter(ch))
				{
					cnt++;
				}
			}
			return cnt;
		}

		public static int CountLowerChar(string src)
		{
			int cnt = 0;
			string text = src.Trim();
			for (int i = 0; i < text.Length; i++)
			{
				char ch = text[i];
				if (char.IsLower(ch))
				{
					cnt++;
				}
			}
			return cnt;
		}

		public static int CountUpperChar(string src)
		{
			int cnt = 0;
			string text = src.Trim();
			for (int i = 0; i < text.Length; i++)
			{
				char ch = text[i];
				if (char.IsUpper(ch))
				{
					cnt++;
				}
			}
			return cnt;
		}

		public static int CountNumChar(string src)
		{
			int cnt = 0;
			string text = src.Trim();
			for (int i = 0; i < text.Length; i++)
			{
				char ch = text[i];
				if (char.IsNumber(ch))
				{
					cnt++;
				}
			}
			return cnt;
		}

		public static int CountConsecutiveChar(string src, bool caseSensitive)
		{
			bool flag = false;
			char oldch = '\0';
			int cnt = 1;
			int oldcnt = 0;
			string text = src.Trim();
			for (int i = 0; i < text.Length; i++)
			{
				char ch = text[i];
				if (oldch == (caseSensitive ? ch : char.ToUpper(ch)))
				{
					cnt++;
					flag = true;
				}
				else if (flag)
				{
					if (cnt > oldcnt)
					{
						oldcnt = cnt;
					}
					cnt = 1;
					flag = false;
				}
				oldch = (caseSensitive ? ch : char.ToUpper(ch));
			}
			if (cnt > oldcnt)
			{
				oldcnt = cnt;
			}
			return oldcnt;
		}

		public static string Left(string src, int len)
		{
			string result;
			if (len >= src.Length)
			{
				result = src;
			}
			else
			{
				result = src.Substring(0, len);
			}
			return result;
		}

		public static int CompareToIgnoreCase(string src1, string src2)
		{
			return src1.ToUpper().CompareTo(src2.ToUpper());
		}

		public static string Substitute(string sOrigine, string sCosa, string sCon)
		{
			string sValue = sOrigine;
			string result;
			if (sOrigine == null || sCosa == null || sCon == null)
			{
				result = null;
			}
			else
			{
				for (int iStart = sValue.IndexOf(sCosa); iStart > -1; iStart = sValue.IndexOf(sCosa, iStart + sCon.Length))
				{
					int iEnd = iStart + sCosa.Length;
					sValue = sValue.Substring(0, iStart) + sCon + sValue.Substring(iEnd, sValue.Length - iEnd);
				}
				result = sValue;
			}
			return result;
		}

		public static string ReadFromFile(string sFileName)
		{
			string sOutBuffer = null;
			string result;
			try
			{
				StreamReader sr = File.OpenText(sFileName);
				sOutBuffer = sr.ReadToEnd();
				sr.Close();
			}
			catch
			{
				result = null;
				return result;
			}
			result = sOutBuffer;
			return result;
		}
	}
}
