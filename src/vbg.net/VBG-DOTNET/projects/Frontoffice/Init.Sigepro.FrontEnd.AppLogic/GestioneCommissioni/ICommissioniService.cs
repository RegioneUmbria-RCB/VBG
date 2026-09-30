using Init.SIGePro.Manager.DTO.Commissioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni
{
    public interface ICommissioniService
    {
        IEnumerable<CommissioneTestataDto> GetCommissioniApertePerUtenteCorrente();

        DettaglioCommissioneDto GetDettaglioCommissionePerUtenteCorrente(int idCommissione);

        PraticaCommissioneEstesaDto GetDettaglioPraticaPerUtenteCorrente(int idCommissione, string uuidIstanza);

        bool VerificaAccessoAFilePerUtenteCorrente(int idCommissione, string uuidIstanza, int codiceOggetto);

        bool ApprovaAllegatoPerUtenteCorrente(int value, int idAllegato);
    }
}
