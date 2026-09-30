using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public interface IStepPage
    {
        string IdComune { get; }
        string Software { get; }
        int IdDomanda { get; }
        UserAuthenticationResult UserAuthenticationResult { get; }
        bool CheckIfCanEnterPage();
    }
}
