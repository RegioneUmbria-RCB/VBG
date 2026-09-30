namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public interface IMenuUrlBuilder
    {
        string SegnapostoAlias { get; }
        string SegnapostoSoftware { get; }
        string SegnapostoToken { get; }
        string SegnapostoBaseUrlCore { get; }
        string SegnapostoBaseUrlFramework { get; }
        string ParseMenuUrl(IMenuItemConUrl menuItem);
        string RisolviSegnaposto(string parsedCoreUrl);
        string AggiungiAliasESoftwareAQuerystring(string parsedUrl);
        string AggiungiTokenAQuerystring(string parsedUrl);
    }
}
