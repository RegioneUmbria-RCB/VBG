using Init.SIGePro.Manager.DTO.DatiDinamici;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Ricerche
{
    public interface IRicercheDatiDinamiciService
    {
        RisultatoRicercaDatiDinamiciDto InitializeControl(int idCampo, string value);
        RisultatoRicercaDatiDinamiciDto[] GetCompletionList(int idCampo, string partial, ValoreFiltroRicercaDto[] filtri);
        AutocompleteSearchResultDto GetCompletionList2(int idCampo, string partial, ValoreFiltroRicercaDto[] filtri);

    }
}
