using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    public interface IAnagraficheWriteInterface
    {
        void CollegaAziendaAdAnagrafica(int idAnagrafica, int idAziendaCollegata);
        void Elimina(int idAnagrafica);
        void EliminaTutto();
        /*AnagraficaDomanda*/
        void Crea(TipoPersonaEnum tipoPersona, string codiceFiscale);
        void AggiungiOAggiorna(AnagraficaDomanda row, ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazione);
        void Sincronizza(ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazione);
        void VerificaFlagsCittadiniExtracomunitari(ICittadinanzeService cittadinanzeService);
        void AggiungiAnagraficaConSoggettoCollegato(AnagraficaDomanda anagrafica, AnagraficaDomanda anagraficaCollegata, ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazione);
        void CopiaAnagraficheDaDomanda(DomandaOnline domandaOrigine, IEnumerable<IAnagraficaDaCopiare> anagrafiche);
    }
}
