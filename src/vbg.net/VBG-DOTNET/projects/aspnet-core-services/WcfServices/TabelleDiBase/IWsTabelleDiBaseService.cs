using Init.SIGePro.Manager.DTO.TabelleDiBase;
using System.Collections.Generic;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.TabelleDiBase
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsTabelleDiBaseService" in both code and config file together.
    [ServiceContract]
    public interface IWsTabelleDiBaseService
    {
        [OperationContract]
        List<ElencoProfessionaleDto> GetElenchiProfessionali(string token);
        [OperationContract]
        List<SedeInailDto> GetElencoSediInail(string token);
        [OperationContract]
        List<SedeInpsDto> GetElencoSediInps(string token);
        [OperationContract]
        List<FormaGiuridicaDto> GetListaFormeGiuridiche(string token);
        [OperationContract]
        List<TitoloDto> GetListaTitoli(string token);
        [OperationContract]
        List<ModalitaPagamentoDto> GetModalitaPagamento(string token);
    }
}
