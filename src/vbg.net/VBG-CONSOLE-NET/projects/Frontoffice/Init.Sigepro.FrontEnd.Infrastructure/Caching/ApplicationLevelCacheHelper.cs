using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.Infrastructure.Caching
{
    public class ApplicationLevelCacheHelper
    {
        public static object _lock = new object();


        public T GetOrAdd<T>(string key, Func<T> defaultValLoader)
        {
            if (HttpContext.Current == null || HttpContext.Current.Application == null)
            {
                return defaultValLoader();
            }

            var obj = HttpContext.Current.Application[key];

            if (obj == null)
            {
                lock (_lock)
                {
                    obj = defaultValLoader();

                    HttpContext.Current.Application[key] = obj;
                }
            }

            return (T)obj;
        }

        public void Remove(string key)
        {
            HttpContext.Current.Application.Remove(key);
        }
    }
}
