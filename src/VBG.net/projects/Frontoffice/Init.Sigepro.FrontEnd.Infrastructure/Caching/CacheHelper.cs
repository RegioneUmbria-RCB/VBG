using System;
using System.Web;
using System.Web.Caching;

namespace Init.Sigepro.FrontEnd.Infrastructure.Caching
{
	public class CacheHelper
	{
		public static bool KeyExists(string key)
		{
			return HttpContext.Current.Cache[key] != null;
		}

		public static T AddEntry<T>(string key, T value)
		{
			return AddEntry(key, value, HttpContext.Current.Server.MapPath("~/web.config"));
		}

		public static T AddEntry<T>(string key, T value, string fileDep)
		{
			HttpContext.Current.Cache.Add(key, value, new CacheDependency(fileDep), DateTime.Now.AddDays(1), Cache.NoSlidingExpiration, CacheItemPriority.Normal, null);

			return value;
		}

		public static T GetEntry<T>(string key)
		{
			return (T)HttpContext.Current.Cache[key];
		}
	}
}
