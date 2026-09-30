using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Votazioni
{
    public interface IVotazioniCommissioniService
    {
        bool UtentePuoEsprimereVoto(int idCommissione, int codiceAnagrafe, string uuidIstanza);
        VotazionePraticaCommissioneDto GetVotoUtente(int idCommissione, int codiceAnagrafe, string uuidIstanza);
        IEnumerable<CommissioniVotiBaseDto> GetListaPareri();
        void EsprimiVotoPerUtente(int idCommissione, int codiceAnagrafe, string uuidIstanza, VotoPraticaCommissioneDto voto, IProtocollazioneCommissioneService protocollazioneService);
    }
}
