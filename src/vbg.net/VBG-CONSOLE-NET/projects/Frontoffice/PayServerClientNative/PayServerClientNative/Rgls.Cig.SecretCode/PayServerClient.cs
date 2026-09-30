using Rgls.Cig.Utility;
using System;

namespace Rgls.Cig.SecretCode
{
	public class PayServerClient : SCBase
	{
		public new string ServerURL
		{
			set
			{
				base.ServerURL = value;
			}
		}

		public new string ProxyServer
		{
			set
			{
				base.ProxyServer = value;
			}
		}

		public new string ProxyPort
		{
			set
			{
				base.ProxyPort = value;
			}
		}

		public new int LenComponente
		{
			get
			{
				return 3;
			}
		}

		public new string ComponenteDefault
		{
			get
			{
				return "DEF";
			}
		}

		public new DateTime DataVariazione
		{
			get
			{
				return base.DataVariazione;
			}
		}

		public new string Descrizione
		{
			get
			{
				return base.Descrizione;
			}
		}

		public new string PS2S_NetBuffer
		{
			get
			{
				return base.PS2S_NetBuffer;
			}
		}

		public new string PS2S_DataBuffer
		{
			get
			{
				return base.PS2S_DataBuffer;
			}
		}

		public new string PS2S_HASHR
		{
			get
			{
				return base.PS2S_HASHR;
			}
		}

		public new string PS2S_HASHC
		{
			get
			{
				return base.PS2S_HASHC;
			}
		}

		public new string PS2S_Componente
		{
			get
			{
				return base.PS2S_Componente;
			}
		}

		public new string Componente
		{
			get
			{
				return base.Componente;
			}
			set
			{
				base.Componente = value;
			}
		}

		public PayServerClient(string DBConfigFile) : base(DBConfigFile)
		{
		}

		public PayServerClient(string sChiave, int iType) : base(sChiave, iType)
		{
		}

		public PayServerClient(bool mode) : base(mode)
		{
		}

		public new string GetErrorDescr(int err)
		{
			return base.GetErrorDescr(err);
		}

		public new int PS2S_Net2DataBuffer(string sNetBuffer, string sCompDefault, int lFinestraTemporale)
		{
			return base.PS2S_Net2DataBuffer(sNetBuffer, sCompDefault, lFinestraTemporale);
		}

		public new int PS2S_Data2NetBuffer(string sDataBuffer, string sComp, DateTime dt, int iProtocolVersion)
		{
			return base.PS2S_Data2NetBuffer(sDataBuffer, sComp, dt, iProtocolVersion);
		}

		public int PS2S_Data2NetBuffer(string sDataBuffer, string sComp, DateTime dt)
		{
			return base.PS2S_Data2NetBuffer(sDataBuffer, sComp, dt, 2);
		}

		public new int PS2S_PC_Request2RID(string sRequestBuffer, string sComp, DateTime dt)
		{
			string c = CStr.Substitute(sRequestBuffer, "&", "&amp;");
			c = CStr.Substitute(c, "&amp;amp;", "&amp;");
			return base.PS2S_PC_Request2RID(c, sComp, dt);
		}

		public int PS2S_PC_Request2RID(string sRequestBuffer, string sComp, DateTime dt, bool fAmpersend)
		{
			int result;
			if (fAmpersend)
			{
				result = this.PS2S_PC_Request2RID(sRequestBuffer, sComp, dt);
			}
			else
			{
				result = base.PS2S_PC_Request2RID(sRequestBuffer, sComp, dt);
			}
			return result;
		}

		public new int PS2S_PC_PID2Data(string sIdBuffer, string sComp, DateTime dt, int lFinestraTemporale)
		{
			return base.PS2S_PC_PID2Data(sIdBuffer, sComp, dt, lFinestraTemporale);
		}
	}
}
