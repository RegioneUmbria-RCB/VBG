using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Pagamenti;
using Init.Sigepro.FrontEnd.Pagamenti.MIP;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP
{
    public class PagamentiMipSettingsReader : IPagamentiSettingsReader
    {
        IConfigurazione<ParametriConfigurazionePagamentiMIP> _cfg;

        public PagamentiMipSettingsReader(IConfigurazione<ParametriConfigurazionePagamentiMIP> cfg)
        {
            this._cfg = cfg;
        }

        public PagamentiSettings GetSettings()
        {
            return this._cfg.Parametri;
        }
    }
}
