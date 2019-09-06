using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public interface IAnagraficheWriteInterface
	{
		void CollegaAziendaAdAnagrafica(int idAnagrafica, int idAziendaCollegata);
		void Elimina(int idAnagrafica);
		/*AnagraficaDomanda*/ 
		void Crea(TipoPersonaEnum tipoPersona, string codiceFiscale);
		void AggiungiOAggiorna(AnagraficaDomanda row, ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazione);
		void Sincronizza(ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazione);
		void VerificaFlagsCittadiniExtracomunitari(ICittadinanzeService cittadinanzeService);
		void AggiungiAnagraficaConSoggettoCollegato(AnagraficaDomanda anagrafica, AnagraficaDomanda anagraficaCollegata, ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazione);
        void CopiaAnagraficheDaDomanda(DomandaOnline domandaOrigine, IEnumerable<IAnagraficaDaCopiare> anagrafiche);
    }
}
