using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public interface IMenuLoader
    {
        MenuModel Load(MenuFile file);
    }
}
