#if NET9_0_OR_GREATER
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Collections.Generic;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths.Standard
{
    internal class StandardResolveUrl : IResolveUrl
    {
        private readonly ICoreUrlResolver _coreUrlResolver;

        public StandardResolveUrl(ICoreUrlResolver coreUrlResolver)
        {
            this._coreUrlResolver = coreUrlResolver;
        }
        public string ToAbsoluteUrl(string url)
        {
            return this._coreUrlResolver.ToAbsoluteUrl(url);
        }
    }
}
#endif
