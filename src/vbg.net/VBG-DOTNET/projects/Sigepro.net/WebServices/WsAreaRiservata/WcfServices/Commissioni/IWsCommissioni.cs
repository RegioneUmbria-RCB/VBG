using Init.SIGePro.Manager.DTO.Commissioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Commissioni
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsCommissioni" in both code and config file together.
    [ServiceContract]
    public interface IWsCommissioni
    {
        [OperationContract]
        IEnumerable<CommissioneTestataDto> GetCommissioniAperteByCodiceAnagrafe(string token, int codiceAnagrafe);

        [OperationContract]
        DettaglioCommissioneDto GetDettaglioCommissione(string token, int idCommissione, int codiceAnagrafe);

        [OperationContract]
        PraticaCommissioneEstesaDto GetDettaglioPratica(string token, int idCommissione, int codiceAnagrafe, string uuidIstanza);

        [OperationContract]
        bool VerificaAccessoFile(string token, int idCommissione, int codiceAnagrafe, string uuidIstanza, int codiceOggetto);

        [OperationContract]
        bool ApprovaAllegato(string token, int idCommissione, int idAllegato, int codiceAnagrafe);
    }
}
