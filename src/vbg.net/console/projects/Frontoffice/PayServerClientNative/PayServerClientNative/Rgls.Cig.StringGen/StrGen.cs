using Rgls.Cig.Utility;
using System;
using System.Text;

namespace Rgls.Cig.StringGen
{
	public class StrGen
	{
		public const string sLettereMaiu = "QWERTYUIOPLKJHGFDSAZXCVBNM";

		public const string sLettereMin = "qwertyuioplkjhgfdsazxcvbnm";

		public const string sNumeri = "1234567890";

		public const string sSpeciali = "£?-_@";

		public const string sVocali = "eioau";

		public const string sConsonanti = "qwrtyplkjhgfdszxcvbnm";

		public const int NUM_SET = 4;

		public const int ID_SET_LETTERE_MAIU = 0;

		public const int ID_SET_LETTERE_MIN = 1;

		public const int ID_SET_NUMERI = 2;

		public const int ID_SET_SPECIALI = 3;

		public int m_lMinLen;

		public int m_lMaxLen;

		public int[] m_lMinSetLen;

		public int[] m_lMaxSetLen;

		public bool m_fMaiuscole;

		public bool m_fMinuscole;

		public bool m_fSiglaAlfabetica;

		public string m_sPrefix;

		public string m_sFormatString;

		public string[] m_sSetChar;

		public int APPOSIZIONE
		{
			get
			{
				return 0;
			}
		}

		public int APPOSIZIONECONPUNTO
		{
			get
			{
				return 1;
			}
		}

		public int APPOSIZIONECONTRATTO
		{
			get
			{
				return 2;
			}
		}

		public int INIZIALE
		{
			get
			{
				return 3;
			}
		}

		public int INIZIALECONPUNTO
		{
			get
			{
				return 4;
			}
		}

		public int INIZIALECONTRATTO
		{
			get
			{
				return 5;
			}
		}

		public int APPOSIZIONENUMERO
		{
			get
			{
				return 6;
			}
		}

		public int APPOSIZIONECONPUNTONUMERO
		{
			get
			{
				return 7;
			}
		}

		public int APPOSIZIONECONTRATTONUMERO
		{
			get
			{
				return 8;
			}
		}

		public int INIZIALENUMERO
		{
			get
			{
				return 9;
			}
		}

		public int INIZIALECONPUNTONUMERO
		{
			get
			{
				return 10;
			}
		}

		public int INIZIALECONTRATTONUMERO
		{
			get
			{
				return 11;
			}
		}

		public int APPOSIZIONESTRINGA
		{
			get
			{
				return 12;
			}
		}

		public int APPOSIZIONECONPUNTOSTRINGA
		{
			get
			{
				return 13;
			}
		}

		public int APPOSIZIONECONTRATTOSTRINGA
		{
			get
			{
				return 14;
			}
		}

		public int INIZIALESTRINGA
		{
			get
			{
				return 15;
			}
		}

		public int INIZIALECONPUNTOSTRINGA
		{
			get
			{
				return 16;
			}
		}

		public int INIZIALECONTRATTOSTRINGA
		{
			get
			{
				return 17;
			}
		}

		public string SetLettereMaiuscoleDefault
		{
			get
			{
				return "QWERTYUIOPLKJHGFDSAZXCVBNM";
			}
		}

		public string SetLettereMinuscoleDefault
		{
			get
			{
				return "qwertyuioplkjhgfdsazxcvbnm";
			}
		}

		public string SetNumeriDefault
		{
			get
			{
				return "1234567890";
			}
		}

		public string SetSpecialiDefault
		{
			get
			{
				return "£?-_@";
			}
		}

		public bool SoloMaiuscole
		{
			set
			{
				this.m_fMaiuscole = value;
			}
		}

		public bool SoloMinuscole
		{
			set
			{
				this.m_fMinuscole = value;
			}
		}

		public string SetLettereMaiuscole
		{
			set
			{
				this.m_sSetChar[0] = value;
			}
		}

