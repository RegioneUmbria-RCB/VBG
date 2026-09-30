using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Authentication.Utils
{
    public class DatabaseCreator : IDatabaseCreator
    {
        private readonly IAuthenticationInfoResolver _resolver;

        public DatabaseCreator(IAuthenticationInfoResolver resolver)
        {
            this._resolver = resolver;
        }
        public DataBase Create()
        {
            return this._resolver.Resolve().CreateDatabase();
        }
    }

    public class AuthInfoDatabaseCreator : IDatabaseCreator
    {
        private readonly AuthenticationInfo _authenticationInfo;

        public AuthInfoDatabaseCreator(AuthenticationInfo authenticationInfo)
        {
            this._authenticationInfo = authenticationInfo;
        }

        public DataBase Create()
        {
            return this._authenticationInfo.CreateDatabase();
        }
    }
}
