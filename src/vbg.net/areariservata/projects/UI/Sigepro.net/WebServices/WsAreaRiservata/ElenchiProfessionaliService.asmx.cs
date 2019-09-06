using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for ElenchiProfessionaliService
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class ElenchiProfessionaliService : System.Web.Services.WebService
	{

		[WebMethod]
		public List<ElenchiProfessionaliBase> GetElenchiProfessionali(string token)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
			{
				return new ElenchiProfessionaliBaseMgr(db).GetList(new ElenchiProfessionaliBase());
			}
		}
	}
}
