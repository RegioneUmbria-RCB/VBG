using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V3;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class MenuLoader : IMenuLoader
    {
        private readonly IMenuUrlBuilder _menuUrlBuilder;
        private readonly IConfigurazione<ParametriAreaRiservataCore> _configurazione;
        private IGlyphiconToFontAwesomeIconMapper _glyphiconToFontAwesomeIconMapper;

        public MenuLoader(IMenuUrlBuilder menuUrlBuilder, IConfigurazione<ParametriAreaRiservataCore> configurazione, IGlyphiconToFontAwesomeIconMapper glyphiconToFontAwesomeIconMapper)
        {
            this._menuUrlBuilder = menuUrlBuilder;
            this._configurazione = configurazione;
            this._glyphiconToFontAwesomeIconMapper = glyphiconToFontAwesomeIconMapper;
        }

        public MenuModel Load(MenuFile file)
        {
#if NET48
            return MainMenuV2.FromMenuFile(file).ToMenuModel(this._menuUrlBuilder);
#else
            return MainMenuV3.FromMenuFile(file).ToMenuModel(this._menuUrlBuilder, this._glyphiconToFontAwesomeIconMapper);
#endif
        }
    }
}
