using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{

    internal class CompositeAnagrafeFinder : AbstractAnagrafeFinder
    {
        private readonly IEnumerable<AbstractAnagrafeFinder> _finders;
        private readonly ILogger<CompositeAnagrafeFinder> _logger;

        public CompositeAnagrafeFinder(ILoggerFactory loggerFactory, IEnumerable<AbstractAnagrafeFinder> finders)
        {
            if (finders == null) //TODO verifica condizione empty
                throw new ArgumentNullException(nameof(finders));

            this._finders = finders;
            this._logger = loggerFactory.CreateLogger<CompositeAnagrafeFinder>();
        }

        internal override AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            foreach (var finder in this._finders)
            {
                this._logger.LogDebug("Ricerca dell'anagrafica {@anagrafica} ({@tipoPersona}) utilizzando il finder {@tipoFinder}", codiceFiscalePartitaIva, tipoPersona, finder.GetType().Name);

                var result = finder.Find(tipoPersona, codiceFiscalePartitaIva);

                if (result != null)
                {
                    this._logger.LogDebug("Anagrafica {@anagrafica} ({@tipoPersona}) trovata utilizzando il finder {@tipoFinder}", codiceFiscalePartitaIva, tipoPersona, finder.GetType().Name);
                    return result;
                }
            }

            this._logger.LogDebug("Anagrafica {@anagrafica} ({@tipoPersona}) NON trovata, verrà sollevata una eccezione", codiceFiscalePartitaIva, tipoPersona);

            throw new InvalidOperationException($"Non è stato possibile trovare un anagrafica per il codice fiscale/p.iva {codiceFiscalePartitaIva} e tipo soggetto {tipoPersona}");
        }

        internal override async Task<AnagraficaDomanda> FindAsync(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
        {
            foreach (var finder in this._finders)
            {
                this._logger.LogDebug("Ricerca dell'anagrafica {@anagrafica} ({@tipoPersona}) utilizzando il finder {@tipoFinder}", codiceFiscalePartitaIva, tipoPersona, finder.GetType().Name);

                var result = await finder.FindAsync(tipoPersona, codiceFiscalePartitaIva);

                if (result != null)
                {
                    this._logger.LogDebug("Anagrafica {@anagrafica} ({@tipoPersona}) trovata utilizzando il finder {@tipoFinder}", codiceFiscalePartitaIva, tipoPersona, finder.GetType().Name);
                    return result;
                }
            }

            throw new InvalidOperationException($"Non è stato possibile trovare un anagrafica per il codice fiscale/p.iva {codiceFiscalePartitaIva} e tipo soggetto {tipoPersona}");
        }
    }
}
