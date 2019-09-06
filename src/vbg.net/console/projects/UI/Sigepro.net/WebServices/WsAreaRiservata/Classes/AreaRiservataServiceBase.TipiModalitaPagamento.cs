using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager;
using System.Web.Services;
using Init.SIGePro.Manager.Logic.AidaSmart;
using Init.SIGePro.Manager.Logic.AidaSmart.GestioneModalitaPagamento;

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
                var console = new ConsoleService(db, authInfo.Alias);
                var service = new ModalitaPagamentoConsoleService(console);

                return service.GetModalitaPagamento().ToList();
                //return
                //	new TipiModalitaPagamentoMgr(db).GetList(authInfo.IdComune)
                //									.Select(x => new BaseDto<string, string>(x.MP_ID, x.MP_DESCRESTESA))
                //									.ToList();

            }

		}
	}
}