using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda.CopiaDomanda
{
    public class CopiaDatiDomandaService : ICopiaDatiDomandaService
    {
        ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public CopiaDatiDomandaService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }

        public void CopiaDatiDomanda(int idDomandaOrigine, int idDomandaDestinazione, ElementiDaCopiare elementiDaCopiare)
        {
            var domandaOrigine = this._salvataggioDomandaStrategy.GetById(idDomandaOrigine);
            var domandaDestinazione = this._salvataggioDomandaStrategy.GetById(idDomandaDestinazione);

            // Copia anagrafiche
            domandaDestinazione.WriteInterface.Anagrafiche.CopiaAnagraficheDaDomanda(domandaOrigine, elementiDaCopiare.Anagrafiche);

            // Copia Documenti
            domandaDestinazione.WriteInterface.Documenti.CopiaDocumentiDaDomanda(domandaOrigine, elementiDaCopiare.Allegati.Select(x => x.Id));

            this._salvataggioDomandaStrategy.Salva(domandaDestinazione);
        }
    }
}
