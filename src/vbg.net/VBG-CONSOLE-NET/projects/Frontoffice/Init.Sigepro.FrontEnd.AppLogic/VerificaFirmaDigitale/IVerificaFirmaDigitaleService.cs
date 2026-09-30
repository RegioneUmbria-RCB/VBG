using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{

    public interface IVerificaFirmaDigitaleService
    {
        EsitoVerificaFirmaDigitale VerificaFirmaDigitale(BinaryFile file);
        EsitoVerificaFirmaDigitale VerificaFirmaDigitale(int codiceOggetto);
    }

}
