using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class MenuServiceV2 : IMenuService
    {
        private readonly IMenuReader _menuReader;
        private readonly IMenuUpgrader _menuUpgrader;
        private readonly IMenuWriter _menuWriter;
        private readonly IMenuLoader _menuLoader;

        public MenuServiceV2(IMenuReader menuReader, IMenuUpgrader menuUpgrader, IMenuWriter menuWriter, IMenuLoader menuLoader)
        {
            this._menuReader = menuReader;
            this._menuUpgrader = menuUpgrader;
            this._menuWriter = menuWriter;
            this._menuLoader = menuLoader;
        }

        public MenuFile GetMenuXml()
        {
            return this._menuReader.Read();
        }

        public virtual MenuModel LoadMenu()
        {
            var file = GetMenuXml();
            var upgradeResult = this._menuUpgrader.Upgrade(file);

            if (upgradeResult.HasBeenUpgraded)
            {
                // Salva nel db il menu xml aggiornato al nuovo formato
                // this._menuWriter.UpdateConfigurationFile(upgradeResult.File);
            }

            return this._menuLoader.Load(upgradeResult.File);
        }

        public void UpdateMenu(MenuFile menuFile)
        {
            this._menuWriter.UpdateConfigurationFile(menuFile);
        }
    }
}
