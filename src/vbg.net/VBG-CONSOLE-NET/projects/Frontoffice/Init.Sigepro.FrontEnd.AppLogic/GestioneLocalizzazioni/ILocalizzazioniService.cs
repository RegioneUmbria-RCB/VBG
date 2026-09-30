using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using System;
using System.Collections.Generic;
namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
    interface ILocalizzazioniService
    {
        void AggiungiLocalizzazione(int idDomanda, NuovaLocalizzazione localizzazione, NuovoRiferimentoCatastale riferimentiCatastali = null);
        void AssegnaRiferimentiCatastaliALocalizzazione(int idDomanda, int idLocalizzazione, NuovoRiferimentoCatastale riferimentiCatastali);
        void EliminaLocalizzazione(int idDomanda, int idLocalizzazione);
        void EliminaRiferimentiCatastali(int idDomanda, int idRiferimentoCatastale);
        IEnumerable<StradarioDto> FindByMatchParziale(string aliasComune, string codiceComune, string comuneLocalizzazione, string indirizzo);
        StradarioDto GetById(int id);
        StradarioDto GetIndirizzoByCodViario(string codViario);
    }
}
