using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;
using Sigepro.net.WebServices.WsSIGePro;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for CampiRicercaVisura
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class CampiRicercaVisuraService : SigeproWebService
	{

		[WebMethod]
		public List<CampoVisuraFrontoffice> GetFiltriVisuraFrontoffice(string token, string software)
		{
			var authResult = CheckToken(token);

			using (var db = authResult.CreateDatabase())
				return new FoConfigurazioneMgr(db).GetFiltriVisuraFrontoffice(authResult.IdComune, software);
		}


		[WebMethod]
		public List<CampoVisuraFrontoffice> GetCampiTabellaVisura(string token, string software)
		{
			var authResult = CheckToken(token);

			using (var db = authResult.CreateDatabase())
				return new FoConfigurazioneMgr(db).GetCampiTabellaVisura(authResult.IdComune, software);
		}

		[WebMethod]
		public List<CampoVisuraFrontoffice> GetFiltriArchivioIstanzeFrontoffice(string token, string software)
		{
			var authResult = CheckToken(token);

			using (var db = authResult.CreateDatabase())
				return new FoConfigurazioneMgr(db).GetFiltriArchivioIstanzeFrontoffice(authResult.IdComune, software);
		}

		[WebMethod]
		public List<CampoVisuraFrontoffice> GetCampiTabellaArchivioIstanze(string token, string software)
		{
			var authResult = CheckToken(token);

			using (var db = authResult.CreateDatabase())
				return new FoConfigurazioneMgr(db).GetCampiTabellaArchivioIstanze(authResult.IdComune, software);
		}


		[WebMethod]
		public int GetRecordPerPagina(string token, string software)
		{
			var authResult = CheckToken(token);

			using (var db = authResult.CreateDatabase())
				return new FoConfigurazioneMgr(db).GetRecordPerPagina(authResult.IdComune, software);
		}
	}
}
