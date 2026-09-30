using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices
{
    public class WcfServiceBase : ITokenValidatorService
    {
        private readonly IAuthenticationManager _authenticationManager;
        private readonly ITransientAuthenticationInfoResolver _transientAuthentication;

        public WcfServiceBase(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthentication)
        {
            this._authenticationManager = authenticationManager;
            this._transientAuthentication = transientAuthentication;
        }

        public AuthenticationInfo CheckToken(string token)
        {
            var authInfo = this._authenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            _transientAuthentication.SetTransientAuthInfo(authInfo);

            return authInfo;
        }
    }
}