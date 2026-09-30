using Rgls.Cig.Utility;
using System;

namespace Rgls.Cig.SecretCode
{
	public class PayServerClient : SCBase
	{

		public PayServerClient(string sChiave, int iType, bool usaHashLowercase) : base(sChiave, iType, usaHashLowercase)
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

		public new int PS2S_PC_Request2RID(string requestBuffer, string componentName, DateTime dt)
		{
			requestBuffer = requestBuffer?.Replace("&",  "&amp;").Replace("&amp;amp;", "&amp;");
			return base.PS2S_PC_Request2RID(requestBuffer, componentName, dt);
		}

	}
}
