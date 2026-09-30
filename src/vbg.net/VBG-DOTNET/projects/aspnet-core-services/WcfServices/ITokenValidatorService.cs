using Init.SIGePro.Manager.Authentication;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices
{
    public interface ITokenValidatorService
    {
        AuthenticationInfo CheckToken(string token);
    }
}
