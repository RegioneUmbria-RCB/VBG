using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Anagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Microsoft.VisualStudio.Threading;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
    internal class OnceOnlyAnagrafeFinder : AbstractAnagrafeFinder
    {
        private readonly IOnceOnlyAnagraficheService _onceOnlyService;
        private readonly IComuniService _comuniService;

        public OnceOnlyAnagrafeFinder(IOnceOnlyAnagraficheService onceOnlyService, IComuniService comuniService)
        {
            this._onceOnlyService = onceOnlyService;
            this._comuniService = comuniService;
        }

        internal override AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            var jtf = new JoinableTaskFactory(new JoinableTaskContext());

            return jtf.Run(() => this.FindAsync(tipoPersona, codiceFiscalePartitaIva));
        }

        internal override async Task<AnagraficaDomanda> FindAsync(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            if (tipoPersona != TipoPersonaEnum.Fisica)
            {
                return null;
            }

            var anagrafe = await this._onceOnlyService.TrovaAnagraficaByCodiceFiscaleAsync(tipoPersona, codiceFiscalePartitaIva);

            if (anagrafe == null)
            {
                return null;
            }

            return new AnagrafeAdapter(anagrafe, this._comuniService).ToAnagraficaDomanda();
        }
    }
}