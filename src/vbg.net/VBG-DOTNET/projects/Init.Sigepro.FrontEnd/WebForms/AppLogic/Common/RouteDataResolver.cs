using Init.Sigepro.FrontEnd.AppLogic.Common;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Common
{
    internal class RouteDataResolver : CompositeResolver
    {
        private readonly string _itemName;

        public RouteDataResolver(string itemName)
            : base(new BaseResolver())
        {
            this._itemName = itemName;
        }

        protected override string InternalGetValue()
        {
            var item = HttpContext.Current.Request.RequestContext.RouteData.Values[this._itemName];

            return item == null ? string.Empty : item.ToString();
        }
    }
}