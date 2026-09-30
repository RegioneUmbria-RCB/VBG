namespace Init.Sigepro.FrontEnd.Infrastructure.Server
{
    public interface IPathMapper
    {
        bool IsPathMappingSupported { get; }

        string MapPath(string relative);
    }
}
