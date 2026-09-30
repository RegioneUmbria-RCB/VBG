using Init.Sigepro.FrontEnd.AppLogic.RicercaPraticheWs;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRicercaPratiche
{
    public interface IRicercaPraticheService
    {
        RisultatoRicercaPratiche TrovaPraticaDaEstremiProtocollo(int idDomanda, string numeroProtocollo, DateTime dataProtocollo);
        RisultatoRicercaPratiche TrovaPraticaDaNumeroIstanza(int idDomanda, string numeroIstanza);
        void IgnoraRicercaPratiche(int idDomanda);
        void ImpostaDatiPraticaDaRisultatoRicerca(int idDomanda, RisultatoRicercaPratiche risultatoRicerca, CopiaDatiDomandaFlags flags);
        RisultatoRicercaPratiche GetRisultatoRicerca(int idDomanda);
        bool RisultatoRicercaPresente(int idDomanda);
    }
}