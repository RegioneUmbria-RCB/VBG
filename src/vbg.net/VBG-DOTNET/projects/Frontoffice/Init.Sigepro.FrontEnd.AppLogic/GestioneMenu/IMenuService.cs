using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public interface IMenuService
    {
        MenuFile GetMenuXml();
        MenuModel LoadMenu();
        void UpdateMenu(MenuFile menuFile);
    }
}
