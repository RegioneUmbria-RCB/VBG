namespace VBG.Shared.Infrastructure.Caching
{
    public interface ISessionCache : ICache
    {
        T Set<T>(string key, T value);
        T Get<T>(string key) where T : class;
        void Remove(string key);
    }
}