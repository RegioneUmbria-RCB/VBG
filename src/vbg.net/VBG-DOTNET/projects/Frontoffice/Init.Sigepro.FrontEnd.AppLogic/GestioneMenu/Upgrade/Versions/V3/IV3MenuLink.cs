namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V3
{
    public interface IV3MenuLink
    {
        string UrlFramework { get; }
        string UrlCore { get; }
        string UrlEsterno { get; }
        bool CompletaUrl { get; }
    }
}
