using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneDelegaATrasmettere
{
    public interface IDelegaATrasmettereService
    {
        void EliminaDelegaATrasmettere(int idDomanda);
        void EliminaDocumentoIdentita(int idDomanda);
        void SalvaAllegato(int idDomanda, BinaryFile file, bool verificaFirma);
        void SalvaAllegato(int idDomanda, int codiceOggetto);
        void SalvaDocumentoIdentita(int idDomanda, BinaryFile file, bool verificaFirma = false);
        void SalvaDocumentoIdentita(int idDomanda, int codiceOggetto);
    }
}