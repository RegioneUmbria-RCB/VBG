#if NET48
using VBG.Shared.Infrastructure.Caching;
using System;
using System.Web;

namespace VBG.Shared.Infrastructure.Caching.Framework
{
    public class ApplicationCache : IApplicationCache
    {
        public static object _lock = new();

        public void Clear()
        {
            HttpContext.Current?.Application?.Clear();
        }

        public T Get<T>(string key) where T : class
        {
            if (HttpContext.Current?.Application == null)
            {
                return null;
            }

            var obj = HttpContext.Current.Application[key];

            return (T)obj;
        }

        public T GetOrAdd<T>(string key, Func<T> addCallback) where T : class
        {
            if (HttpContext.Current?.Application == null)
            {
                return addCallback();
            }

            var obj = HttpContext.Current.Application[key];

            if (obj == null)
            {
                lock (_lock)
                {
                    obj = addCallback();

                    HttpContext.Current.Application[key] = obj;
                }
            }

            return (T)obj;
        }

        public void Remove(string key)
        {
            if (HttpContext.Current?.Application == null)
            {
                return;
            }

            HttpContext.Current.Application.Remove(key);
        }

        public T Set<T>(string key, T value)
        {
            if (HttpContext.Current?.Application == null)
            {
                return value;
            }

            HttpContext.Current.Application[key] = value;

            return value;
        }

        public bool TryGetValue<T>(string key, out T value) where T : class
        {
            var val = HttpContext.Current.Application[key];

            if (val == null)
            {
                value = null;
                return false;
            }

            value = (T)val;

            return true;
        }
    }
}
#endif