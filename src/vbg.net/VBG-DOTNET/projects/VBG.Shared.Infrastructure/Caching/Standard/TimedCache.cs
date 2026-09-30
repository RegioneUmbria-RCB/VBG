#if NET9_0_OR_GREATER
using VBG.Shared.Infrastructure.Caching;
using Microsoft.Extensions.Caching.Memory;
using Microsoft.Extensions.Primitives;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace VBG.Shared.Infrastructure.Caching.Standard
{
    internal class TimedCache : ITimedCache
    {
        private static CancellationTokenSource _resetCacheToken = new CancellationTokenSource();
        private readonly IMemoryCache _cache;

        // private readonly ConcurrentDictionary<string, TimedCacheItem> _dictionary = new ConcurrentDictionary<string, TimedCacheItem>();

        public TimedCache(IMemoryCache cache)
        {
            this._cache = cache;
        }

        public void Clear()
        {
            if (_resetCacheToken != null && !_resetCacheToken.IsCancellationRequested && _resetCacheToken.Token.CanBeCanceled)
            {
                _resetCacheToken.Cancel();
                _resetCacheToken.Dispose();
            }

            _resetCacheToken = new CancellationTokenSource();
        }

        public T GetOrAdd<T>(string key, int timeoutMinutes, Func<T> addCallback) where T : class
        {
            if (this._cache.TryGetValue(key, out var currEntry))
            {
                if (currEntry != null)
                {
                    return (T)currEntry;
                }
            }

            var options = new MemoryCacheEntryOptions().SetPriority(CacheItemPriority.Normal).SetSlidingExpiration(TimeSpan.FromMinutes(timeoutMinutes));

            options.AddExpirationToken(new CancellationChangeToken(_resetCacheToken.Token));

            var value = addCallback();

            this._cache.Set(key, value, options);

            return value;
        }

        public void Remove(string key)
        {
            this._cache.Remove(key);
        }
    }
}
#endif