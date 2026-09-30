using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione
{
    public interface IConfigurazioneStc
    {
        string IdNodoMittente { get; }
        string IdEnteMittente { get; }
        string IdSportelloMittente { get; }
        string IdNodoDestinatario { get; }
        string IdEnteDestinatario { get; }
        string IdSportelloDestinatario { get; }
        string UrlInvio { get; }
        string Username { get; }
        string Password { get; }

        IConfigurazioneStc GetSpecializzazionePerSoftware(IAliasSoftwareResolver aliasSoftwareResolver);
    }
}