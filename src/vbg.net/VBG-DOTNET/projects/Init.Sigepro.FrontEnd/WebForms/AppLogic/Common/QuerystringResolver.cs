using Init.Sigepro.FrontEnd.AppLogic.Common;
using VBG.Shared.Infrastructure.Caching;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Common
{
    internal class QuerystringResolver : CompositeResolver
    {
        private readonly string _itemName;

        public QuerystringResolver(string itemName, IContextCache cache)
            : base(new ContextItemResolver(itemName, cache))
        {
            this._itemName = itemName;
        }

        protected override string InternalGetValue()
        {
            var qs = this.GetFromQuerystring();

            return string.IsNullOrEmpty(qs) ? this.GetFromContext() : qs;
        }

        private string GetFromQuerystring()
        {
            var item = HttpContext.Current.Request.QueryString[this._itemName];

            return item == null ? string.Empty : item.ToString();
        }

        private string GetFromContext()
        {
            var item = HttpContext.Current.Items[this._itemName];

            return item == null ? string.Empty : item.ToString();
        }
    }
}