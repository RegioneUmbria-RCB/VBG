using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class CachedMenuService : MenuServiceV2
    {
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IContextCache _applicationCache;

        public CachedMenuService(IAliasSoftwareResolver aliasSoftwareResolver, IContextCache applicationCache, IMenuReader menuReader, IMenuUpgrader menuUpgrader, IMenuWriter menuWriter, IMenuLoader menuLoader)
            : base(menuReader, menuUpgrader, menuWriter, menuLoader)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._applicationCache = applicationCache;
        }

        public override MenuModel LoadMenu()
        {
            var cacheKey = $"Menu.{this._aliasSoftwareResolver.AliasComune}-{this._aliasSoftwareResolver.Software}";

            return this._applicationCache.GetOrAdd(cacheKey, () => base.LoadMenu());
        }
    }
}
