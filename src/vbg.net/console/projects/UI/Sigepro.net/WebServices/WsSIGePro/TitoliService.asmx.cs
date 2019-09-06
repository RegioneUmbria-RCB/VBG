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
	/// Summary description for TitoliService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class TitoliService : System.Web.Services.WebService
	{

		[WebMethod]
		public List<Titoli> GetList(string token)
		{
			var ai = AuthenticationManager.CheckToken(token);

			if (ai == null)
				throw new InvalidTokenException(token);

			var filtro = new Titoli{
				IDCOMUNE = ai.IdComune,
				OrderBy = "TITOLO asc"
			};

			return new TitoliMgr( ai.CreateDatabase()).GetList( filtro );
		}
	}
}
