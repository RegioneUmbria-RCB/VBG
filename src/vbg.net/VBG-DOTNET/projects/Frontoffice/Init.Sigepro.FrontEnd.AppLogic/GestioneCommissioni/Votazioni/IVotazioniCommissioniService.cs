using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.Votazioni
{
    public interface IVotazioniCommissioniService
    {
        /// <summary>
        /// restituisce true se l'utente loggato può esprimere un voto.
        /// Questo si verifica nel caso in cui:
        /// - la carica ricoperta dall'utente ha diritto di voto
        /// - la commissione è aperta
        /// - nella commissione e per la pratica in oggetto non è già stato emesso un parere
        /// </summary>
        /// <param name="idCommissione"></param>
        /// <param name="uuidIstanza"></param>
        /// <returns></returns>
        bool UtenteLoggatoPuoEsprimereVoto(int idCommissione, string uuidIstanza);


        /// <summary>
        /// recupera il voto dell'utente loggato
        /// </summary>
        /// <param name="idCommissione"></param>
        /// <param name="uuidIstanza"></param>
        VotazionePraticaCommissioneDto GetVotoUtenteLoggato(int idCommissione, string uuidIstanza);
        
        
        IEnumerable<CommissioniVotiBaseDto> GetListaPareri();

        /// <summary>
        /// Esprime il voto per l'utente loggato
        /// </summary>
        /// <param name="idCommissione"></param>
        /// <param name="uuidIstanza"></param>
        /// <param name="voto"></param>
        void EsprimiVotoPerUtenteLoggato(int idCommissione, string uuidIstanza, VotoPraticaCommissioneDto voto);
    }
}
