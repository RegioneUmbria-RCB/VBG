// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.PagamentiESED
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsPagamentiESEDService" in both code and config file together.
    [ServiceContract]
    public interface IWsPagamentiESEDService
    {
        [OperationContract]
        EsitoNotificaPagamentiESEDDto SalvaNotificaPagamentoESED(string token, string idDomanda, string numeroOperazione, string messaggioXml, string esito, string data, string idOrdine, string idTransazione, string tipoPagamento);
        [OperationContract]
        DatiNotificaPagamentiESEDDto GetDatiNotifica(string token, string numeroOperazione);
    }
}
