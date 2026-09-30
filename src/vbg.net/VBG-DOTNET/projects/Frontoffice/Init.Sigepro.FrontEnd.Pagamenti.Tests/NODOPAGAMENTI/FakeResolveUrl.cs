using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.NODOPAGAMENTI
{
    internal class FakeResolveUrl : IResolveUrl
    {
        public FakeResolveUrl()
        {
        }

        public string ToAbsoluteUrl(string url)
        {
            throw new System.NotImplementedException();
        }
    }
}