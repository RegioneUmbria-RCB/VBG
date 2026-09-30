using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V2;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V3
{
    public class V2ToV3MenuUpgrader : IMenuVersionUpgrader
    {
        private readonly IFrameworkToCoreUrlMapper _urlMapper;

        public V2ToV3MenuUpgrader(IFrameworkToCoreUrlMapper urlMapper)
        {
            this._urlMapper = urlMapper;
        }

        public MenuFile Upgrade(MenuFile oldFile)
        {
            var old = MainMenuV2.FromMenuFile(oldFile);

            var newMenu = new MainMenuV3()
            {
                Descrizione = old.Descrizione,
                MenuUtente = old.MenuUtente.Select(x => new MenuItemV3(x, this._urlMapper)).ToList(),
                MenuDestra = old.MenuDestra.Select(x => new SezioneMenuV3(x, this._urlMapper)).ToList(),
                Sezioni = old.Sezioni.Select(x => new SezioneMenuV3(x, this._urlMapper)).ToList(),
            };

            return newMenu.ToMenuFile();

        }

    }
}
