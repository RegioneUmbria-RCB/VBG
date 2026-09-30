using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.CoreServices.Autenticazione
{
    public interface IAuthenticationDataStore
    {
        void Save(UserAuthenticationResult userAuthenticationResult);
        void LogOut();
    }
}
