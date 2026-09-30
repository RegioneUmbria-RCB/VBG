using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
    public static class IstanzeMappaliExtensions
    {
        public static NuovoRiferimentoCatastale ToNuovoRiferimentoCatastale(this IstanzeMappali istanzeMappali)
        {
            var codTipoCatasto = istanzeMappali.Catasto?.CODICE;
            var tipoCatasto = istanzeMappali.Catasto?.DESCRIZIONE;
            
            return new NuovoRiferimentoCatastale(codTipoCatasto, tipoCatasto, istanzeMappali.Foglio, istanzeMappali.Particella, istanzeMappali.Sub,istanzeMappali.Sezione );

        }
    }
}
