using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.WebServices.istanze.visura
{
    /// <summary>
    /// Summary description for riepilogo_pratica
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class riepilogo_pratica : Ninject.Web.WebServiceBase
    {
        [Inject]
        protected RiepilogoDomandaDaVisuraService _riepilogoDomandaService { get; set; }

        [Inject]
        protected SigeproSecurityProxy _securityProxy { get; set; }

        [Inject]
        protected IVisuraService _visuraService { get; set; }


        [WebMethod(EnableSession=true)]
        public BinaryFile GeneraRiepilogo(string tokenApplicativo, string uidPratica)
        {
            var checkTokenResponse = this._securityProxy.CheckToken(tokenApplicativo);

            var alias = checkTokenResponse.tokenInfo.alias;

            

            HttpContext.Current.Items["token"] = tokenApplicativo;
            HttpContext.Current.Items["IdComune"] = alias;

            var pratica = this._visuraService.GetByUuid(uidPratica);

            HttpContext.Current.Items["Software"] = pratica.SOFTWARE;


            return this._riepilogoDomandaService.GeneraRiepilogoDomanda(uidPratica);
        }
    }
}
