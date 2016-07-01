using System.Web;

namespace Init.Sigepro.FrontEnd.Infrastructure.Caching
{
	public class SessionHelper
	{
		public static bool KeyExists(string key)
		{
			if (HttpContext.Current == null || HttpContext.Current.Session == null)
				return false;

			return HttpContext.Current.Session[key] != null;
		}

		public static void RemoveEntry(string key)
		{
			if (HttpContext.Current == null || HttpContext.Current.Session == null)
				return;

			HttpContext.Current.Session[key] = null;
		}

		public static T AddEntry<T>(string key, T value)
		{
			if (HttpContext.Current == null || HttpContext.Current.Session == null)
				return value;

			HttpContext.Current.Session.Add(key, value);

			return value;
		}

		public static T GetEntry<T>(string key)
		{
			return (T)HttpContext.Current.Session[key];
		}
	}
}
