namespace VBG.Shared.Infrastructure.Caching
{
    public interface IApplicationCache : ICache
#if NET9_0_OR_GREATER
        , IAsyncCache
#endif
    {
        T Set<T>(string key, T value);
        T Get<T>(string key) where T : class;
        void Remove(string key);
        bool TryGetValue<T>(string key, out T value) where T : class;
    }
}