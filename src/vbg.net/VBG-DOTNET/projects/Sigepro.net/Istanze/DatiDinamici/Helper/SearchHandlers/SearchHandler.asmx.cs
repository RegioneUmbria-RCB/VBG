using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.DatiDinamici.RicercheSigepro;
using Ninject;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Services;
using System.Web.Services;

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
    public class SearchHandler : Ninject.Web.WebServiceBase
    {
        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }
        private string GetToken()
        {
            return HttpContext.Current.Request.QueryString["token"];
        }


        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod(EnableSession = true)]
        public object initializeControl(int idCampo, string valore)
        {
            var authInfo = this._authenticationManager.CheckToken(this.GetToken());

            var val = new RicercheDatiDinamiciService(authInfo).InitializeControl(idCampo, valore);

            return new { value = val.Value, label = val.Label };
        }

        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod(EnableSession = true)]
        public object getCompletionList(int idCampo, string partial, List<ValoreFiltroRicerca> filtri)
        {
            var authInfo = this._authenticationManager.CheckToken(this.GetToken());

            return new RicercheDatiDinamiciService(authInfo)
                        .GetCompletionList(idCampo, partial, filtri)
                        .Risultati
                        .Select(x => new { value = x.Value, label = x.Label });
        }

    }
}
