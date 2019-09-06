using System;

namespace Rgls.Cig.Utility
{
	public class paddingPKCS5
	{
		internal int a;

		internal int b;

		internal paddingPKCS5()
		{
			this.a = 0;
			this.b = 8;
		}

		internal sbyte[] pad(sbyte[] abyte0, int i)
		{
			int j = i % this.b;
			sbyte byte0;
			if (j == 0)
			{
				byte0 = (sbyte)this.b;
			}
			else
			{
				byte0 = (sbyte)(this.b - j);
			}
			sbyte[] abyte = new sbyte[i + (int)byte0];
			for (int ind = 0; ind < abyte0.Length; ind++)
			{
				abyte[ind] = abyte0[ind];
			}
			for (int k = 0; k < (int)byte0; k++)
			{
				abyte[i + k] = byte0;
			}
			return abyte;
		}

		internal sbyte[] unpad(sbyte[] abyte0)
		{
			int i = abyte0.Length;
			sbyte byte0 = abyte0[i - 1];
			sbyte[] result;
			if ((int)byte0 > this.b || byte0 < 1)
			{
				result = new sbyte[0];
			}
			else
			{
				int j = i - (int)byte0;
				sbyte[] abyte = new sbyte[j];
				for (int ind = 0; ind < j; ind++)
				{
					abyte[ind] = abyte0[ind];
				}
				for (int k = j; k < i; k++)
				{
					if (abyte0[k] != byte0)
					{
						result = new sbyte[0];
						return result;
					}
				}
				result = abyte;
			}
			return result;
		}
	}
}
