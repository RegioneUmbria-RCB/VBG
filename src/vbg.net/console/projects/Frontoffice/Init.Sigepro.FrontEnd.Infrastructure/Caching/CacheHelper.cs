using System;
using System.Web;
using System.Web.Caching;

namespace Init.Sigepro.FrontEnd.Infrastructure.Caching
{
	public class CacheHelper
    {
        private static object _lock = new object();

		public static bool KeyExists(string key)
		{
            if(HttpContext.Current == null)
            {
                return false;
            }

			return HttpContext.Current.Cache[key] != null;
		}

		public static T AddEntry<T>(string key, T value)
		{
            if (HttpContext.Current == null)
            {
                return value;
            }

            return AddEntry(key, value, HttpContext.Current.Server.MapPath("~/web.config"));
		}

		public static T AddEntry<T>(string key, T value, string fileDep)
		{
            if (HttpContext.Current == null)
            {
                return value;
            }

            HttpContext.Current.Cache.Add(key, value, new CacheDependency(fileDep), DateTime.Now.AddDays(1), Cache.NoSlidingExpiration, CacheItemPriority.Normal, null);

			return value;
		}

		public static T GetEntry<T>(string key)
		{
			return (T)HttpContext.Current.Cache[key];
		}

        public static T GetOrAdd<T>(string key, Func<T> addCallback)
        {
            lock(_lock)
            {
                if (!KeyExists(key))
                {
                    var val = addCallback();

                    AddEntry(key, val);

                    return val;
                }
            }

            return GetEntry<T>(key);


        }
	}
}
