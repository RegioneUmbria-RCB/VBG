using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Ricerche;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using VBG.DatiDinamici.Agid.WebControls.Controlli.DatiDinamiciSearch;

namespace Init.Sigepro.FrontEnd.CoreServices.Shared
{
    public class DOLDatiDinamiciSearchService : IDatiDinamiciSearchService
    {
        private readonly IRicercheDatiDinamiciService _ricercheDatiDinamiciService;

        public DOLDatiDinamiciSearchService(IRicercheDatiDinamiciService ricercheDatiDinamiciService)
        {
            this._ricercheDatiDinamiciService = ricercheDatiDinamiciService;
        }

        public GetCompletionListResult GetCompletionList(ProprietaCampoRicerca proprietaCampo, string partial, IEnumerable<ValoreFiltroRicerca> filtri)
        {
            var filtriAppLogic = filtri.Select(x => new ValoreFiltroRicercaDto
            {
                nome = x.NomeCampo,
                val = x.Valore
            }).ToArray();

            var result = this._ricercheDatiDinamiciService.GetCompletionList2(proprietaCampo.Id, partial, filtriAppLogic);

            if (result is null || result.TotaleRisultatiTrovati == 0)
            {
                return GetCompletionListResult.Empty;
            }

            var elementi = result.Risultati.Select(x => new RisultatoRicercaDatiDinamici(x.Value, x.Label)).ToArray();

            return new GetCompletionListResult(elementi, result.TotaleRisultatiTrovati);

        }

        public RisultatoRicercaDatiDinamici? InitializeControl(ProprietaCampoRicerca proprietaCampo, string valoreIniziale)
        {
            var result = this._ricercheDatiDinamiciService.InitializeControl(proprietaCampo.Id, valoreIniziale);

            if (result is null)
            {
                return null;
            }

            return new RisultatoRicercaDatiDinamici(result.Value, result.Label);
        }
    }
}
