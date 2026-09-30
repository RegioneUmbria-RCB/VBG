namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions
{
    public interface IMenuVersionUpgrader
    {
        MenuFile Upgrade(MenuFile oldFile);
    }
}
