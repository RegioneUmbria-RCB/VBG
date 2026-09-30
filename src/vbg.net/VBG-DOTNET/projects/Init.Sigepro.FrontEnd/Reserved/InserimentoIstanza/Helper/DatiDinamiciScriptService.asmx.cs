using Init.SIGePro.DatiDinamici.ServerScriptService;
using System.Linq;
using System.Web.Script.Services;
using System.Web.Services;
using VBG.DatiDinamici.ServerScriptService;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Helper
{
    /// <summary>
    /// Summary description for DatiDinamiciScriptService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [System.Web.Script.Services.ScriptService]
    public class DatiDinamiciScriptService : System.Web.Services.WebService
    {
        private readonly D2ScriptService _scriptService = new D2ScriptService();

        [WebMethod(EnableSession = true)]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public string ValoreDecodificaModificato(string idCampo, int indice, string valoreDecodifica)
        {
            return this._scriptService.ValoreDecodificaModificato(idCampo, indice, valoreDecodifica);
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public StatoModello CampoModificato(string idCampo, int indice, string valore, string valoreDecodificato)
        {
            return this._scriptService.CampoModificato(idCampo, indice, valore, valoreDecodificato);
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public StatoModello GetStatoModello()
        {
            return this._scriptService.GetStatoModello2();
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public object SalvaModello()
        {
            return this._scriptService.SalvaModello();
        }

        [WebMethod(EnableSession = true)]
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        public CampoModificato[] GetValoriCampi()
        {
            return this._scriptService.GetValoriCampi().ToArray();
        }

    }
}
