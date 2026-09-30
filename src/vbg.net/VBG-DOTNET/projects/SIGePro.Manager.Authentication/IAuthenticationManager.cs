
using Init.SIGePro.Manager.Authentication.WsSigeproSecurity;

namespace Init.SIGePro.Manager.Authentication
{
    public interface IAuthenticationManager
    {
        AuthenticationInfo CheckToken(string token, TipiAmbiente ambiente = TipiAmbiente.DOTNET);
        ApplicationInfoType[] GetApplicationInfo();
        //string GetApplicationInfoValue(string param);
        AuthenticationInfo GetTokenApplicativo(string alias);
        AuthenticationInfo Login(string alias, string userId, string password, ContextType tipoContesto);

    }
}
