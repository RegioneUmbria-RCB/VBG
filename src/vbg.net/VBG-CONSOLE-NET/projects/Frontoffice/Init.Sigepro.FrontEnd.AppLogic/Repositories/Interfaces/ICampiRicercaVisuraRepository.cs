using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface ICampiRicercaVisuraRepository
	{
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.CampoVisuraFrontoffice[] GetCampiTabellaArchivioIstanze(string idComune, string software);
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.CampoVisuraFrontoffice[] GetCampiTabellaVisura(string idComune, string software);
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.CampoVisuraFrontoffice[] GetFiltriArchivioIstanzeFrontoffice(string idComune, string software);
		Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService.CampoVisuraFrontoffice[] GetFiltriVisuraFrontoffice(string idComune, string software);
		int GetRecordPerPagina(string idComune, string software);
	}
}
