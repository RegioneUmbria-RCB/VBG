
namespace Init.SIGePro.Manager.Authentication
{
    internal interface IAuthenticationInfoRepository
    {
        AuthenticationInfo GetByToken(string token, TipiAmbiente tipiAmbiente);
        AuthenticationInfo GetByLoginInfo(string alias, string userId, string password, ContextType tipoContesto);
    }
}
