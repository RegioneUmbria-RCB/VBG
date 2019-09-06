using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Sigepro.net.WebServices.WsSIGePro;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public List<FormeGiuridiche> GetListaFormeGiuridiche(string token)
		{
			return new FormeGiuridicheService().GetList(token);
		}
	}
}
