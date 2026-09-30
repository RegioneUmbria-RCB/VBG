using Init.SIGePro.Manager.DTO.RisorseTestuali;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.RisorseTestuali
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsRisorseTestualiService" in both code and config file together.
    [ServiceContract]
    public interface IWsRisorseTestualiService
    {
        [OperationContract]
        RisorsaTestualeDto[] GetRisorseTestuali(string token, string software);
        [OperationContract]
        void AggiornaRisorsaTestuale(string token, string software, string chiave, string valore, int codiceAnagrafe);
    }
}
