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
	/// Summary description for StatiIstanzaService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class StatiIstanzaService : System.Web.Services.WebService
	{

		[WebMethod]
		public List<StatiIstanza> GetStatiIstanza(string token, string software)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
			{
				var filtro = new StatiIstanza
				{
					Idcomune = authResult.IdComune,
					Software = software,
					OrderBy = "ordine asc"
				};

				return new StatiIstanzaMgr(db).GetList(filtro);
			}
		}

		[WebMethod]
		public StatiIstanza GetStatoIstanza(string token, string software, string codiceStato)
		{
			var authResult = AuthenticationManager.CheckToken(token);

			if (authResult == null)
				throw new ArgumentException("Token non valido: " + token);

			using (var db = authResult.CreateDatabase())
			{
				return new StatiIstanzaMgr(db).GetById( authResult.IdComune , software , codiceStato);
			}

		}
	}
}
