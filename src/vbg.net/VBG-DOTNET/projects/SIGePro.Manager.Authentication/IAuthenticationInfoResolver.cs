
namespace Init.SIGePro.Manager.Authentication
{
    public interface IAuthenticationInfoResolver
    {
        AuthenticationInfo Resolve();
    }

    public interface ITransientAuthenticationInfoResolver : IAuthenticationInfoResolver
    {
        void SetTransientAuthInfo(AuthenticationInfo authenticationInfo);
    }
}
