using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public List<CampoVisuraFrontoffice> GetFiltriVisuraFrontoffice(string token, string software)
		{
			return new CampiRicercaVisuraService().GetFiltriVisuraFrontoffice(token, software);
		}


		[WebMethod]
		public List<CampoVisuraFrontoffice> GetCampiTabellaVisura(string token, string software)
		{
			return new CampiRicercaVisuraService().GetCampiTabellaVisura(token, software);
		}

		[WebMethod]
		public List<CampoVisuraFrontoffice> GetFiltriArchivioIstanzeFrontoffice(string token, string software)
		{
			return new CampiRicercaVisuraService().GetFiltriArchivioIstanzeFrontoffice(token, software);
		}

		[WebMethod]
		public List<CampoVisuraFrontoffice> GetCampiTabellaArchivioIstanze(string token, string software)
		{
			return new CampiRicercaVisuraService().GetCampiTabellaArchivioIstanze(token, software);
		}


		[WebMethod]
		public int GetRecordPerPaginaTabellaVisura(string token, string software)
		{
			return new CampiRicercaVisuraService().GetRecordPerPagina(token, software);
		}
	}
}
