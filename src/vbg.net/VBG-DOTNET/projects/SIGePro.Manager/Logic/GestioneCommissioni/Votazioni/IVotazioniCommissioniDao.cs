using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Votazioni
{
    public interface IVotazioniCommissioniDao
    {
        IEnumerable<CommissioniVotiBaseDto> GetListaPareri();
        VotazionePraticaCommissioneDto GetVotoUtente(int idCommissione, int codiceRiga, int idAppello);
        bool CaricaDellUtentePermetteVoto(int idAppello);
        int GetIdRigaByCodiceIstanza(int idCommissione, int codiceIstanza);
        void AggiornaAnagraficaAppello(int idAppello, int codiceAnagrafe);
        void InserisciVoto(int idCommissione, int idRiga, int codiceParere, int idAppello, string note, int? codiceOggetto, IEstremiProtocollazioneCommissione estremiProtocollazione);
    }
}
