using Microsoft.AspNetCore.Http;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions;

namespace VBG.Backend.Protocollo.AppLogic.Core.Infrastructure
{
    internal class CoreRequestUrlProvider : IRequestUrlProvider
    {
        private readonly IHttpContextAccessor _httpContextAccessor;

        public CoreRequestUrlProvider(IHttpContextAccessor httpContextAccessor)
        {
            _httpContextAccessor = httpContextAccessor;
        }

        public RequestUrlInfo GetRequestInfo()
        {
            var req = _httpContextAccessor.HttpContext.Request;

            return new RequestUrlInfo
            {
                Scheme = req.Scheme,
                Host = req.Host.Host,
                Port = req.Host.Port ?? 80,
                ApplicationPath = req.PathBase.HasValue
                    ? req.PathBase.Value
                    : string.Empty
            };
        }
    }
}
