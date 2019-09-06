using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public List<BaseDto<string,string>> GetModalitaPagamento(string token)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				return
					new TipiModalitaPagamentoMgr(db).GetList(authInfo.IdComune)
													.Select(x => new BaseDto<string, string>(x.MP_ID, x.MP_DESCRESTESA))
													.ToList();

			}

		}
	}
}