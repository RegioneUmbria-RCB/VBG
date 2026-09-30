#if NET9_0_OR_GREATER
using VBG.Shared.Infrastructure.Caching;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;

namespace VBG.Shared.Infrastructure.Caching.Standard
{
    public class SessionCache : ISessionCache
    {
        private readonly ConcurrentDictionary<string, object> _cache = new ConcurrentDictionary<string, object>();

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

            return default(T);
        }

        public T GetOrAdd<T>(string key, Func<T> addCallback) where T : class
        {
            return (T)this._cache.GetOrAdd(key, _ =>
            {
                return addCallback();
            });
        }

        public void Remove(string key)
        {
            this._cache.TryRemove(key, out _);
        }

        public T Set<T>(string key, T value)
        {
            this._cache[key] = value;

            return value;
        }
    }
}
#endif