		public string SetLettereMinuscole
		{
			set
			{
				this.m_sSetChar[1] = value;
			}
		}

		public string SetNumeri
		{
			set
			{
				this.m_sSetChar[2] = value;
			}
		}

		public string SetSpeciali
		{
			set
			{
				this.m_sSetChar[3] = value;
			}
		}

		public bool SiglaAlfabetica
		{
			get
			{
				return this.m_fSiglaAlfabetica;
			}
			set
			{
				this.m_fSiglaAlfabetica = value;
			}
		}

		public int MaxLen
		{
			get
			{
				return this.m_lMaxLen;
			}
			set
			{
				this.m_lMaxLen = value;
				this.AssegnaMaxSetLen();
			}
		}

		public int MinLen
		{
			get
			{
				return this.m_lMinLen;
			}
			set
			{
				this.m_lMinLen = value;
				this.AssegnaMinSetLen();
			}
		}

		public int MaxSetLenMaiuscole
		{
			set
			{
				this.m_lMaxSetLen[0] = value;
				this.AssegnaMaxLen();
			}
		}

		public int MinSetLenMaiuscole
		{
			set
			{
				this.m_lMinSetLen[0] = value;
				this.AssegnaMinLen();
			}
		}

		public int MaxSetLenMinuscole
		{
			set
			{
				this.m_lMaxSetLen[1] = value;
				this.AssegnaMaxLen();
			}
		}

		public int MinSetLenMinuscole
		{
			set
			{
				this.m_lMinSetLen[1] = value;
				this.AssegnaMinLen();
			}
		}

		public int MaxSetLenNumeri
		{
			set
			{
				this.m_lMaxSetLen[2] = value;
				this.AssegnaMaxLen();
			}
		}

		public int MinSetLenNumeri
		{
			set
			{
				this.m_lMinSetLen[2] = value;
				this.AssegnaMinLen();
			}
		}

		public int MaxSetLenSpeciali
		{
			set
			{
				this.m_lMaxSetLen[3] = value;
				this.AssegnaMaxLen();
			}
		}

		public int MinSetLenSpeciali
		{
			set
			{
				this.m_lMinSetLen[3] = value;
				this.AssegnaMinLen();
			}
		}

		public string Stringa
		{
			get
			{
				return this.GeneraStringa2();
			}
		}

		public string StringaHex
		{
			get
			{
				return this.Ascii2Hex(this.GeneraStringa2());
			}
		}

		public string SetCharString
		{
			get
			{
				string str = string.Format("{0:D2}{1:D2}{2:D2}{3:D2}{4:D2}{5:D2}{6:D2}{7:D2}", new object[]
				{
					this.m_lMinSetLen[1],
					this.m_lMaxSetLen[1],
					this.m_lMinSetLen[0],
					this.m_lMaxSetLen[0],
					this.m_lMinSetLen[2],
					this.m_lMaxSetLen[2],
					this.m_lMinSetLen[3],
					this.m_lMaxSetLen[3]
				});
				return str + (this.m_fMinuscole ? "1" : "0") + (this.m_fMaiuscole ? "1" : "0");
			}
			set
			{
				if (value.Length > 1 && value.Substring(0, 1) == "S")
				{
					if (this.pCheckCharStringS(value))
					{
						this.m_sFormatString = value;
					}
				}
				if (this.pCheckCharString(value))
				{
					this.m_sFormatString = "";
					this.MinSetLenMinuscole = Convert.ToInt32(value.Substring(0, 2), 10);
					this.MaxSetLenMinuscole = Convert.ToInt32(value.Substring(2, 2), 10);
					this.MinSetLenMaiuscole = Convert.ToInt32(value.Substring(4, 2), 10);
					this.MaxSetLenMaiuscole = Convert.ToInt32(value.Substring(6, 2), 10);
					this.MinSetLenNumeri = Convert.ToInt32(value.Substring(8, 2), 10);
					this.MaxSetLenNumeri = Convert.ToInt32(value.Substring(10, 2), 10);
					this.MinSetLenSpeciali = Convert.ToInt32(value.Substring(12, 2), 10);
					this.MaxSetLenSpeciali = Convert.ToInt32(value.Substring(14, 2), 10);
					this.SoloMinuscole = (value.Substring(16, 1) == "1");
					this.SoloMaiuscole = (value.Substring(17, 1) == "1");
				}
			}
		}

