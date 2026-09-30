using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V3;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.IoC
{
    public static class MenuNinjectModule
    {
        public static IDIProvider ConfiguraMenu(this IDIProvider k)
        {
            k.AddScoped<IMenuService, CachedMenuService>();
            k.AddScoped<IMenuReader, MenuReader>();
            k.AddScoped<IMenuUrlBuilder, MenuUrlBuilder>();
            k.AddScoped<IMenuUpgrader, MenuUpgrader>();
            k.AddScoped<IMenuWriter, MenuWriter>();
            k.AddScoped<IMenuLoader, MenuLoader>();

            k.AddScoped<V1ToV2MenuUpgrader>();
            k.AddScoped<V2ToV3MenuUpgrader>();
            k.AddScoped<IFrameworkToCoreUrlMapper, FrameworkToCoreUrlMapper>();
            k.AddScoped<IGlyphiconToFontAwesomeIconMapper, GlyphiconToFontAwesomeIconMapper>();

            return k;
        }
    }
}
