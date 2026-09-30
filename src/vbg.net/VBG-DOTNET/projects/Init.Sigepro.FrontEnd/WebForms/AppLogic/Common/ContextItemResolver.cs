using Init.Sigepro.FrontEnd.AppLogic.Common;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Common
{
    internal class ContextItemResolver : CompositeResolver
    {
        private readonly string _itemName;
        private readonly IContextCache _cache;

        public ContextItemResolver(string itemName, IContextCache cache)
            : base(new RouteDataResolver(itemName))
        {
            this._itemName = itemName;
            this._cache = cache;
        }

        protected override string InternalGetValue()
        {
            var item = this._cache.Get<string>(this._itemName);

            return item == null ? string.Empty : item.ToString();
        }
    }
}