using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Sigepro.net.WebServices.WsSIGePro;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public string GetUrlSchedaCARTEndo(string token, int codEndo)
		{
			return new CARTService().GetUrlSchedaCARTEndo(token, codEndo);
		}

		[WebMethod]
		public string GetUrlSchedaCARTIntervento(string token, int codIntervento)
		{
			return new CARTService().GetUrlSchedaCARTIntervento(token, codIntervento);
		}

		[WebMethod]
		public string GetCodiceAttivitaBdrDaIdIntervento(string token, string software, int codIntervento)
		{
			return new CARTService().GetCodiceAttivitaBdrDaIdIntervento(token, software, codIntervento);
		}
	}
}
