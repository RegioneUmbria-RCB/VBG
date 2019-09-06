using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Manager;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for CampiVisuraServiceV2
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class CampiVisuraServiceV2 : SigeproWebService
	{
		const string CONTESTO_ARCHIVIO_LISTA = "ARCHIVIO_LISTA";
		const string CONTESTO_ARCHIVIO_FILTRI = "ARCHIVIO_FILTRI";
		const string CONTESTO_VISURA_LISTA = "VISURA_LISTA";
		const string CONTESTO_VISURA_FILTRI = "VISURA_FILTRI";

		[WebMethod]
		public List<FoVisuraCampi> GetFiltriVisura(string token , string software)
		{
			var ai = CheckToken(token);

			using (var db = ai.CreateDatabase())
				return new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_VISURA_FILTRI);
		}

		[WebMethod]
		public List<FoVisuraCampi> GetCampiListaVisura(string token, string software)
		{
			var ai = CheckToken(token);

			using (var db = ai.CreateDatabase())
				return new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_VISURA_LISTA);
		}

		[WebMethod]
		public List<FoVisuraCampi> GetFiltriArchivio(string token, string software)
		{
			var ai = CheckToken(token);

			using (var db = ai.CreateDatabase())
				return new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_ARCHIVIO_FILTRI);
		}

		[WebMethod]
		public List<FoVisuraCampi> GetCampiListaArchivio(string token, string software)
		{
			var ai = CheckToken(token);

			using (var db = ai.CreateDatabase())
				return new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_ARCHIVIO_LISTA);
		}
	}
}
