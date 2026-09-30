#if NET48
using VBG.Shared.Infrastructure.Caching;
using System;
using System.Web;
using System.Web.Caching;

namespace VBG.Shared.Infrastructure.Caching.Framework
{
    public class TimedCache : ITimedCache
    {
        public void Clear()
        {
            if (HttpContext.Current != null)
            {
                foreach (System.Collections.DictionaryEntry item in HttpContext.Current.Cache)
                {
                    HttpContext.Current.Cache.Remove(item.Key.ToString());
                }
            }
        }

        public T GetOrAdd<T>(string key, int timeoutMinutes, Func<T> addCallback) where T : class
        {
            if (HttpContext.Current != null)
            {
                var cacheValue = HttpContext.Current.Cache.Get(key);

                if (cacheValue != null)
                {
                    return (T)cacheValue;
                }
                var tokenExpiration = DateTime.Now.AddMinutes(timeoutMinutes);
                var value = addCallback();

                if (value == null)
                {
                    HttpContext.Current.Cache.Remove(key);
                    return value;
                }

                HttpContext.Current.Cache.Add(key,
                            value,
                            null,
                            tokenExpiration,
                            Cache.NoSlidingExpiration, CacheItemPriority.Normal, null);

                return value;
            }

            return addCallback();
        }

        public void Remove(string key)
        {
            if (HttpContext.Current == null)
            {
                return;
            }

            HttpContext.Current.Cache.Remove(key);
        }
    }
}
#endif
