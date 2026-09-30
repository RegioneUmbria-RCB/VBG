using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneDatiExtra
{
    public class DatiExtraService : IDatiExtraService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;

        public DatiExtraService(ISalvataggioDomandaStrategy salvataggioStrategy)
        {
            this._salvataggioStrategy = salvataggioStrategy;
        }

        public T Get<T>(int idPresentazione, string chiave) where T : class
        {
            var domanda = this._salvataggioStrategy.GetById(idPresentazione);

            return domanda.ReadInterface.DatiExtra.Get<T>(chiave);
        }

        public void Set<T>(int idPresentazione, string chiave, T valore) where T : class
        {
            var domanda = this._salvataggioStrategy.GetById(idPresentazione);

            domanda.WriteInterface.DatiExtra.Set(chiave, valore);

            this._salvataggioStrategy.Salva(domanda);
        }

        public async Task<T> GetAsync<T>(int idPresentazione, string chiave) where T : class
        {
            var domanda = await this._salvataggioStrategy.GetByIdAsync(idPresentazione);

            return domanda.ReadInterface.DatiExtra.Get<T>(chiave);
        }

        public async Task SetAsync<T>(int idPresentazione, string chiave, T valore) where T : class
        {
            var domanda = await this._salvataggioStrategy.GetByIdAsync(idPresentazione);

            domanda.WriteInterface.DatiExtra.Set(chiave, valore);

            await this._salvataggioStrategy.SalvaAsync(domanda);
        }
    }
}
