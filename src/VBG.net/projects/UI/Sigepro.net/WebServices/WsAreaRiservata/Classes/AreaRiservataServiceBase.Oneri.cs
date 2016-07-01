using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager.DTO.Oneri;
using Init.SIGePro.Manager;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public List<OnereDto> GetListaOneriDaIdInterventoECodiciEndo(string token, int codiceIntervento, List<int> listaIdEndo)
		{
			var ai = CheckToken(token);

			using (var db = ai.CreateDatabase())
			{
				var endoMgr = new InventarioProcedimentiMgr(db);
				var intervMgr = new AlberoProcMgr(db);

				var rVal = new List<OnereDto>(intervMgr.GetListaOneriDaIdIntervento( ai.IdComune , codiceIntervento ));

				for (int i = 0; i < listaIdEndo.Count; i++)
				{


					var oneriEndo = endoMgr.GetOneriDaCodiceEndo(ai.IdComune, listaIdEndo[i]);

					rVal.AddRange(oneriEndo);					
				}

				return rVal;

			}
		}
	}
}