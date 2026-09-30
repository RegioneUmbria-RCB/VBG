using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.Backend;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Anagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
    public class RicercheAnagraficheService : IRicercheAnagraficheService
    {
        private readonly ILoggerFactory _loggerFactory;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IAnagraficheBackendService _anagraficheBackendService;
        private readonly IComuniService _comuniService;
        private readonly IOnceOnlyAnagraficheService _onceOnlyService;

        public RicercheAnagraficheService(ILoggerFactory loggerFactory, ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAnagraficheBackendService anagraficheBackendService,
            IComuniService comuniService, IOnceOnlyAnagraficheService onceOnlyService)
        {
            this._loggerFactory = loggerFactory;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._anagraficheBackendService = anagraficheBackendService;
            this._comuniService = comuniService;
            this._onceOnlyService = onceOnlyService;
        }

        public AnagraficaDomanda RicercaAnagrafica(int idDomanda, TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva, bool ignoraRicercaBackofficePerPersoneFisiche)
        {
            var finder = this.CreateFinder(idDomanda, tipoPersona, ignoraRicercaBackofficePerPersoneFisiche);

            return finder.Find(tipoPersona, codiceFiscalePartitaIva);
        }

        private AbstractAnagrafeFinder CreateFinder(int idDomanda, TipoPersonaEnum tipoPersona, bool ignoraRicercaBackofficePerPersoneFisiche)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
            var finders = new List<AbstractAnagrafeFinder>
            {
                new DomandaAnagrafeFinder(domanda.ReadInterface.Anagrafiche)
            };

            if (tipoPersona == TipoPersonaEnum.Giuridica)
            {
                finders.Add(new BackendAnagrafeFinder(this._anagraficheBackendService, this._comuniService));
            }
            else
            {
                if (this._onceOnlyService.IsOnceOnlyAttivo)
                {
                    finders.Add(new OnceOnlyAnagrafeFinder(this._onceOnlyService, this._comuniService));
                }
                else
                {
                    if (!ignoraRicercaBackofficePerPersoneFisiche)
                    {
                        finders.Add(new BackendAnagrafeFinder(this._anagraficheBackendService, this._comuniService));
                    }
                }
            }

            finders.Add(new NuovaAnagraficaFinder(domanda));

            var finder = new CompositeAnagrafeFinder(this._loggerFactory, finders);

            return finder;
        }

        public async Task<AnagraficaDomanda> RicercaAnagraficaAsync(int idDomanda, TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva, bool ignoraRicercaBackofficePerPersoneFisiche)
        {
            var finder = this.CreateFinder(idDomanda, tipoPersona, ignoraRicercaBackofficePerPersoneFisiche);

            return await finder.FindAsync(tipoPersona, codiceFiscalePartitaIva);
        }
    }
}
