using Init.SIGePro.Manager.DTO.Commissioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni
{
    public interface ICommissioniService
    {
        IEnumerable<CommissioneTestataDto> GetCommissioniAperteByCodiceAnagrafe(int codiceAnagrafe);

        DettaglioCommissioneDto GetDettaglioCommissione(int idCommissione, int codiceAnagrafe);

        PraticaCommissioneEstesaDto GetDettaglioPratica(int idCommissione, int codiceAnagrafe, int codiceIstanza);

        bool ApprovaAllegato(int idCommissione, int idAllegato, int codiceAnagrafe);

        bool AccessoAFile(int idCommissione, int codiceAnagrafe, int codiceIstanza, int codiceOggetto);
    }
}
