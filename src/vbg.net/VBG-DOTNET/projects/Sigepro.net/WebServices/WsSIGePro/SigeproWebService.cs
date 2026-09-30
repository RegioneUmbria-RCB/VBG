using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using Ninject;

namespace Sigepro.net.WebServices.WsSIGePro
{
    public class SigeproWebService : Ninject.Web.WebServiceBase
    {
        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }

        [Inject]
        public ITransientAuthenticationInfoResolver _transientAuthentication { get; set; }

        protected AuthenticationInfo CheckToken(string token)
        {
            AuthenticationInfo authInfo = this._authenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            _transientAuthentication.SetTransientAuthInfo(authInfo);

            return authInfo;
        }
    }
}
