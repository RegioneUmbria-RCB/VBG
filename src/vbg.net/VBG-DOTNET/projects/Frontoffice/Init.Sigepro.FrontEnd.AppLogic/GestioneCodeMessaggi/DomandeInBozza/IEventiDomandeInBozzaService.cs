using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza
{
    public interface IEventiDomandeInBozzaService
    {
        bool RabbitAbilitato { get; }

        void DomandaInBozzaModificata(string codiceFiscaleUtente, IDomandaOnlineReadInterface domanda);

        void DomandaInBozzaPresentata(IDomandaOnlineReadInterface domanda);

        void DomandaInBozzaEliminata(IDomandaOnlineReadInterface domanda);
    }
}
