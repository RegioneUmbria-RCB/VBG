using VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale;


namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.Autenticazione
{
    public interface IAuthenticationService
    {
        string Token { get; }
        string Username { get; }
        void Login();
        void Logout();
        Dictionary<string, string> GetRuoli(GestioneDocumentaleService gestDocWrapper);
    }
}
