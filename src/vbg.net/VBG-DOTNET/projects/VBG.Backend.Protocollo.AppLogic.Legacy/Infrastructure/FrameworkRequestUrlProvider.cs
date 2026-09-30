using System.Web;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Infrastructure
{
    internal class FrameworkRequestUrlProvider : IRequestUrlProvider
    {
        public RequestUrlInfo GetRequestInfo()
        {
            var req = HttpContext.Current.Request;

            return new RequestUrlInfo
            {
                Scheme = req.Url.Scheme,
                Host = req.Url.Host,
                Port = req.Url.Port,
                ApplicationPath = req.ApplicationPath
            };
        }
    }
}