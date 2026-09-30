//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Web;
//using System.Web.Services;
//using Init.SIGePro.Manager.DTO.Oneri;
//using Init.SIGePro.Manager;
//using Init.SIGePro.Manager.Logic.ServiziConsole;
//using Init.SIGePro.Manager.Logic.ServiziConsole.GestioneOneri;
//using Init.SIGePro.Manager.Logic.ServiziConsole.GestioneOneri.GestioneConti;

//namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
//{
//	public partial class AreaRiservataServiceBase
//	{
//		[WebMethod]
//		public List<OnereDto> GetListaOneriDaIdInterventoECodiciEndo(string token, int codiceIntervento, List<int> listaIdEndo, string codiceComuneAssociato)
//        {
//			var ai = CheckToken(token);

//			using (var db = ai.CreateDatabase())
//			{
//                var consoleService = new ConsoleService(db, ai.Alias);
//                var oneriService = new OneriConsoleService(db, consoleService);

//                return oneriService.GetListaOneriDaIdInterventoECodiciEndo(codiceIntervento, listaIdEndo, codiceComuneAssociato).ToList();
//            }
//		}

//		[WebMethod]
//		public ContoDto GetContoDaIdCausaleOnere(string token, string software, string codiceComune, int idCausale)
//        {
//			var ai = CheckToken(token);

//			using (var db = ai.CreateDatabase())
//            {
//				var consoleService = new ConsoleService(db, ai.Alias);
//				var contiService = new ContiService(db, consoleService);
//				return contiService.GetContoDaIdCausaleOnere(codiceComune, software, idCausale);
//			}
//        }
//	}
//}