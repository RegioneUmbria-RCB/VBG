using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.FileUpload
{
    public interface IDatiDinamiciFileUploadService
    {
        DatiFileCaricato SalvaAllegato(BinaryFile file, bool verificaFirmaDigitale);
        DatiFileCaricato GetDatiFileCaricato(int codiceOggetto);
    }
}
