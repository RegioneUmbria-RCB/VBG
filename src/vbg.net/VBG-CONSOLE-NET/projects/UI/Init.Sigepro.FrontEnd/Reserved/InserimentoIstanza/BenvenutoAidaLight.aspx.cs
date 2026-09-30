using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class BenvenutoAidaLight : IstanzeStepPage
	{

		protected void Page_Load(object sender, EventArgs e)
		{
			var qs = GetAuthenticatedQuerystring();
			var url = GetUrlPresentazioneDomanda();

			Response.Redirect(String.Format("{0}?{1}", url, qs));
		}

		private string GetUrlPresentazioneDomanda()
		{
			var url = ConfigurationManager.AppSettings["aidaLight.mainUrl"];

			if(String.IsNullOrEmpty(url))
			{
				throw new Exception("Url di AIDA light non definito");
			}

			return url;

		}

		private string GetAuthenticatedQuerystring()
		{
			var tokenServiceUrl = ConfigurationManager.AppSettings["aidaLight.tokenServiceUrl"];

			if (String.IsNullOrEmpty(tokenServiceUrl))
			{
				throw new Exception("Url del token service non definito");
			}

			var uar = UserAuthenticationResult.DatiUtente;
            var qs = $"?cf={Server.UrlEncode(uar.Codicefiscale)}&nome={Server.UrlEncode(uar.Nome)}&cognome={Server.UrlEncode(uar.Nominativo)}&sesso={Server.UrlEncode(uar.Sesso)}&time={DateTime.Now.ToString("yyyyMMddHHmmssffff")}";

			ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };

			var request = (HttpWebRequest)WebRequest.Create(tokenServiceUrl + qs);
			request.Method = "GET";

			var response = (HttpWebResponse)request.GetResponse(); ;
			var stream = response.GetResponseStream();
			var readStream = new StreamReader(stream, Encoding.UTF8);

			return readStream.ReadToEnd();
		}

	}
}