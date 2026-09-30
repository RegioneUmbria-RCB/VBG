using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class MenuService
    {
        MenuReader _menuReader;
        MenuUpgrader _menuUpgrader;

        public MenuService(MenuReader menuReader, MenuUpgrader menuUpgrader)
        {
            this._menuReader = menuReader;
            this._menuUpgrader = menuUpgrader;
        }

        public MainMenu LoadMenu()
        {
            var menuFile = this._menuReader.Read();

            return this._menuUpgrader.Upgrade(menuFile);
        }
    }
}
