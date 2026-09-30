using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class MenuWriter : IMenuWriter
    {
        private readonly IOggettiService _oggettiService;
        private readonly IConfigurazione<ParametriMenuV2> _configurazione;

        public MenuWriter(IOggettiService oggettiService, IConfigurazione<ParametriMenuV2> configurazione)
        {
            this._oggettiService = oggettiService;
            this._configurazione = configurazione;
        }
        public void UpdateConfigurationFile(MenuFile menuFile)
        {
            if (this._configurazione.Parametri.CodiceOggettoMenu.HasValue)
            {
                this._oggettiService.AggiornaOggetto(this._configurazione.Parametri.CodiceOggettoMenu.Value, menuFile.BinaryContent);
            }
        }
    }
}
