namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths
{
    public interface IQuerystringParameter
    {
        string ParameterName { get; }
        string ParameterStringValue { get; }
    }
}