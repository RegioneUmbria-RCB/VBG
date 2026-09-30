using System;
using System.Threading.Tasks;

namespace VBG.Shared.Infrastructure.Caching
{
    public class NullApplicationCache : IApplicationCache
    {
        public void Clear()
        {
        }

        public T Get<T>(string key) where T : class
        {
            return null;
        }

        public T GetOrAdd<T>(string key, Func<T> addCallback) where T : class
        {
            return addCallback();
        }

        public async Task<T> GetOrAddAsync<T>(string key, Func<Task<T>> addCallbackAsync) where T : class
        {
            return await addCallbackAsync();
        }

        public void Remove(string key)
        {
        }

        public T Set<T>(string key, T value)
        {
            return value;
        }

        public bool TryGetValue<T>(string key, out T value) where T : class
        {
            value = null;
            return false;
        }
    }
}
