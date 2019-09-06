using System;
using System.Text;

namespace Rgls.Cig.Utility
{
	public class Blowfish : obj_Blowfish
	{
		private sbyte[] key = new sbyte[]
		{
			56,
			51,
			54,
			56
		};

		public string Decrypt16(string stin)
		{
			sbyte[] bufin = new sbyte[stin.Length / 2];
			for (int i = 0; i < stin.Length; i += 2)
			{
				bufin[i / 2] = Convert.ToSByte(stin.Substring(i, 2), 16);
			}
			sbyte[] buffout = this.decrypt(this.key, bufin);
			StringBuilder stout = new StringBuilder();
			for (int i = 0; i < buffout.Length; i++)
			{
				stout.Append((char)buffout[i]);
			}
			return stout.ToString();
		}

		public string Encrypt16(string stin)
		{
			sbyte[] bufin = new sbyte[stin.Length];
			for (int i = 0; i < stin.Length; i++)
			{
				bufin[i] = (sbyte)stin[i];
			}
			sbyte[] buffout = this.encrypt(this.key, bufin);
			StringBuilder stout = new StringBuilder();
			for (int i = 0; i < buffout.Length; i++)
			{
				string s = Convert.ToString((byte)buffout[i], 16);
				if (s.Length == 1)
				{
					stout.Append("0" + s);
				}
				else
				{
					stout.Append(s);
				}
			}
			return stout.ToString();
		}

		internal sbyte[] encrypt(sbyte[] skey, sbyte[] s)
		{
			obj_Blowfish cipher = new obj_Blowfish();
			paddingPKCS5 pad = new paddingPKCS5();
			cipher.setKey(skey);
			return cipher.encrypt(pad.pad(s, s.Length));
		}

		internal sbyte[] decrypt(sbyte[] skey, sbyte[] s)
		{
			obj_Blowfish cipher = new obj_Blowfish();
			paddingPKCS5 pad = new paddingPKCS5();
			cipher.setKey(skey);
			return pad.unpad(cipher.decrypt(s));
		}
	}
}
