
namespace Init.SIGePro.Manager.Authentication.Utils
{
    public class IdComuneResolver : IIdComuneResolver
    {
        private readonly IAuthenticationInfoResolver _resolver;

        public IdComuneResolver(IAuthenticationInfoResolver resolver)
        {
            this._resolver = resolver;
        }

        public string IdComune => this._resolver.Resolve().IdComune;
    }

    public class AuthInfoIdComuneResolver : IIdComuneResolver
    {
        private readonly AuthenticationInfo _authInfo;

        public AuthInfoIdComuneResolver(AuthenticationInfo authInfo)
        {
            this._authInfo = authInfo;
        }

        public string IdComune => this._authInfo.IdComune;
    }
}
