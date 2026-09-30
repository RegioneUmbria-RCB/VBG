using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFiltroInterventiAlbero
{
    public class FiltroInterventiAlberoService
    {
        private static class Constants
        {
            public const string DatiExtraKey = "FiltroInterventiAlberoService.IdInterventoRoot";
        }

        public class IdInterventoStore
        {
            public int IdIntervento { get; set; }
        }

        private readonly DomandeOnlineService _domandeOnlineService;

        public FiltroInterventiAlberoService(DomandeOnlineService domandeOnlineService)
        {
            this._domandeOnlineService = domandeOnlineService;
        }

        public void ImpostaFiltroInterventi(int idDomanda, int idInterventoRoot)
        {
            var domanda = this._domandeOnlineService.GetById(idDomanda);

            domanda.WriteInterface.DatiExtra.Set(Constants.DatiExtraKey, new IdInterventoStore
            {
                IdIntervento = idInterventoRoot
            });

            this._domandeOnlineService.SalvaDomanda(domanda);
        }

        public int? GetFiltroInterventi(int idDomanda)
        {
            var domanda = this._domandeOnlineService.GetById(idDomanda);

            var store = domanda.ReadInterface.DatiExtra.Get<IdInterventoStore>(Constants.DatiExtraKey);

            return store?.IdIntervento;
        }
    }
}
