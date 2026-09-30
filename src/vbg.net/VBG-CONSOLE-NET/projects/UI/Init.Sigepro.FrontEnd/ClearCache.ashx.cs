using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd
{
	/// <summary>
	/// Summary description for ClearCache
	/// </summary>
	public class ClearCache : IHttpHandler, IReadOnlySessionState
	{

		public void ProcessRequest(HttpContext context)
		{
			context.Session.Clear();

			var keyList = new List<object>();

			var it = context.Cache.GetEnumerator();

			while ( it.MoveNext( ) )
				keyList.Add(it.Key);

			keyList.ForEach( x => context.Cache.Remove( x.ToString( ) ));

			try
			{
				System.Web.HttpRuntime.UnloadAppDomain();
			}
			catch (Exception )
			{
				context.Response.Write("impossibile scaricare l'app domain");
			}

			context.Response.Write("cache riciclata");
		}

		public bool IsReusable
		{
			get
			{
				return false;
			}
		}
	}
}