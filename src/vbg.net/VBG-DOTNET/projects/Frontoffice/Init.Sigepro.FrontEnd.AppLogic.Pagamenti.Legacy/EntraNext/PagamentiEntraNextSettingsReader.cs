using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.Configurazione;
using VBG.Pagamenti.Legacy.ENTRANEXT;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext
{
    public class PagamentiEntraNextSettingsReader : IPagamentiEntraNextSettingsReader
    {
        private readonly IConfigurazione<ParametriConfigurazionePagamentiEntraNext> _cfg;

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