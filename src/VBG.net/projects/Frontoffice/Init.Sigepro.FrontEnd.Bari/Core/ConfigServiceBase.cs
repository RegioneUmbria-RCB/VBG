// -----------------------------------------------------------------------
// <copyright file="ConfigServiceBase.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.ServiceModel;
	using Init.Sigepro.FrontEnd.Bari.BariConfigServiceReference;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ConfigServiceBase
	{
		IConfigServiceUrl _webServiceUrl;
		IToken _token;
		ISoftware _software;

		public ConfigServiceBase(IConfigServiceUrl webServiceUrl, IToken token, ISoftware software)
		{
			this._webServiceUrl = webServiceUrl;
			this._token = token;
			this._software = software;
		}

		protected string GetToken()
		{
			return this._token.Get();
		}

		protected string GetSoftware()
		{
			return this._software.Get();
		}

		protected BariConfigServiceSoapClient CreaClientWsSigepro()
		{
			var binding = new BasicHttpBinding("defaultServiceBinding");
			var endpoint = new EndpointAddress(this._webServiceUrl.ServiceUrl);

			return new BariConfigServiceSoapClient(binding, endpoint);
		}
	}
}
