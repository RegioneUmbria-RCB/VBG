using System;

namespace Rgls.Cig.Utility
{
	public class DBUtil
	{
		public static string ISODateFormat = "yyyyMMddHHmmss";

		public static string ShortISODateFormat = "yyyyMMdd";

		public static string ITDateFormat = "dd/MM/yyyy";

		public static string SQLDateFormat = "{'ts' \"'\"yyyy-MM-dd HH\":\"mm\":\"ss\"'\"}";

		protected static string SEPARATOREDEC = ",";

		public static DateTime now()
		{
			return DateTime.Now;
		}

		public static string createParam(string sNome, object oParam)
		{
			string sClassName = oParam.GetType().FullName;
			string result;
			if (sClassName.CompareTo("System.String") == 0)
			{
				result = sNome + "='" + DBUtil.toDBstring((string)oParam) + "'";
			}
			else if (sClassName.CompareTo("System.Int32") == 0)
			{
				result = sNome + "=" + (int)oParam;
			}
			else if (sClassName.CompareTo("System.Byte") == 0)
			{
				result = sNome + "=" + (byte)oParam;
			}
			else if (sClassName.CompareTo("System.Int16") == 0)
			{
				result = sNome + "=" + (short)oParam;
			}
			else if (sClassName.CompareTo("System.Int64") == 0)
			{
				result = sNome + "=" + (long)oParam;
			}
			else if (sClassName.CompareTo("System.Boolean") == 0)
			{
				if ((bool)oParam)
				{
					result = sNome + "= 1";
				}
				else
				{
					result = sNome + "= 0";
				}
			}
			else if (sClassName.CompareTo("System.Double") == 0)
			{
				result = sNome + "=" + (double)oParam;
			}
			else if (sClassName.CompareTo("System.Single") == 0)
			{
				result = sNome + "=" + (float)oParam;
			}
			else if (sClassName.CompareTo("System.DateTime") == 0)
			{
				result = sNome + "=" + ((DateTime)oParam).ToString(DBUtil.SQLDateFormat);
			}
			else
			{
				result = "";
			}
			return result;
		}

		public static string createParam(string sNome, int iParam)
		{
			return sNome + "=" + iParam;
		}

		public static string createParam(string sNome, byte iParam)
		{
			return sNome + "=" + iParam;
		}

		public static string createParam(string sNome, short iParam)
		{
			return sNome + "=" + iParam;
		}

		public static string createParam(string sNome, long iParam)
		{
			return sNome + "=" + iParam;
		}

		public static string createParam(string sNome, bool iParam)
		{
			string result;
			if (iParam)
			{
				result = sNome + "= 1";
			}
			else
			{
				result = sNome + "= 0";
			}
			return result;
		}

		public static string createParam(string sNome, double iParam)
		{
			return sNome + "=" + iParam;
		}

		public static string createParam(string sNome, float iParam)
		{
			return sNome + "=" + iParam;
		}

		public static string createParamFK(object oParam)
		{
			string s = DBUtil.createParam(oParam);
			if (s.CompareTo("''") == 0)
			{
				s = "NULL";
			}
			return s;
		}

		public static string createParam(object oParam)
		{
			string result;
			if (oParam == null)
			{
				result = "NULL";
			}
			else
			{
				string sClassName = oParam.GetType().FullName;
				if (sClassName.CompareTo("System.String") == 0)
				{
					result = "'" + DBUtil.toDBstring((string)oParam) + "'";
				}
				else if (sClassName.CompareTo("System.Int32") == 0)
				{
					result = ((int)oParam).ToString();
				}
				else if (sClassName.CompareTo("System.Byte") == 0)
				{
					result = ((byte)oParam).ToString();
				}
				else if (sClassName.CompareTo("System.Int16") == 0)
				{
					result = ((short)oParam).ToString();
				}
				else if (sClassName.CompareTo("System.Int64") == 0)
				{
					result = ((long)oParam).ToString();
				}
				else if (sClassName.CompareTo("System.Boolean") == 0)
				{
					if ((bool)oParam)
					{
						result = "1";
					}
					else
					{
						result = "0";
					}
				}
				else if (sClassName.CompareTo("System.Double") == 0)
				{
					result = ((double)oParam).ToString();
				}
				else if (sClassName.CompareTo("System.Single") == 0)
				{
					result = ((float)oParam).ToString();
				}
				else if (sClassName.CompareTo("System.DateTime") == 0)
				{
					result = ((DateTime)oParam).ToString(DBUtil.SQLDateFormat);
				}
				else
				{
					result = "";
				}
			}
			return result;
		}

