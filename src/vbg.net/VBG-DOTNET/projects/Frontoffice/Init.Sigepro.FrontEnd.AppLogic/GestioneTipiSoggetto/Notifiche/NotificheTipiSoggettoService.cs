using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto.Notifiche
{
    public class NotificheTipiSoggettoService : INotificheTipiSoggettoService
    {
        private readonly TipiSoggettoServiceCreator _serviceCreator;
        private readonly ISalvataggioDomandaStrategy _datiDomandaService;

        public NotificheTipiSoggettoService(TipiSoggettoServiceCreator serviceCreator, ISalvataggioDomandaStrategy datiDomandaService)
        {
            this._serviceCreator = serviceCreator;
            this._datiDomandaService = datiDomandaService;
        }

        public async Task<IEnumerable<string>> GetListaNominativiPerNotificheAsync(int idDomanda)
        {
            var domanda = await this._datiDomandaService.GetByIdAsync(idDomanda);

            var soggetti = domanda.ReadInterface.Anagrafiche.Anagrafiche.Select(x => new
            {
                IdTipoSoggetto = x.TipoSoggetto?.Id,
                Nominativo = x.ToString(),
            })
                .Where(x => x.IdTipoSoggetto != null)
                .GroupBy(x => x.IdTipoSoggetto.Value)
                .ToDictionary(x => x.Key, x => x.Select(it => it.Nominativo).ToList());

            var tipiSoggettoConNotifiche = await this._serviceCreator.CallAsync((ws) => ws.Service.GetIdTipiSoggettoCheRicevonoNotificheAsync(ws.Token, soggetti.Keys.ToArray()));
            var rVal = new List<string>();

            foreach (var idTipoSoggetto in tipiSoggettoConNotifiche)
            {
                rVal.AddRange(soggetti[idTipoSoggetto]);
            }

            return rVal;
        }
    }
}
