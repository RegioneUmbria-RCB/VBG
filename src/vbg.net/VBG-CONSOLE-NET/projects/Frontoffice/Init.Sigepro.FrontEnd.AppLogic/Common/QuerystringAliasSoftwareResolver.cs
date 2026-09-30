using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
    internal interface IResolver
    {
        string GetValue();
    }

    internal abstract class CompositeResolver : IResolver
    {
        IResolver _fallback;

        public CompositeResolver(IResolver fallback)
        {
            this._fallback = fallback;
        }

        public string GetValue()
        {
            var value = InternalGetValue();

            if (!String.IsNullOrEmpty(value))
            {
                return value;
            }

            return _fallback.GetValue();
        }

        protected abstract string InternalGetValue();        
    }

    internal class BaseResolver: IResolver
    {
        public string GetValue()
        {
            return string.Empty;
        }
    }

    internal class ContextItemResolver : CompositeResolver
    {
        string _itemName;

        public ContextItemResolver(string itemName)
            :base(new BaseResolver())
        {
            this._itemName = itemName;
        }

        protected override string InternalGetValue()
        {
            var item = HttpContext.Current.Items[this._itemName];

            return item == null ? string.Empty : item.ToString();
        }
    }

    internal class RouteDataResolver : CompositeResolver
    {
        string _itemName;

        public RouteDataResolver(string itemName)
            :base(new ContextItemResolver(itemName))
        {
            this._itemName = itemName;
        }

        protected override string InternalGetValue()
        {
            var item = HttpContext.Current.Request.RequestContext.RouteData.Values[this._itemName];

            return item == null ? string.Empty : item.ToString();
        }
    }

    internal class QuerystringResolver : CompositeResolver
    {
        string _itemName;

        public QuerystringResolver(string itemName)
            :base(new RouteDataResolver(itemName))
        {
            this._itemName = itemName;
        }

        protected override string InternalGetValue()
        {
            var item = HttpContext.Current.Request.QueryString[this._itemName];

            return item == null ? string.Empty : item.ToString();
        }
    }





	internal class QuerystringAliasResolver: IAliasResolver
	{
        string _alias = null;

		private static class Constants
		{
			public const string IdComuneParameterName = "IdComune";
			public const string AliasParameterName = "alias";
		}

		public string AliasComune
		{
			get
			{
                if (!String.IsNullOrEmpty(this._alias))
                    return this._alias;

                this._alias = new QuerystringResolver(Constants.IdComuneParameterName).GetValue();

                if (!string.IsNullOrEmpty(this._alias))
                    return this._alias;

                this._alias = new QuerystringResolver(Constants.AliasParameterName).GetValue();

                if (String.IsNullOrEmpty(this._alias))
					throw new InvalidOperationException("Parametro Idcomune non valido");

                return this._alias;
			}
		}
	}

	internal class QuerystringAliasSoftwareResolver : QuerystringAliasResolver, IAliasSoftwareResolver
	{
        string _software = null;

		private static class Constants
		{
            public const string DefaultSoftware = "SS";
			public const string SoftwareParameterName = "Software";
		}

		#region IAliasSoftwareResolver Members
		
		public string Software
		{
			get
			{
                if (!String.IsNullOrEmpty(this._software))
                {
                    return this._software;
                }

                this._software = new QuerystringResolver(Constants.SoftwareParameterName).GetValue();

                if (String.IsNullOrEmpty(this._software) )
                {
                    this._software = new QuerystringResolver(Constants.SoftwareParameterName.ToLower()).GetValue();
                }

                if (String.IsNullOrEmpty(this._software) && HttpContext.Current.Request.Path.ToUpperInvariant().Contains("/CONTENUTI/"))
                {
                    this._software = Constants.DefaultSoftware;
                }

				if (String.IsNullOrEmpty(this._software))
					throw new InvalidOperationException("Parametro Software non valido");

                return this._software;
			}
		}

		#endregion
	}
}
