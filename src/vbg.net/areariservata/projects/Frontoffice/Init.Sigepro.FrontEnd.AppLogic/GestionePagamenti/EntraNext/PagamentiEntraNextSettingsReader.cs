using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Pagamenti;
using Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext
{
    public class PagamentiEntraNextSettingsReader : IPagamentiEntraNextSettingsReader
    {
        IConfigurazione<ParametriConfigurazionePagamentiEntraNext> _cfg;

        public PagamentiEntraNextSettingsReader(IConfigurazione<ParametriConfigurazionePagamentiEntraNext> cfg)
        {
            this._cfg = cfg;
        }

        public PaymentSettingsEntraNext GetSettings()
        {
            return this._cfg.Parametri;
        }
    }
}