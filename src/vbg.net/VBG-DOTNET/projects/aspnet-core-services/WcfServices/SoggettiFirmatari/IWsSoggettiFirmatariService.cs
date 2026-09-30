using Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.SoggettiFirmatari
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsSoggettiFirmatariService" in both code and config file together.
    [ServiceContract]
    public interface IWsSoggettiFirmatariService
    {
        [OperationContract]
        ConfigurazioneSoggettiFirmatariDto GetSoggettiFirmatariDaIdDocumenti(string token, RichiestaSoggettiFirmatariDaIdDocumenti idDocumenti);

        [OperationContract]
        VerificaSoggettiFirmatariRiepilogoDomandaDto GetSoggettiFirmatariRiepilogoDomanda(string token, int idDocumento);
    }
}
