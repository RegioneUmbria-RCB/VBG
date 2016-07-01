using System;
using System.Linq;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.WebControls;
using System.Globalization;
using System.Threading;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public class ReservedBasePage : BasePage
	{
		[Inject]
		public IAuthenticationDataResolver _authenticationDataResolver { get; set; }


		public UserAuthenticationResult UserAuthenticationResult
		{
			get { return _authenticationDataResolver.DatiAutenticazione; }
		}

		protected string CodiceUtente
		{
			get { return UserAuthenticationResult.DatiUtente.Codicefiscale; }
		}


		public ReservedBasePage()
		{

		}



		#region gestione della navigazione
		protected class QuerystringArgumentsList
		{
			List<KeyValuePair<string, string>> _list = new List<KeyValuePair<string,string>>();

			public void Add(string key, object value)
			{
				this._list.Add(new KeyValuePair<string, string>(key, value.ToString()));
			}

			public IEnumerable<KeyValuePair<string, string>> AsEnumerable()
			{
				return this._list;
			}
		}

		protected void Redirect(string url, Action<QuerystringArgumentsList> parametersBuilder)
		{
			var arguments = new QuerystringArgumentsList();

			parametersBuilder( arguments );

			Redirect(url, arguments.AsEnumerable());
		}

		private void Redirect(string url, IEnumerable<KeyValuePair<string, string>> parametriQuerystring)
		{
			var qs = String.Join( "&" , parametriQuerystring.Select(x => x.Key + "=" + Server.UrlEncode(x.Value)).ToArray());

			RedirectInternal(url, qs);
		}

		private void RedirectInternal(string url, string parametriQuerystring)
		{
			string urlFmt = "{0}?Token={1}&Software={2}&IdComune={3}";

			string newUrl = String.Format(urlFmt, url, UserAuthenticationResult.Token, Software, IdComune);

			if (!String.IsNullOrEmpty(parametriQuerystring))
				newUrl += "&" + parametriQuerystring;

			Response.Redirect(newUrl);
		}

		[Obsolete]
		protected void Redirect(string url, string parametriQuerystring)
		{
			string urlFmt = "{0}?Token={1}&Software={2}&IdComune={3}";

			string newUrl = String.Format(urlFmt, url, UserAuthenticationResult.Token, Software, IdComune);

			if (!String.IsNullOrEmpty(parametriQuerystring))
				newUrl += "&" + parametriQuerystring;

			Response.Redirect(newUrl);
		}

		protected void Redirect(string url)
		{
			Redirect(url, "");
		}
		#endregion

	}
}
