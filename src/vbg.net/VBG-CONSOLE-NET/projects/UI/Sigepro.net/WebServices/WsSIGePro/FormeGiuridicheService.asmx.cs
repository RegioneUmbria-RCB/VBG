using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;

namespace Sigepro.net.WebServices.WsSIGePro
{
	/// <summary>
	/// Summary description for FormeGiuridicheService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class FormeGiuridicheService : System.Web.Services.WebService
	{

		[WebMethod]
		public List<FormeGiuridiche> GetList(string token)
		{
			var ai = AuthenticationManager.CheckToken(token);

			if (ai == null)
				throw new InvalidTokenException(token);

			var filtro = new FormeGiuridiche
			{
				IDCOMUNE = ai.IdComune,
				OrderBy = "FORMAGIURIDICA asc"
			};

			return new FormeGiuridicheMgr(ai.CreateDatabase()).GetList(filtro);
		}
	}
}
