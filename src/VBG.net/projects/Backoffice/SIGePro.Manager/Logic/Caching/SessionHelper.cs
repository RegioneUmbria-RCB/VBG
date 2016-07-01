using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.SIGePro.Manager.Logic.Caching
{
	internal class SessionHelper
	{
		internal static bool KeyExists(string key)
		{
			if (HttpContext.Current == null || HttpContext.Current.Session == null)
				return false;

			return HttpContext.Current.Session[key] != null;
		}

		internal static T AddEntry<T>(string key, T value)
		{
			if (HttpContext.Current == null || HttpContext.Current.Session == null)
				return value;

			HttpContext.Current.Session.Add(key, value);

			return value;
		}

		internal static T GetEntry<T>(string key)
		{
			return (T)HttpContext.Current.Session[key];
		}
	}
}
