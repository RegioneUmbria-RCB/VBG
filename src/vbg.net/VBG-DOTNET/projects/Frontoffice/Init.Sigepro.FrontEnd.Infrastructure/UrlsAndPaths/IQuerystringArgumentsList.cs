namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths
{
    public interface IQuerystringArgumentsList
    {
        IQuerystringArgumentsList Add(IQuerystringParameter parameter);
        IQuerystringArgumentsList Add(string key, object value);
    }
}