		public static string createParam(int iParam)
		{
			return iParam.ToString();
		}

		public static string createParam(byte iParam)
		{
			return iParam.ToString();
		}

		public static string createParam(short iParam)
		{
			return iParam.ToString();
		}

		public static string createParam(long iParam)
		{
			return iParam.ToString();
		}

		public static string createParam(bool iParam)
		{
			string result;
			if (iParam)
			{
				result = "1";
			}
			else
			{
				result = "0";
			}
			return result;
		}

		public static string createParam(double iParam)
		{
			return iParam.ToString();
		}

		public static string createParam(float iParam)
		{
			return iParam.ToString();
		}

		public static string toDBstring(string sInput)
		{
			int nStart = 0;
			string buffOut = "";
			if (sInput != null)
			{
				int nPos;
				while ((nPos = sInput.IndexOf("'", nStart)) != -1)
				{
					buffOut = buffOut + sInput.Substring(nStart, nPos - nStart) + "''";
					nStart = nPos + 1;
				}
				buffOut += sInput.Substring(nStart);
			}
			return buffOut;
		}

		public static string valueOfstring(string s)
		{
			string result;
			if (s == null)
			{
				result = "";
			}
			else
			{
				result = s;
			}
			return result;
		}

		public static string valueOfstring(int i)
		{
			return i.ToString();
		}

		public static string ISOvalueOfDate(DateTime d)
		{
			return d.ToString(DBUtil.ISODateFormat);
		}

		public static string ISOvalueOfDate()
		{
			return DateTime.Now.ToString(DBUtil.ISODateFormat);
		}

		public static string shortISOvalueOfDate(DateTime d)
		{
			return d.ToString(DBUtil.ShortISODateFormat);
		}

		public static string shortISOvalueOfDate()
		{
			return DateTime.Now.ToString(DBUtil.ShortISODateFormat);
		}

		public static long dateDiffOre(DateTime d1, DateTime d2)
		{
			return (long)DBUtil.DateDiff("h", d2, d1);
		}

		public static long dateDiffMin(DateTime d1, DateTime d2)
		{
			return (long)DBUtil.DateDiff("m", d2, d1);
		}

		public static long dateDiffSec(DateTime d1, DateTime d2)
		{
			return (long)DBUtil.DateDiff("s", d2, d1);
		}

		public static long dateDiffMill(DateTime d1, DateTime d2)
		{
			return (long)DBUtil.DateDiff("mm", d2, d1);
		}

		public static string currency2string(int v)
		{
			string s = v.ToString();
			int i = s.Length;
			string r;
			if (i < 3)
			{
				r = "0" + DBUtil.SEPARATOREDEC + s;
			}
			else
			{
				r = s.Substring(0, i - 2) + DBUtil.SEPARATOREDEC + s.Substring(i - 2);
			}
			return r;
		}

		public static string number2string(int v, int l)
		{
			string result;
			try
			{
				string s = "0000000000" + v;
				result = s.Substring(s.Length - l);
			}
			catch (Exception e)
			{
				e.ToString();
				result = "";
			}
			return result;
		}

		public static bool checkParam(string sParam)
		{
			return sParam != null && !sParam.Equals("");
		}

		private static double DateDiff(string howtocompare, DateTime startDate, DateTime endDate)
		{
			double diff = 0.0;
			try
			{
				TimeSpan TS = new TimeSpan(startDate.Ticks - endDate.Ticks);
				string text = howtocompare.ToLower();
				switch (text)
				{
				case "m":
					diff = Convert.ToDouble(TS.TotalMinutes);
					goto IL_172;
				case "s":
					diff = Convert.ToDouble(TS.TotalSeconds);
					goto IL_172;
				case "h":
					diff = Convert.ToDouble(TS.TotalHours);
					goto IL_172;
				case "t":
					diff = Convert.ToDouble(TS.Ticks);
					goto IL_172;
				case "mm":
					diff = Convert.ToDouble(TS.TotalMilliseconds);
					goto IL_172;
				case "yyyy":
					diff = Convert.ToDouble(TS.TotalDays / 365.0);
					goto IL_172;
				case "q":
					diff = Convert.ToDouble(TS.TotalDays / 365.0 / 4.0);
					goto IL_172;
				}
				diff = Convert.ToDouble(TS.TotalDays);
				IL_172:;
			}
			catch (Exception e)
			{
				e.ToString();
				diff = -1.0;
			}
			return diff;
		}
	}
}