		public StrGen()
		{
			this.m_sSetChar = new string[4];
			this.m_lMinSetLen = new int[4];
			this.m_lMaxSetLen = new int[4];
			for (int i = 0; i < 4; i++)
			{
				this.m_sSetChar[i] = "";
			}
			this.m_lMinLen = 13;
			this.m_lMaxLen = 20;
			this.m_sSetChar[0] = "QWERTYUIOPLKJHGFDSAZXCVBNM";
			this.m_lMinSetLen[0] = 4;
			this.m_lMaxSetLen[0] = 6;
			this.m_sSetChar[1] = "qwertyuioplkjhgfdsazxcvbnm";
			this.m_lMinSetLen[1] = 4;
			this.m_lMaxSetLen[1] = 6;
			this.m_sSetChar[2] = "1234567890";
			this.m_lMinSetLen[2] = 3;
			this.m_lMaxSetLen[2] = 5;
			this.m_sSetChar[3] = "£?-_@";
			this.m_lMinSetLen[3] = 2;
			this.m_lMaxSetLen[3] = 3;
			this.m_sPrefix = "";
			this.m_sFormatString = "";
			this.m_fMaiuscole = false;
			this.m_fMinuscole = false;
			this.m_fSiglaAlfabetica = true;
		}

		public string Identificativo()
		{
			string sTag = string.Format("{0:D4}{1:D2}{2:D2}{3:D2}{4:D2}{5:D2}{6:D3}", new object[]
			{
				DateTime.Now.Year,
				DateTime.Now.Month,
				DateTime.Now.Day,
				DateTime.Now.Hour,
				DateTime.Now.Minute,
				DateTime.Now.Second,
				DateTime.Now.Millisecond
			});
			return sTag + this.GeneraStringa("QWERTYUIOPLKJHGFDSAZXCVBNMqwertyuioplkjhgfdsazxcvbnm", 3);
		}

		public string Identificativo(char c)
		{
			string sTag = string.Format("{0:D4}{1:D2}{2:D2}{3:D2}{4:D2}{5:D2}{6:D3}", new object[]
			{
				DateTime.Now.Year,
				DateTime.Now.Month,
				DateTime.Now.Day,
				DateTime.Now.Hour,
				DateTime.Now.Minute,
				DateTime.Now.Second,
				DateTime.Now.Millisecond
			});
			return c.ToString().ToUpper() + sTag + this.GeneraStringa("QWERTYUIOPLKJHGFDSAZXCVBNMqwertyuioplkjhgfdsazxcvbnm", 2);
		}

		public string StringaLen(int vl)
		{
			this.MaxLen = vl;
			this.MinLen = vl;
			return this.GeneraStringa2();
		}

		public string StringaLenHex(int vl)
		{
			this.MaxLen = vl;
			this.MinLen = vl;
			return this.Ascii2Hex(this.GeneraStringa2());
		}

		public string Ascii2Hex(string sin)
		{
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < sin.Length; i++)
			{
				int bin = (int)Convert.ToChar(sin.Substring(i, 1));
				string s = Convert.ToString(bin, 16).ToUpper();
				str.Append((s.Length == 1) ? ("0" + s) : s);
			}
			return str.ToString();
		}

