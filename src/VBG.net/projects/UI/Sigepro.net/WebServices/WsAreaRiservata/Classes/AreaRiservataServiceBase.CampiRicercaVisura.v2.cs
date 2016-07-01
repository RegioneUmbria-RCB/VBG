using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.SIGePro.Data;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public List<FoVisuraCampi> GetFiltriVisuraV2(string token, string software)
		{
			return new CampiVisuraServiceV2().GetFiltriVisura(token, software);
		}


		[WebMethod]
		public List<FoVisuraCampi> GetCampiListaVisuraV2(string token, string software)
		{
			return new CampiVisuraServiceV2().GetCampiListaVisura(token, software);
		}

		[WebMethod]
		public List<FoVisuraCampi> GetFiltriArchivioV2(string token, string software)
		{
			return new CampiVisuraServiceV2().GetFiltriArchivio(token, software);
		}

		[WebMethod]
		public List<FoVisuraCampi> GetCampiListaArchivioV2(string token, string software)
		{
			return new CampiVisuraServiceV2().GetCampiListaArchivio(token, software);
		}
	}
}