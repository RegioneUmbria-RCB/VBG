#if NET48
using VBG.Shared.Infrastructure.Caching;
using System;
using System.Web;

namespace VBG.Shared.Infrastructure.Caching.Framework
{
    public class SessionCache : ISessionCache
    {
        public static object _lock = new object();

        public T Set<T>(string key, T value)
        {
            if (HttpContext.Current?.Session != null)
            {
                HttpContext.Current.Session[key] = value;
            }

            return value;
        }

        public T Get<T>(string key) where T : class
        {
            return (T)HttpContext.Current?.Session?[key];
        }

        public T GetOrAdd<T>(string key, Func<T> addCallback) where T : class
        {
            if (HttpContext.Current?.Session == null)
            {
                return addCallback();
            }

            var obj = HttpContext.Current.Session[key];

            if (obj == null)
            {
                lock (_lock)
                {
                    obj = addCallback();

                    HttpContext.Current.Session[key] = obj;
                }
            }

            return (T)obj;
        }

        public void Remove(string key)
        {
            HttpContext.Current?.Session?.Remove(key);
        }

        public void Clear()
        {
            HttpContext.Current?.Session?.Clear();
        }
    }
}
#endif