		public string Hex2Ascii(string sin)
		{
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < sin.Length; i += 2)
			{
				string s = sin.Substring(i, 2);
				int ch = Convert.ToInt32(s, 16);
				str.Append((char)ch);
			}
			return str.ToString();
		}

		public string Ascii2Cod(string sin)
		{
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < sin.Length; i++)
			{
				int bin = (int)Convert.ToChar(sin.Substring(i, 1));
				string s = Convert.ToString(Math.Abs(bin - 47), 10);
				str.Append((s.Length == 1) ? ("0" + s) : s);
			}
			return str.ToString();
		}

		public string Cod2Ascii(string sin)
		{
			string result;
			try
			{
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < sin.Length; i += 2)
				{
					string s = sin.Substring(i, 2);
					int ch = Convert.ToInt32(s, 10);
					str.Append((char)(ch + 47));
				}
				result = str.ToString();
			}
			catch
			{
				result = "";
			}
			return result;
		}

		public string Sigla(string sp1, string sp2, int m, int l, int ln)
		{
			string retval = "";
			string s;
			string s2;
			if (this.m_fSiglaAlfabetica)
			{
				s = this.pSoloAlfa(sp1);
				s2 = this.pSoloAlfa(sp2);
			}
			else
			{
				s = sp1;
				s2 = sp2;
			}
			if (ln <= 0)
			{
				ln = 4;
			}
			string s3 = this.GeneraStringa("QWERTYUIOPLKJHGFDSAZXCVBNM", ln);
			Random rnd = new Random();
			string i = string.Format("{0:D4}", rnd.Next(9999));
			if (l <= 0)
			{
				switch (m)
				{
				case 0:
					retval = s + s2;
					break;
				case 1:
					retval = s + "." + s2;
					break;
				case 2:
					retval = s + "_" + s2;
					break;
				case 3:
					if (s.Length > 0)
					{
						retval = s[0] + s2;
					}
					break;
				case 4:
					if (s.Length > 0)
					{
						retval = s[0] + "." + s2;
					}
					break;
				case 5:
					if (s.Length > 0)
					{
						retval = s[0] + "_" + s2;
					}
					break;
				case 6:
					retval = s + s2 + i;
					break;
				case 7:
					retval = s + "." + s2 + i;
					break;
				case 8:
					retval = s + "_" + s2 + i;
					break;
				case 9:
					if (s.Length > 0)
					{
						retval = s[0] + s2 + i;
					}
					break;
				case 10:
					if (s.Length > 0)
					{
						retval = string.Concat(new object[]
						{
							s[0],
							".",
							s2,
							i
						});
					}
					break;
				case 11:
					if (s.Length > 0)
					{
						retval = string.Concat(new object[]
						{
							s[0],
							"_",
							s2,
							i
						});
					}
					break;
				case 12:
					retval = s + s2 + s3;
					break;
				case 13:
					retval = s + "." + s2 + s3;
					break;
				case 14:
					retval = s + "_" + s2 + s3;
					break;
				case 15:
					if (s.Length > 0)
					{
						retval = s[0] + s2 + s3;
					}
					break;
				case 16:
					if (s.Length > 0)
					{
						retval = string.Concat(new object[]
						{
							s[0],
							".",
							s2,
							s3
						});
					}
					break;
				case 17:
					if (s.Length > 0)
					{
						retval = string.Concat(new object[]
						{
							s[0],
							"_",
							s2,
							s3
						});
					}
					break;
				}
			}
			else
			{
				string s1b = (s.Length >= l) ? s.Substring(0, l - 1) : s;
				switch (m)
				{
				case 0:
					retval = CStr.Left(s + s2, l);
					break;
				case 1:
					retval = CStr.Left(s1b + "." + s2, l);
					break;
				case 2:
					retval = CStr.Left(s1b + "_" + s2, l);
					break;
				case 3:
					if (s.Length > 0)
					{
						retval = CStr.Left(s[0] + s2, l);
					}
					break;
				case 4:
					if (s.Length > 0)
					{
						retval = CStr.Left(s[0] + "." + s2, l);
					}
					break;
				case 5:
					if (s.Length > 0)
					{
						retval = CStr.Left(s[0] + "_" + s2, l);
					}
					break;
				case 6:
					retval = CStr.Left(s + s2, l) + i;
					break;
				case 7:
					retval = CStr.Left(s1b + "." + s2, l) + i;
					break;
				case 8:
					retval = CStr.Left(s1b + "_" + s2, l) + i;
					break;
				case 9:
					if (s.Length > 0)
					{
						retval = CStr.Left(s[0] + s2, l) + i;
					}
					break;
				case 10:
					if (s.Length > 0)
					{
						retval = CStr.Left(s[0] + "." + s2, l) + i;
					}
					break;
				case 11:
					if (s.Length > 0)
					{
						retval = CStr.Left(s[0] + "_" + s2, l) + i;
					}
					break;
				case 12:
					retval = CStr.Left(s + s2, l) + s3;
					break;
				case 13:
					retval = CStr.Left(s1b + "." + s2, l) + s3;
					break;
				case 14:
					retval = CStr.Left(s1b + "_" + s2, l) + s3;
					break;
				case 15:
					if (s.Length > 0)
					{
						retval = CStr.Left(s[0] + s2, l) + s3;
					}
					break;
				case 16:
					if (s.Length > 0)
					{
						retval = CStr.Left(s[0] + "." + s2, l) + s3;
					}
					break;
				case 17:
					if (s.Length > 0)
					{
						retval = CStr.Left(s[0] + "_" + s2, l) + s3;
					}
					break;
				}
			}
			return retval;
		}

		public string SiglaLen(string s1, string s2, int l, int ln, int m)
		{
			return this.Sigla(s1, s2, m, l, ln);
		}

		private bool pCheckCharString(string s)
		{
			bool result;
			if (CStr.CountNumChar(s) != s.Length || s.Length != 18)
			{
				result = false;
			}
			else
			{
				for (int i = 0; i < 3; i++)
				{
					if (Convert.ToInt32(s.Substring(i * 4, 2), 10) > Convert.ToInt32(s.Substring(i * 4 + 2, 2), 10))
					{
						result = false;
						return result;
					}
				}
				result = ("00011011".IndexOf(s.Substring(s.Length - 2, 2)) != -1);
			}
			return result;
		}

		private string pSoloAlfa(string sin)
		{
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < sin.Length; i++)
			{
				char ch = sin[i];
				if (char.ToUpper(ch) >= 'A' && char.ToUpper(ch) <= 'Z')
				{
					str.Append(ch);
				}
			}
			return str.ToString();
		}

		private string GeneraStringa(string sin, int lLen)
		{
			string result;
			if (sin.Length == 0)
			{
				result = "";
			}
			else
			{
				Random rnd = new Random();
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < lLen; i++)
				{
					int ind = rnd.Next(sin.Length);
					char ch = sin[ind];
					str.Append(ch);
				}
				result = str.ToString();
			}
			return result;
		}

		private void AssegnaMaxLen()
		{
			int i = 0;
			for (int j = 0; j < 4; j++)
			{
				i += this.m_lMaxSetLen[j];
			}
			this.m_lMaxLen = i;
		}

		private void AssegnaMinLen()
		{
			int i = 0;
			for (int j = 0; j < 4; j++)
			{
				i += this.m_lMinSetLen[j];
			}
			this.m_lMinLen = i;
		}

		private void AssegnaMaxSetLen()
		{
			int i = 0;
			for (int j = 0; j < 4; j++)
			{
				this.m_lMaxSetLen[j] = this.m_lMaxLen / 4;
				i += this.m_lMaxSetLen[j];
			}
			if (i < this.m_lMaxLen)
			{
				this.m_lMaxSetLen[0] = this.m_lMaxSetLen[0] + this.m_lMaxLen - i;
			}
		}

		private void AssegnaMinSetLen()
		{
			int i = 0;
			for (int j = 0; j < 4; j++)
			{
				this.m_lMinSetLen[j] = this.m_lMinLen / 4;
				i += this.m_lMinSetLen[j];
			}
			if (i < this.m_lMinLen)
			{
				this.m_lMinSetLen[0] = this.m_lMinSetLen[0] + this.m_lMinLen - i;
			}
		}

		private string GeneraStringa2()
		{
			int[] iIndici = new int[4];
			string[] sParziali = new string[4];
			Random rnd = new Random();
			string retval;
			if (this.m_sFormatString == string.Empty)
			{
				int i = 0;
				for (int j = 0; j < 4; j++)
				{
					iIndici[j] = 0;
					if (this.m_lMaxSetLen[j] == 0 && this.m_lMinSetLen[j] == 0)
					{
						sParziali[j] = "";
					}
					else
					{
						int ln = (int)((double)(this.m_lMaxSetLen[j] - this.m_lMinSetLen[j]) * rnd.NextDouble());
						sParziali[j] = this.GeneraStringa(this.m_sSetChar[j], ln + this.m_lMinSetLen[j]);
					}
					i += sParziali[j].Length;
				}
				StringBuilder s = new StringBuilder();
				for (int k = 0; k < i; k++)
				{
					char c = '\0';
					int j = rnd.Next(3);
					do
					{
						if (j >= 4)
						{
							j = 0;
						}
						if (iIndici[j] >= sParziali[j].Length)
						{
							j++;
						}
						else
						{
							c = sParziali[j][iIndici[j]];
							iIndici[j]++;
						}
					}
					while (c == '\0');
					s.Append(c);
				}
				if (this.m_fMaiuscole)
				{
					retval = s.ToString().ToUpper();
				}
				else if (this.m_fMinuscole)
				{
					retval = s.ToString().ToLower();
				}
				else
				{
					retval = s.ToString();
				}
			}
			else
			{
				StringBuilder s = new StringBuilder();
				for (int j = 1; j < this.m_sFormatString.Length; j++)
				{
					string c2 = string.Empty;
					char c3 = this.m_sFormatString[j];
					if (c3 <= 'L')
					{
						if (c3 != '#')
						{
							switch (c3)
							{
							case 'A':
								c2 = "eioau".ToUpper();
								break;
							case 'B':
								c2 = "qwrtyplkjhgfdszxcvbnm".ToUpper() + "qwrtyplkjhgfdszxcvbnm";
								break;
							case 'C':
								c2 = "qwrtyplkjhgfdszxcvbnm".ToUpper();
								break;
							case 'D':
								break;
							case 'E':
								c2 = "eioau".ToUpper() + "eioau";
								break;
							default:
								if (c3 == 'L')
								{
									c2 = "QWERTYUIOPLKJHGFDSAZXCVBNM";
								}
								break;
							}
						}
						else
						{
							c2 = "1234567890";
						}
					}
					else if (c3 != 'X')
					{
						switch (c3)
						{
						case 'a':
							c2 = "eioau";
							break;
						case 'b':
							break;
						case 'c':
							c2 = "qwrtyplkjhgfdszxcvbnm";
							break;
						default:
							if (c3 == 'l')
							{
								c2 = "qwertyuioplkjhgfdsazxcvbnm";
							}
							break;
						}
					}
					else
					{
						c2 = "QWERTYUIOPLKJHGFDSAZXCVBNMqwertyuioplkjhgfdsazxcvbnm";
					}
					int id = (int)(rnd.NextDouble() * (double)((float)c2.Length));
					s.Append(c2[id]);
				}
				retval = s.ToString();
			}
			return retval;
		}

		private bool pCheckCharStringS(string s)
		{
			bool result;
			if (s.Length < 3)
			{
				result = false;
			}
			else if (s[0] != 'S')
			{
				result = false;
			}
			else
			{
				for (int i = 1; i < s.Length; i++)
				{
					if ("AaECcBLlX#".IndexOf(s[i]) == -1)
					{
						result = false;
						return result;
					}
				}
				result = true;
			}
			return result;
		}
	}
}
