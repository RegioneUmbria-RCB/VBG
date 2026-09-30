using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda
{

    public interface IAllegatiDomandaFoRepository
    {
        void EliminaAllegato(int idDomanda, int idAllegato);
        BinaryFile LeggiAllegato(int idDomanda, int idAllegato);
        SalvataggioAllegatoResult SalvaAllegato(int idDomanda, BinaryFile file, bool richiedeFirmaDigitale);
        SalvataggioAllegatoResult SalvaAllegato(int idDomanda, int codiceOggetto);
        Task<SalvataggioAllegatoResult> SalvaAllegatoAsync(int idDomanda, BinaryFile file, bool richiedeFirmaDigitale);
        Task<SalvataggioAllegatoResult> SalvaAllegatoAsync(int idDomanda, int codiceOggetto);
        bool ConfrontaHash(BinaryFile file, string hashConfronto);
        SalvataggioAllegatoResult SalvaAllegatoConfrontaHash(int idDomanda, BinaryFile file, string hashConfronto);
        string LeggiChecksumAllegato(int codiceOggetto);
    }
}
