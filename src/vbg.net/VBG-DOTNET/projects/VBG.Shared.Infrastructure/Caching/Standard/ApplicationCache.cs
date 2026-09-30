#if NET9_0_OR_GREATER

using VBG.Shared.Infrastructure.Caching;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace VBG.Shared.Infrastructure.Caching.Standard
{
    internal class ApplicationCache : IApplicationCache
    {
        private readonly ConcurrentDictionary<string, object> _cache = new();

        public void Clear()
        {
            this._cache.Clear();
        }

        public T Get<T>(string key) where T : class
        {
            if (this._cache.TryGetValue(key, out var val))
            {
                return (T)val;
            }

            return default;
        }

        public T GetOrAdd<T>(string key, Func<T> addCallback) where T : class
        {
            return (T)this._cache.GetOrAdd(key, _ =>
            {
                return addCallback();
            });
        }

        public async Task<T> GetOrAddAsync<T>(string key, Func<Task<T>> addCallbackAsync) where T : class
        {
            if (this._cache.TryGetValue(key, out var value))
            {
                return (T)value;
            }

            var val = await addCallbackAsync();

            this._cache.TryAdd(key, val);

            return val;
        }

        public void Remove(string key)
        {
            this._cache.TryRemove(key, out _);
        }

        public T Set<T>(string key, T value)
        {
            this._cache.TryAdd(key, value);

            return value;
        }

        public bool TryGetValue<T>(string key, out T value) where T : class
        {
            if (this._cache.TryGetValue(key, out var val))
            {
                value = (T)val;
                return true;
            }

            value = null;
            return false;
        }
    }
}

#endif
