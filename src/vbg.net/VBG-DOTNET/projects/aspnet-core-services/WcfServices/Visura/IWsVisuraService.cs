// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Visura
{
    // NOTA: è possibile utilizzare il comando "Rinomina" del menu "Refactoring" per modificare il nome di interfaccia "IWsVisuraService" nel codice e nel file di configurazione contemporaneamente.
    [ServiceContract]
    public interface IWsVisuraService
    {
        [OperationContract]
        Visura GetDettaglioPratica(string token, int codiceIstanza);

        [OperationContract]
        Visura GetDettaglioPraticaByUuid(string token, string uuid);
    }
}
