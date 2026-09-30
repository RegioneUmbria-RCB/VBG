using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.MIP
{
    public class MockResolveUrl : IResolveUrl
    {
        public string ResolvedUrl = String.Empty;

        public string ToAbsoluteUrl(string url)
        {
            this.ResolvedUrl = url;

            return url;
        }
    }
}
