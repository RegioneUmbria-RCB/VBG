using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Logic.GestioneDomandaOnLine
{
    public interface IEliminaDomandaService
    {
        void EliminaDomanda(int idDomanda, IEventPublisher eventPublisher);

    }
}
