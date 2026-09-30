using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsCampiRicercaVisuraRepository : Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces.ICampiRicercaVisuraRepository
	{
		AreaRiservataServiceCreator _serviceCreator;

		public WsCampiRicercaVisuraRepository(AreaRiservataServiceCreator serviceCreator)
		{
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			_serviceCreator = serviceCreator;
		}

		public  CampoVisuraFrontoffice[] GetFiltriVisuraFrontoffice(string idComune, string software)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				return ws.Service.GetFiltriVisuraFrontoffice(ws.Token, software);
			}
		}

		public  CampoVisuraFrontoffice[] GetFiltriArchivioIstanzeFrontoffice(string idComune, string software)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				return ws.Service.GetFiltriArchivioIstanzeFrontoffice(ws.Token, software);
			}
		}

		public  CampoVisuraFrontoffice[] GetCampiTabellaVisura(string idComune, string software)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				return ws.Service.GetCampiTabellaVisura(ws.Token, software);
			}
		}

		public  CampoVisuraFrontoffice[] GetCampiTabellaArchivioIstanze(string idComune, string software)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				return ws.Service.GetCampiTabellaArchivioIstanze(ws.Token, software);
			}
		}

		public  int GetRecordPerPagina(string idComune, string software)
		{
			using (var ws = _serviceCreator.CreateClient(idComune))
			{
				return ws.Service.GetRecordPerPaginaTabellaVisura(ws.Token, software);
			}
		}
	}
}
