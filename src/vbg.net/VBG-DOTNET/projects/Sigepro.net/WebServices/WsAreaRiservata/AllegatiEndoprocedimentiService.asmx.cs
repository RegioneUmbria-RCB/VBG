using System.Collections.Generic;
using System.ComponentModel;
using System.Web.Services;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Common;
using PersonalLib2.Sql;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for AllegatiEndoprocedimenti
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	public class AllegatiEndoprocedimentiService : System.Web.Services.WebService
	{

	}
}
