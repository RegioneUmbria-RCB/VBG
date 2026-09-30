using Init.SIGePro.Manager.DTO.Visura.ProssimiPassi;
using Init.SIGePro.Manager.Logic.Visura;
using PersonalLib2.Data.Providers;
using System.Collections.Generic;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Istanze
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsIstanzeService" in both code and config file together.
    [ServiceContract]
    public interface IWsIstanzeService
    {
        [OperationContract]
        Init.SIGePro.Data.Istanze GetDettaglioPratica(string token, int codiceIstanza);
        [OperationContract]
        Init.SIGePro.Data.Istanze GetDettaglioPraticaByUuid(string token, string uuid, bool effettuaSubVisuraMovimenti);
        [OperationContract]
        RisultatoVisuraPraticaV3 GetListaPraticheV3(string token, RichiestaListaPraticheV3 richiestaLista);
        [OperationContract]
        RisultatoVisuraPraticaV3 GetListaPraticheV3Paginato(string token, RichiestaListaPraticheV3 richiestaLista, QueryPaginationRequest paginationRequest);
        [OperationContract]
        int[] GetOggettiIstanzaDaValoriMetadato(string token, int codiceIstanza, string chiaveMetadato, string valoreMetadato);
        [OperationContract]
        string GetUuidDaCodiceIstanza(string token, int codiceIstanza);
        [OperationContract]
        IEnumerable<ProssimiPassiDto> GetProssimiPassi(string token, int codiceIstanza);

    }
}
