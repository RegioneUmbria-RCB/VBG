using System.Web;
using System.Linq;
using System.Web.Script.Services;
using System.Web.Services;
using Init.SIGePro.Manager.Logic.DatiDinamici.RicercheSigepro;
using System.Collections.Generic;

namespace Sigepro.net.Istanze.DatiDinamici.Helper.SearchHandlers
{
	/// <summary>
	/// Summary description for SearchHandler
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	[System.Web.Script.Services.ScriptService]
	public class SearchHandler : System.Web.Services.WebService
	{
		private string GetToken()
		{
			return HttpContext.Current.Request.QueryString["token"];
		}


		[ScriptMethod(ResponseFormat = ResponseFormat.Json)]
		[WebMethod(EnableSession = true)]
		public object initializeControl(int idCampo, string valore)
		{
			var val = new RicercheDatiDinamiciService(GetToken()).InitializeControl(idCampo, valore);

			return new { value = val.Value, label = val.Label };
		}

		[ScriptMethod(ResponseFormat = ResponseFormat.Json)]
		[WebMethod(EnableSession=true)]
        public object getCompletionList(int idCampo, string partial, List<ValoreFiltroRicerca> filtri)
		{
			return new RicercheDatiDinamiciService(GetToken())
						.GetCompletionList(idCampo, partial, filtri)
						.Select(x => new { value=x.Value, label = x.Label});
		}

	}
}
