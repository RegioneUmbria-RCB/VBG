using Init.Sigepro.FrontEnd.AppLogic.Common;
using VBG.Shared.Infrastructure.Caching;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Common
{
    internal class QuerystringAliasSoftwareResolver : QuerystringAliasResolver, IAliasSoftwareResolver
    {
        private string _software = null;
        private readonly IContextCache _cache;

        private static class Constants
        {
            public const string DefaultSoftware = "SS";
            public const string SoftwareParameterName = "Software";
        }

        internal QuerystringAliasSoftwareResolver(IContextCache cache)
            : base(cache)
        {
            this._cache = cache;
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

                this._software = new QuerystringResolver(Constants.SoftwareParameterName, this._cache).GetValue();

                if (String.IsNullOrEmpty(this._software))
                {
                    this._software = new QuerystringResolver(Constants.SoftwareParameterName.ToLower(), this._cache).GetValue();
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