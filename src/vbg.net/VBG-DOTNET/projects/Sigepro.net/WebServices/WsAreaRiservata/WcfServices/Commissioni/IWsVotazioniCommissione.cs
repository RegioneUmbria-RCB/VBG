using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Commissioni
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsVotazioniCommissione" in both code and config file together.
    [ServiceContract]
    public interface IWsVotazioniCommissione
    {
        [OperationContract]
        IEnumerable<CommissioniVotiBaseDto> GetListaPareri(string token);

        [OperationContract]
        VotazionePraticaCommissioneDto GetVotoUtente(string token, int idCommissione, int codiceAnagrafe, string uuidIstanza);

        [OperationContract]
        bool UtentePuoEsprimereVoto(string token, int idCommissione, int codiceAnagrafe, string uuidIstanza);

        [OperationContract]
        void EsprimiVotoPerUtente(string token, int idCommissione, int codiceAnagrafe, string uuidIstanza, VotoPraticaCommissioneDto voto);
    }
}
