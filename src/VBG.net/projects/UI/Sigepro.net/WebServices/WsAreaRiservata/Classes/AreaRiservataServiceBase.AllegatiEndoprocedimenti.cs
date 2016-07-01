using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public List<InventarioProcedimenti> GetAllegatiEndoprocedimenti(string token, List<string> codiciEndoSelezionati, AmbitoRicerca ambitoRicercaDocumenti)
		{
			return new AllegatiEndoprocedimentiService().GetAllegatiEndoprocedimenti(token, codiciEndoSelezionati, ambitoRicercaDocumenti);
		}
	}
}
