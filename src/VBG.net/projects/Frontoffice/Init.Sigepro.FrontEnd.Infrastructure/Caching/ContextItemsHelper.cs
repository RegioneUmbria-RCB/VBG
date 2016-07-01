using System.Web;

namespace Init.Sigepro.FrontEnd.Infrastructure.Caching
{
	public class ContextItemsHelper
	{
		public static bool KeyExists(string key)
		{
			if (HttpContext.Current == null)
				return false;

			return HttpContext.Current.Items[key] != null;
		}

		public static T AddEntry<T>(string key, T value)
		{
			if (HttpContext.Current != null)
				HttpContext.Current.Items.Add(key, value);

			return value;
		}

		public static T GetEntry<T>(string key) where T : class
		{
			if (HttpContext.Current == null)
				return (T)null;

			return (T)HttpContext.Current.Cache[key];
		}
	}
}
