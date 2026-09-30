using Init.SIGePro.Data;

namespace Init.SIGePro.Manager.Logic.GestioneDomandaOnLine
{
    public interface IDomandaOnlineService
    {
        // void EliminaDomanda(int idDomanda);
        FoDomande GetById(int idDomanda);
        void RecuperaDocumentiIstanzaCollegata(int codiceIstanzaOrigine, int idDomandaDestinazione);
    }
}