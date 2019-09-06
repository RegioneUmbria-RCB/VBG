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
		public List<StatiIstanza> GetStatiIstanza(string token, string software)
		{
			return new StatiIstanzaService().GetStatiIstanza(token, software);
		}

		[WebMethod]
		public StatiIstanza GetStatoIstanza(string token, string software, string codiceStato)
		{
			return new StatiIstanzaService().GetStatoIstanza(token, software, codiceStato);
		}
	}
}
