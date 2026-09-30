using VBG.AppLogic.SSU.DataAccess.Dto;

namespace VBG.AppLogic.SSU.DataAccess
{
    public interface IDomandeSsuRepository
    {
        void MarcaDomandaComePresentata(int idDomanda, string codiceDomandaSsu, string numeroDomandaSsu, string istatEnte);
        FoDomandeSsu? GetByIdDomanda(int idDomanda);
        void AggiornaStatoByIdDomanda(int idDomanda, StatiDomandaSsuEnum statoAsEnum);
        void AggiungiRicevutaByIdDomanda(int idDomanda, int codiceOggettoRicevuta);
        bool AddNewError(int idDomanda, StatiDomandaSsuEnum stato, string errore);
        int CountErroriDomandaPerStato(int idDomanda, StatiDomandaSsuEnum stato);
        void SetNonElaborabile(int idDomanda, bool isElaborabile);
        IEnumerable<ElementoListaDomandeSsuDto> GetDomandeByIdStato(int codiceAnagrafe, string software, StatiDomandaSsuEnum stato);
    }
}
