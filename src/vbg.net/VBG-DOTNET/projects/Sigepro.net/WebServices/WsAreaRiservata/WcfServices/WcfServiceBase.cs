using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager.IOC;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices
{
    public class WcfServiceBase
    {
        //private readonly ITransientAuthenticationInfoResolver _transientAuthentication;

        public AuthenticationInfo CheckToken(string token)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);
            var transientAuthentication = StaticKernelContainer.GetService<ITransientAuthenticationInfoResolver>();

            if (authInfo == null)
                throw new InvalidTokenException(token);

            transientAuthentication.SetTransientAuthInfo(authInfo);

            return authInfo;
        }
    }
}