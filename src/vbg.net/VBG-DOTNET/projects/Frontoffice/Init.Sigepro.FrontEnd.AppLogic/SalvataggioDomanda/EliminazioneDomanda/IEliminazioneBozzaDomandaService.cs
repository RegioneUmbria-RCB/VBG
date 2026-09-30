using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories;
using System.Threading;

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.EliminazioneDomanda
{
    public interface IEliminazioneBozzaDomandaService
    {
        void Elimina(IDomandaOnlineReadInterface domanda);
    }

    public class EliminazioneDomandaService : IEliminazioneBozzaDomandaService
    {
        private readonly IDatiDomandaFoRepository _datiDomandaFoRepository;
        private readonly IEventiDomandeInBozzaService _eventiDomandeInBozzaService;

        public EliminazioneDomandaService(IDatiDomandaFoRepository datiDomandaFoRepository, IEventiDomandeInBozzaService eventiDomandeInBozzaService)
        {
            this._datiDomandaFoRepository = datiDomandaFoRepository;
            this._eventiDomandeInBozzaService = eventiDomandeInBozzaService;
        }

        public void Elimina(IDomandaOnlineReadInterface domanda)
        {
            if (this._eventiDomandeInBozzaService.RabbitAbilitato)
            {
                this._eventiDomandeInBozzaService.DomandaInBozzaEliminata(domanda);

                // TODO: Attendere che la domanda sia effettivamente eliminata...
                var counter = 0;

                while (counter++ < 10)
                {
                    var eliminata = this._datiDomandaFoRepository.DomandaEliminata(domanda.AltriDati.IdPresentazione);

                    if (eliminata)
                    {
                        return;
                    }

                    Thread.Sleep(counter * 100);
                }
            }
            else
            {
                this._datiDomandaFoRepository.Elimina(domanda.AltriDati.IdPresentazione);
            }
        }
    }
}
