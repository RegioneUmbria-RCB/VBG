using Init.SIGePro.Manager.DTO.DatiDinamici;
using System.Collections.Generic;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{

    public interface IDatiDinamiciRepository
    {
        ListaModelliDinamiciDomandaDto GetSchedeDaInterventoEEndo(int intervento, IEnumerable<int> endo, IEnumerable<string> tipiLocalizzazioni, UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche usaTipiLocalizzazioni);
        RisultatoRicercaDatiDinamiciDto InitializeControl(int idCampo, string value);
        RisultatoRicercaDatiDinamiciDto[] GetCompletionList(int idCampo, string partial, ValoreFiltroRicercaDto[] filtri);
        AutocompleteSearchResultDto GetCompletionList2(int idCampo, string partial, ValoreFiltroRicercaDto[] filtri);
    }
}
