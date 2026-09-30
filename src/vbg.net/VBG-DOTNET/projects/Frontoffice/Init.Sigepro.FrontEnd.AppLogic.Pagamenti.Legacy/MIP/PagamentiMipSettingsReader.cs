using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.Configurazione;
using VBG.Pagamenti.Legacy;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP
{
    public class PagamentiMipSettingsReader : IPagamentiSettingsReader
    {
        private readonly IConfigurazione<ParametriConfigurazionePagamentiMIP> _cfg;

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
