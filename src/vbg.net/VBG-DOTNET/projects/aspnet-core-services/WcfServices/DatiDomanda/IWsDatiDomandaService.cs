using Init.SIGePro.Manager.DTO.DatiDomandaOnline;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.DatiDomanda
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsDatiDomandaService" in both code and config file together.
    [ServiceContract]
    public interface IWsDatiDomandaService
    {
        [OperationContract]
        bool DomandaEliminata(string token, int idDomanda);
        [OperationContract]
        void EliminaDomanda(string token, int idDomanda);
        [OperationContract]
        List<DatiDomandaOnlineDto> GetListaDomandeInSospeso(string token, string software, int codiceAnagrafe, string provenienza);
        [OperationContract]
        int GetProssimoIdDomanda(string token);
        [OperationContract]
        void ImpostaIdIstanzaOrigine(string token, int idDomanda, int? idDomandaOrigine);
        [OperationContract]
        DatiDomandaOnlineDto LeggiDatiDomanda(string token, int idDomanda);
        [OperationContract]
        byte[] LeggiDomanda(string token, int idDomanda);
        [OperationContract]
        void MarcaDomandaComePresentata(string token, int idDomanda, int codiceIstanza);
        [OperationContract]
        EsitoSalvataggioDomandaOnlineDto SalvaDomanda(string token, SalvaDomandaCommandDto salvaDomandaCommand);
        [OperationContract]
        bool VerificaStatoInvio(string token, int idDomanda);
    }
}
