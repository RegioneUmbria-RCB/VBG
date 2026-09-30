using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Common.RicercheStradario
{
    internal class CompositeRicercaIndirizzo : IRicercaIndirizzo
    {
        IEnumerable<IRicercaIndirizzo> _providers;

        internal CompositeRicercaIndirizzo(IEnumerable<IRicercaIndirizzo> providers)
        {
            this._providers = providers;
        }

        public IEnumerable<StradarioDto> Cerca(string testo)
        {
            foreach (var provider in this._providers)
            {
                var result = provider.Cerca(testo);

                if (result.Count() > 0)
                {
                    return result;
                }
            }

            return Enumerable.Empty<StradarioDto>();
        }
    }
}
