using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.Modena
{
    public interface ILocalizzazioniModenaService
    {
        void AggiornaLocalizzazioniModenaDaMappa(int idDomanda, LocalizzazioniMappaModenaSelezionate localizzazioniSelezionate, OpzioniSalvataggioLocalizzazioniModena opzioni);
        string GetStringaArrayParticelleSelezionate(int idDomanda, string codiceCatasto);
        string GetStringaArrayParticelleSelezionateCore(int idDomanda, string codiceCatasto);
    }
}
