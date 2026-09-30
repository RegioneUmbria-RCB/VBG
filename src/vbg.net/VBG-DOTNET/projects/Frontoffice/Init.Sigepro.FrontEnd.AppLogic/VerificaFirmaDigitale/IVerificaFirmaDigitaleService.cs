using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Collections.Generic;
using System.Threading.Tasks;
using static Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.VerificaFirmaRest.VerificaFirmaDigitaleRestClient;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{

    public interface IVerificaFirmaDigitaleService
    {
        Task<EsitoVerificaFirmaDigitale> VerificaFirmaDigitaleAsync(BinaryFile file);
        EsitoVerificaFirmaDigitale VerificaFirmaDigitale(BinaryFile file);

        Task<EsitoVerificaFirmaDigitale> VerificaFirmaDigitaleAsync(int codiceOggetto);
        EsitoVerificaFirmaDigitale VerificaFirmaDigitale(int codiceOggetto);

        ValidationCfResultDTO VerificaPresenzaSoggetti(BinaryFile file, IEnumerable<string> codiciFiscaliSoggetti);
    }

}
