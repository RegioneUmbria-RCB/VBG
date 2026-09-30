
namespace Init.SIGePro.Manager.Authentication
{
    public class TemporaryAuthInfoResolver : IAuthenticationInfoResolver
    {
        private readonly AuthenticationInfo _authenticationInfo;

        public TemporaryAuthInfoResolver(AuthenticationInfo authenticationInfo)
        {
            this._authenticationInfo = authenticationInfo;
        }
        public AuthenticationInfo Resolve() => this._authenticationInfo;
    }
}
