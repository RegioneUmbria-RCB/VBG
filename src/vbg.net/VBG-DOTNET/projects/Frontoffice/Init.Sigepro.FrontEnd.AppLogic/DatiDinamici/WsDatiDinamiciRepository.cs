using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.WsVbgDatiDinamici;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    internal class WsDatiDinamiciRepository : IDatiDinamiciRepository, IStrutturaModelloDinamicoRepository
    {
        private readonly IAliasResolver _aliasResolver;
        private readonly WsDatiDinamiciServiceCreator _serviceCreator;
        private readonly IContextCache _sessionCache;

        public WsDatiDinamiciRepository(IAliasResolver aliasResolver, WsDatiDinamiciServiceCreator sc, IContextCache sessionCache)
        {
            this._aliasResolver = aliasResolver;
            this._serviceCreator = sc;
            this._sessionCache = sessionCache;
        }

        public ListaModelliDinamiciDomandaDto GetSchedeDaInterventoEEndo(int intervento, IEnumerable<int> endo, IEnumerable<string> tipiLocalizzazioni, UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche usaTipiLocalizzazioni)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                var req = new GetModelliDinamiciDaInterventoEEndoRequest
                {
                    CodiceIntervento = intervento,
                    ListaEndo = endo.ToArray(),
                    ListaTipiLocalizzazioni = tipiLocalizzazioni.ToArray(),
                    IgnoraTipiLocalizzazione = usaTipiLocalizzazioni == UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No
                };

                return ws.Service.GetModelliDinamiciDaInterventoEEndo(ws.Token, req);
            }
        }

        public RisultatoRicercaDatiDinamiciDto[] GetCompletionList(int idCampo, string partial, ValoreFiltroRicercaDto[] filtri)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetCompletionListRicerchePlus(ws.Token, idCampo, partial, filtri).Risultati;
            }
        }

        public AutocompleteSearchResultDto GetCompletionList2(int idCampo, string partial, ValoreFiltroRicercaDto[] filtri)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetCompletionListRicerchePlus(ws.Token, idCampo, partial, filtri);
            }
        }

        public RisultatoRicercaDatiDinamiciDto InitializeControl(int idCampo, string value)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.InitializeControlRicerchePlus(ws.Token, idCampo, value);
            }
        }

        public IStrutturaModelloDinamico GetStrutturaModelloDinamico(int idModello)
        {
            var cacheKey = $"{this._aliasResolver.AliasComune}:{idModello}";

            return this._sessionCache.GetOrAdd(cacheKey, () =>
            {
                using (var ws = this._serviceCreator.CreateClient())
                {
                    var struttura = ws.Service.GetStrutturaModelloDinamico(ws.Token, idModello);

                    struttura.Modello.FlgStoricizza = 0;

                    return struttura.ToStrutturaModelloDinamico();
                }
            });


        }
    }
}
