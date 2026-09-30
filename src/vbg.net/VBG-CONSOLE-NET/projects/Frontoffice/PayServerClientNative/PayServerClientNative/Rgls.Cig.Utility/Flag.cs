using System;

namespace Rgls.Cig.Utility
{
	public class Flag
	{
		public static readonly int NONDEFINITO = -1;

		public static readonly int VERO = 1;

		public static readonly int FALSO = 0;

		private static readonly int MAXFLAG = 2;

		public static bool IsNonDefinito(string sFlag)
		{
			bool result;
			try
			{
				result = Flag.IsNonDefinito(int.Parse(sFlag));
			}
			catch
			{
				result = false;
			}
			return result;
		}

		public static bool IsNonDefinito(int iFlag)
		{
			return iFlag == Flag.NONDEFINITO;
		}

		public static bool IsVero(string sFlag)
		{
			bool result;
			try
			{
				result = Flag.IsVero(int.Parse(sFlag));
			}
			catch
			{
				result = false;
			}
			return result;
		}

		public static bool IsVero(int iFlag)
		{
			return iFlag == Flag.VERO;
		}

		public static bool IsFalso(string sFlag)
		{
			bool result;
			try
			{
				result = Flag.IsFalso(int.Parse(sFlag));
			}
			catch
			{
				result = false;
			}
			return result;
		}

		public static bool IsFalso(int iFlag)
		{
			return iFlag == Flag.FALSO;
		}

		public static bool isOK(string sFlag)
		{
			bool result;
			try
			{
				result = Flag.isOK(int.Parse(sFlag));
			}
			catch
			{
				result = false;
			}
			return result;
		}

		public static bool isOK(int iFlag)
		{
			return iFlag >= 0 && iFlag <= Flag.MAXFLAG;
		}
	}
}
