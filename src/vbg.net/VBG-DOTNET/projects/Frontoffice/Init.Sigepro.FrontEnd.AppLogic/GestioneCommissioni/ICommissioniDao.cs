using Init.SIGePro.Manager.DTO.Commissioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni
{
    public interface ICommissioniDao
    {
        IEnumerable<CommissioneTestataDto> GetCommissioniAperteByCodiceAnagrafe(int codiceAnagrafe);

        DettaglioCommissioneDto GetDettaglioCommissione(int idCommissione, int codiceAnagrafe);

        PraticaCommissioneEstesaDto GetDettaglioPratica(int idCommissione, int codiceAnagrafe, string uuidIstanza);
        bool VerificaAccessoAFile(int idCommissione, int codiceAnagrafe, string uuidIstanza, int codiceOggetto);
        bool ApprovaAllegato(int idCommissione, int idAllegato, int codiceAnagrafe);
    }
}
