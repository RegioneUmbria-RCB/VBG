using Init.SIGePro.Manager.DTO.Visura.V1;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.V1
{
    public interface ICampiRicercaVisuraRepository
    {
        CampoVisuraFrontofficeDto[] GetCampiTabellaArchivioIstanze(string idComune, string software);
        CampoVisuraFrontofficeDto[] GetCampiTabellaVisura(string idComune, string software);
        CampoVisuraFrontofficeDto[] GetFiltriArchivioIstanzeFrontoffice(string idComune, string software);
        CampoVisuraFrontofficeDto[] GetFiltriVisuraFrontoffice(string idComune, string software);
        int GetRecordPerPagina(string idComune, string software);
    }
}
