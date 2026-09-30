using Init.SIGePro.Manager.DTO.Visura;
using Init.SIGePro.Manager.DTO.Visura.V1;
using Init.SIGePro.Manager.DTO.Visura.V2;
using System.Collections.Generic;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Visura
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsRicercaVisuraService" in both code and config file together.
    [ServiceContract]
    public interface IWsRicercaVisuraService
    {
        [OperationContract]
        List<CampoVisuraFrontofficeDto> GetCampiTabellaArchivioIstanze(string token, string software);
        [OperationContract]
        List<CampoVisuraFrontofficeDto> GetCampiTabellaVisura(string token, string software);
        [OperationContract]
        List<CampoVisuraFrontofficeDto> GetFiltriArchivioIstanzeFrontoffice(string token, string software);
        [OperationContract]
        List<CampoVisuraFrontofficeDto> GetFiltriVisuraFrontoffice(string token, string software);
        [OperationContract]
        int GetRecordPerPagina(string token, string software);

        [OperationContract]
        List<FoVisuraCampiDto> GetFiltriVisuraV2(string token, string software);
        [OperationContract]
        List<FoVisuraCampiDto> GetCampiListaVisuraV2(string token, string software);
        [OperationContract]
        List<FoVisuraCampiDto> GetFiltriArchivioV2(string token, string software);
        [OperationContract]
        List<FoVisuraCampiDto> GetCampiListaArchivioV2(string token, string software);
        [OperationContract]
        List<StatoIstanzaDto> GetStatiIstanza(string token, string software);
        [OperationContract]
        StatoIstanzaDto GetStatoIstanza(string token, string software, string codiceStato);

    }
}
