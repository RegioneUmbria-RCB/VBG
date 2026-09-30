using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V3;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade
{
    public class MenuUpgrader : IMenuUpgrader
    {
        private readonly int VersioneAttuale = -1;

        private readonly Dictionary<int, IMenuVersionUpgrader> _upgraders = new Dictionary<int, IMenuVersionUpgrader>();

        public MenuUpgrader(V1ToV2MenuUpgrader v1toV2, V2ToV3MenuUpgrader v2ToV3, IConfigurazione<ParametriAreaRiservataCore> config)
        {
            this._upgraders.Add(1, v1toV2);

#if !NET48
                this._upgraders.Add(2, v2ToV3);
#endif

            this.VersioneAttuale = this._upgraders.Keys.Max(x => x) + 1;
        }

        public MenuUpgradeResult Upgrade(MenuFile file)
        {
            var hasBeenUpgraded = false;

            while (file.Versione < this.VersioneAttuale)
            {
                file = this._upgraders[file.Versione].Upgrade(file);
            }

            return new MenuUpgradeResult
            {
                HasBeenUpgraded = hasBeenUpgraded,
                File = file
            };
        }
    }
}
