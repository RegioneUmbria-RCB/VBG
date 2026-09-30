using Init.SIGePro.Manager.DTO.Scadenzario;
using System.Collections.Generic;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Scadenzario
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsScadenzarioService" in both code and config file together.
    [ServiceContract]
    public interface IWsScadenzarioService
    {
        [OperationContract]
        ScadenzaAreaPersonaleDto GetScadenzaByUuid(string token, string uuid);

        [OperationContract]
        IEnumerable<ElementoListaScadenzeDto> GetListaScadenze(string token, RichiestaListaScadenzeDto richiesta);

        [OperationContract]
        ScadenzaDto GetScadenzaById(string token, int codiceScadenza);

        [OperationContract]
        DatiMovimentoDaEffettuareDto GetMovimento(string token, string strCodiceMovimento);

        [OperationContract]
        DatiMovimentoDaEffettuareDto GetMovimentoByUuid(string token, string uuidMovimento);

        [OperationContract]
        DocumentiIstanzaSostituibiliDto GetDocumentiSostituibili(string token, int codiceMovimentodaeffettuare);

        [OperationContract]
        ConfigurazioneMovimentoDaEffettuareDto GetConfigurazioneMovimento(string token, int codiceMovimento);

        [OperationContract]
        string GetJsonMovimentoFrontoffice(string token, int idMovimento);

        [OperationContract]
        void SalvaJsonMovimentoFrontoffice(string token, int idMovimento, string datiJson);

        [OperationContract]
        void ImpostaFlagTrasmesso(string token, int idMovimento);

    }
}
