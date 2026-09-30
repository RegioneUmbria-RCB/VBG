using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
    public class UserCredentialsStorage : IUserCredentialsStorage
    {
        private static class Constants
        {
            public const string Key = "UserCredentialsStorage:UserKey";
        }

        private readonly IContextCache _contextCache;

        public UserCredentialsStorage(IContextCache contextCache)
        {
            this._contextCache = contextCache;
        }

        public void Set(UserAuthenticationResult uar)
        {
            this._contextCache.Set(Constants.Key, uar);
        }

        public UserAuthenticationResult Get()
        {
            return this._contextCache.Get<UserAuthenticationResult>(Constants.Key);
        }
    }
}
