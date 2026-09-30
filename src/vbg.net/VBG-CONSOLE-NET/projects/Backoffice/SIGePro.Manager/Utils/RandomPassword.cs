using System;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;
using System.Diagnostics;
using System.IO;

namespace Init.SIGePro.Manager.Utils
{
	public partial class RandomPassword
	{
		// Fields
		public static int DEFAULT_MIN_PASSWORD_LENGTH = 8;
		public static int DEFAULT_MAX_PASSWORD_LENGTH = 10;
		public static string PASSWORD_CHARS_LCASE = "abcdefgijkmnopqrstwxyz";
		public static string PASSWORD_CHARS_UCASE = "ABCDEFGHJKLMNPQRSTWXYZ";
		public static string PASSWORD_CHARS_NUMERIC = "23456789";
		public static string PASSWORD_CHARS_SPECIAL = "*$-+?_&=!%{}/";


		public static string Generate()
		{
			return Generate(DEFAULT_MIN_PASSWORD_LENGTH, DEFAULT_MAX_PASSWORD_LENGTH);
		}



		public static string Generate(int length)
		{
			return Generate(length, length);
		}


		public static string Generate(int minLength, int maxLength)
		{
			int num;
			if (((minLength <= 0) || (maxLength <= 0)) || (minLength > maxLength))
			{
				return null;
			}
			//char[][] chArray = new char[][] { PASSWORD_CHARS_LCASE.ToCharArray(), PASSWORD_CHARS_UCASE.ToCharArray(), PASSWORD_CHARS_NUMERIC.ToCharArray(), PASSWORD_CHARS_SPECIAL.ToCharArray() };
            char[][] chArray = new char[][] { PASSWORD_CHARS_LCASE.ToCharArray(), PASSWORD_CHARS_UCASE.ToCharArray(), PASSWORD_CHARS_NUMERIC.ToCharArray() };
			int[] numArray = new int[chArray.Length];
			for (num = 0; num < numArray.Length; num++)
			{
				numArray[num] = chArray[num].Length;
			}
			int[] numArray2 = new int[chArray.Length];
			for (num = 0; num < numArray2.Length; num++)
			{
				numArray2[num] = num;
			}
			byte[] data = new byte[4];
			new RNGCryptoServiceProvider().GetBytes(data);
			int seed = ((((data[0] & 0x7f) << 0x18) | (data[1] << 0x10)) | (data[2] << 8)) | data[3];
			Random random = new Random(seed);
			char[] chArray2 = null;
			if (minLength < maxLength)
			{
				chArray2 = new char[random.Next(minLength, maxLength + 1)];
			}
			else
			{
				chArray2 = new char[minLength];
			}
			int maxValue = numArray2.Length - 1;
			for (num = 0; num < chArray2.Length; num++)
			{
				int num3;
				int num5;
				if (maxValue == 0)
				{
					num5 = 0;
				}
				else
				{
					num5 = random.Next(0, maxValue);
				}
				int index = numArray2[num5];
				int num6 = numArray[index] - 1;
				if (num6 == 0)
				{
					num3 = 0;
				}
				else
				{
					num3 = random.Next(0, num6 + 1);
				}
				chArray2[num] = chArray[index][num3];
				if (num6 == 0)
				{
					numArray[index] = chArray[index].Length;
				}
				else
				{
					if (num6 != num3)
					{
						char ch = chArray[index][num6];
						chArray[index][num6] = chArray[index][num3];
						chArray[index][num3] = ch;
					}
					numArray[index]--;
				}
				if (maxValue == 0)
				{
					maxValue = numArray2.Length - 1;
				}
				else
				{
					if (maxValue != num5)
					{
						int num8 = numArray2[maxValue];
						numArray2[maxValue] = numArray2[num5];
						numArray2[num5] = num8;
					}
					maxValue--;
				}
			}
			return new string(chArray2);
		}



	}
}
