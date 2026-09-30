using Init.SIGePro.Manager.DTO.Commissioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni
{
    public interface ICommissioniDao
    {
        IEnumerable<CommissioneTestataDto> GetCommissioniAperteByCodiceAnagrafe(int codiceAnagrafe);
        bool VerificaAccessoACommissione(int idCommissione, int codiceAnagrafe);
        CommissioneTestataDto CaricaTestata(int idCommissione);
        IEnumerable<ConvocazioneCommissioneDto> CaricaConvocazioniDaIdTestata(int idCommissione);
        IEnumerable<PraticaCommissioneBreveDto> CaricaPraticheCommissione(int idCommissione, int codiceAnagrafe);
        IEnumerable<AllegatoCommissioneDao> CaricaDocumentiCommissione(int idCommissione, int idAppello);
        PraticaCommissioneEstesaDto CaricaPratica(int idCommissione, int codiceAnagrafe, int codiceIstanza);
        bool PraticaAperta(int idCommissione, int codiceIstanza);
        bool CommissioneAperta(int idCommissione);

        /// <summary>
        /// Recupera l'id appello a partire dal codice anagrafe.
        /// Testa prima per codice anagrafe invitato e poi per appartenenza ad un'amministrazione
        /// </summary>
        /// <param name="idCommissione"></param>
        /// <param name="codiceAnagrafe"></param>
        /// <returns></returns>
        int GetIdAppelloByCodiceAnagrafe(int idCommissione, int codiceAnagrafe);
        int? GetIdAppelloByCodiceAnagrafeInvitato(int idCommissione, int codiceAnagrafe);
        void ApprovaAllegato(int idAppello, int idAllegato, string sha256);
    }
}
