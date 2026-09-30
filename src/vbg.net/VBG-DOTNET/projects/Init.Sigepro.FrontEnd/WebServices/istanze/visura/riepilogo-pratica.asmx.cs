using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using log4net;
using Ninject;
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

        private readonly ILog _log = LogManager.GetLogger(typeof(riepilogo_pratica));


        [WebMethod(EnableSession = true)]
        public BinaryFile GeneraRiepilogo(string tokenApplicativo, string uidPratica)
        {
            var checkTokenResponse = this._securityProxy.CheckToken(tokenApplicativo);

            var alias = checkTokenResponse.tokenInfo.alias;

            this._log.InfoFormat("Generazione riepilogo pratica {0} per il comune {1}", uidPratica, alias);

            try
            {

                HttpContext.Current.Items["token"] = tokenApplicativo;
                HttpContext.Current.Items["IdComune"] = alias;

                var pratica = this._visuraService.GetByUuid(uidPratica, false);

                HttpContext.Current.Items["Software"] = pratica.SOFTWARE;


                return this._riepilogoDomandaService.GeneraRiepilogoDomanda(uidPratica);
            }
            catch (System.Exception ex)
            {

                this._log.ErrorFormat("Errore durante la generazione del riepilogo della pratica {0} per il comune {1}: {2}", uidPratica, alias, ex.ToString());

                throw;
            }
        }
    }
}
