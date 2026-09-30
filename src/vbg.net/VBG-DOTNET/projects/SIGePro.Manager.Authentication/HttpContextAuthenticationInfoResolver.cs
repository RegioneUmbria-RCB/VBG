using VBG.Shared.Infrastructure.Caching;
using System;
using System.Threading;

namespace Init.SIGePro.Manager.Authentication
{
    public class HttpContextAuthenticationInfoResolver : ITransientAuthenticationInfoResolver
    {
        private readonly IContextCache _contextCache;

        // AsyncLocal per scenari non-HTTP o fallback temporanei
        // es. di scenari non-HTTP: Thread background / worker / Test unit
        private static readonly AsyncLocal<AuthenticationInfo> _asyncFallback = new AsyncLocal<AuthenticationInfo>();

        public static class Constants
        {
            public const string ContextKeyName = "HttpContextAuthenticationInfoResolver:CurrentRequestAuthenticationInfo";
        }

        public HttpContextAuthenticationInfoResolver(IContextCache contextCache)
        {
            _contextCache = contextCache ?? throw new ArgumentNullException(nameof(contextCache));
        }

        /// <summary>
        /// Risolve l'AuthenticationInfo corrente.
        /// Cerca prima nel context cache (HTTP), poi nel fallback AsyncLocal.
        /// </summary>
        public AuthenticationInfo Resolve()
        {
            var contextValue = _contextCache.Get<AuthenticationInfo>(Constants.ContextKeyName);

            if (contextValue != null)
                return contextValue;

            // fallback per thread non-HTTP
            return _asyncFallback.Value;
        }

        /// <summary>
        /// Imposta l'AuthenticationInfo corrente.
        /// Salva sia nel context cache (HTTP) sia nel fallback AsyncLocal.
        /// </summary>
        public void SetTransientAuthInfo(AuthenticationInfo authenticationInfo)
        {
            if (authenticationInfo == null)
                throw new ArgumentNullException(nameof(authenticationInfo));

            _contextCache.Set(Constants.ContextKeyName, authenticationInfo);
            _asyncFallback.Value = authenticationInfo;
        }

        /// <summary>
        /// Imposta solo il fallback (utile per thread non-HTTP)
        /// </summary>
        public static void SetFallback(AuthenticationInfo authenticationInfo)
        {
            _asyncFallback.Value = authenticationInfo;
        }

        /// <summary>
        /// Pulisce il fallback AsyncLocal (opzionale, utile per thread long-lived)
        /// </summary>
        public static void ClearFallback()
        {
            _asyncFallback.Value = null;
        }
    }
}
