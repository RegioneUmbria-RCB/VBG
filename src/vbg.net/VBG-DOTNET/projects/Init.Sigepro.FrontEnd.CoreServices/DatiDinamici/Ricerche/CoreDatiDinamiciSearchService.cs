using Init.Sigepro.FrontEnd.AppLogic.DataAccess;
using VBG.DatiDinamici.Agid.WebControls.Controlli.DatiDinamiciSearch;

namespace Init.Sigepro.FrontEnd.CoreServices.DatiDinamici.Ricerche
{
    /// <summary>
    /// Espone i metodi per effettuare ricerche nelle schede dinamiche, utilizzato da arcore e DOL
    /// </summary>
    public class CoreDatiDinamiciSearchService : IDatiDinamiciSearchService
    {
        private readonly DbConnectionFactory _connectionFactory;

        public CoreDatiDinamiciSearchService(DbConnectionFactory connectionFactory)
        {
            this._connectionFactory = connectionFactory;
        }

        public GetCompletionListResult GetCompletionList(ProprietaCampoRicerca proprietaCampo, string partial, IEnumerable<ValoreFiltroRicerca> filtri)
        {
            if (String.IsNullOrEmpty(partial))
            {
                return GetCompletionListResult.Empty;
            }

            using (var db = this._connectionFactory.CreateDatabase())
            {
                var ricercheService = new RicercheVbgHelper(db, this._connectionFactory.IdComune);

                return ricercheService.ExecuteQuery(proprietaCampo, partial, true, filtri);
            }
        }

        public RisultatoRicercaDatiDinamici? InitializeControl(ProprietaCampoRicerca proprietaCampo, string valoreIniziale)
        {
            if (String.IsNullOrEmpty(valoreIniziale))
                return new RisultatoRicercaDatiDinamici("", "");

            using (var db = this._connectionFactory.CreateDatabase())
            {
                var ricercheService = new RicercheVbgHelper(db, this._connectionFactory.IdComune);

                var results = ricercheService
                                .ExecuteQuery(proprietaCampo, valoreIniziale, false, Enumerable.Empty<ValoreFiltroRicerca>(), true);

                var result = results.Risultati
                                    .Select(x => new RisultatoRicercaDatiDinamici(x.Value, x.Label))
                                    .FirstOrDefault();

                return result ?? new RisultatoRicercaDatiDinamici(valoreIniziale, "ERRORE:Impossibile inizializzare il campo");
            }
        }
    }
}
