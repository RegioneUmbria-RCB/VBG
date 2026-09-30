using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public Software GetDatiSoftware(string token, string codice)
		{
			return new SoftwareService().GetDatiSoftware(token, codice);
		}
	}
}
