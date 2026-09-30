using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.Votazioni
{
    public interface IVotazioniCommissioneDao
    {
        IEnumerable<CommissioniVotiBaseDto> GetListaPareri();
        VotazionePraticaCommissioneDto GetVotoUtente(int idCommissione, int codiceAnagrafe, string uuidIstanza);
        bool UtentePuoEsprimereVoto(int idCommissione, int codiceAnagrafe, string uuidIstanza);
        void EsprimiVotoPerUtente(int idCommissione, int codiceAnagrafe, string uuidIstanza, VotoPraticaCommissioneDto voto);
    }
}
