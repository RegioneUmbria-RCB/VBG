using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneDatiExtra
{
    public class DatiExtraService : IDatiExtraService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;

        public DatiExtraService(ISalvataggioDomandaStrategy salvataggioStrategy)
        {
            _salvataggioStrategy = salvataggioStrategy;
        }

        public T Get<T>(int idPresentazione, string chiave) where T : class
        {
            var domanda = _salvataggioStrategy.GetById(idPresentazione);

            return domanda.ReadInterface.DatiExtra.Get<T>(chiave);
        }

        public void Set<T>(int idPresentazione, string chiave, T valore) where T : class
        {
            var domanda = _salvataggioStrategy.GetById(idPresentazione);

            domanda.WriteInterface.DatiExtra.Set(chiave, valore);

            _salvataggioStrategy.Salva(domanda);
        }
    }
}
