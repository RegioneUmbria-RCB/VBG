using Init.Sigepro.FrontEnd.AppLogic.Common;
using VBG.Shared.Infrastructure.Caching;
using System;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Common
{
    internal class QuerystringAliasResolver : IAliasResolver
    {
        private string _alias = null;
        private readonly IContextCache _cache;

        private static class Constants
        {
            public const string IdComuneParameterName = "IdComune";
            public const string AliasParameterName = "alias";
        }

        public QuerystringAliasResolver(IContextCache cache)
        {
            this._cache = cache;
        }

        public string AliasComune
        {
            get
            {
                if (!String.IsNullOrEmpty(this._alias))
                    return this._alias;

                this._alias = new QuerystringResolver(Constants.IdComuneParameterName, this._cache).GetValue();

                if (!string.IsNullOrEmpty(this._alias))
                    return this._alias;

                this._alias = new QuerystringResolver(Constants.AliasParameterName, this._cache).GetValue();

                if (String.IsNullOrEmpty(this._alias))
                    throw new InvalidOperationException("Parametro Idcomune non valido");

                return this._alias;
            }
        }
    }
}