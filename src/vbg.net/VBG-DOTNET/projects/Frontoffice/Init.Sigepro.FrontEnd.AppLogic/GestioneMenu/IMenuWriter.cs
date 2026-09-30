using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public interface IMenuWriter
    {
        void UpdateConfigurationFile(MenuFile menuFile);
    }
}
