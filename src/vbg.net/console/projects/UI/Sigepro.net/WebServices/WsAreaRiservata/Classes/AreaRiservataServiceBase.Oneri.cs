using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager.DTO.Oneri;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.AidaSmart.GestioneOneri;
using Init.SIGePro.Manager.Logic.AidaSmart;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public List<OnereDto> GetListaOneriDaIdInterventoECodiciEndo(string token, int codiceIntervento, List<int> listaIdEndo, string codiceComuneAssociato)
        {
			var ai = CheckToken(token);

			using (var db = ai.CreateDatabase())
			{
                var consoleService = new ConsoleService(db, ai.Alias);
                var oneriService = new OneriConsoleService(db, consoleService);

                return oneriService.GetListaOneriDaIdInterventoECodiciEndo(codiceIntervento, listaIdEndo, codiceComuneAssociato).ToList();


                /*
				var endoMgr = new InventarioProcedimentiMgr(db);
				var intervMgr = new AlberoProcMgr(db);

				var rVal = new List<OnereDto>(intervMgr.GetListaOneriDaIdIntervento( ai.IdComune , codiceIntervento ));

				for (int i = 0; i < listaIdEndo.Count; i++)
				{


					var oneriEndo = endoMgr.GetOneriDaCodiceEndo(ai.IdComune, listaIdEndo[i]);

					rVal.AddRange(oneriEndo);					
				}

				return rVal;
                */
            }
		}
	}
}