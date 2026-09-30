#if NET48
using VBG.Shared.Infrastructure.Caching;
using System;
using System.Web;

namespace VBG.Shared.Infrastructure.Caching.Framework
{
    public class ContextCache : IContextCache
    {
        public void Clear()
        {
            HttpContext.Current?.Items?.Clear();
        }

        public T Get<T>(string key) where T : class
        {
            if (HttpContext.Current?.Items == null)
            {
                return (T)null;
            }

            return (T)HttpContext.Current.Items[key];
        }

        public T GetOrAdd<T>(string key, Func<T> addCallback) where T : class
        {
            if (HttpContext.Current?.Items == null)
            {
                return addCallback();
            }

            var obj = HttpContext.Current.Items[key];

            if (obj == null)
            {
                obj = addCallback();

                HttpContext.Current.Items[key] = obj;
            }

            return (T)obj;
        }

        public T Set<T>(string key, T value)
        {
            if (HttpContext.Current?.Items != null)
            {
                HttpContext.Current.Items[key] = value;
            }

            return value;
        }
    }
}
#endif