using PersonalLib2.Data;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Formule
{
    public interface IFormuleAuthenticationInfo
    {
        IDatabase CreateDatabase();
        string Token { get; }
        string Alias { get; }
        string IdComune { get; }
    }
}