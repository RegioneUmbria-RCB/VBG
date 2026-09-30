using CoreWCF;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;

namespace AreaRiservataCore.wcf.istanze.visura
{
    [ServiceBehavior(IncludeExceptionDetailInFaults = true)]
    public class RiepilogoPraticaService : IRiepilogoPraticaService
    {
        protected RiepilogoDomandaDaVisuraService _riepilogoDomandaService;

        protected SigeproSecurityProxy _securityProxy;

        protected IVisuraService _visuraService;
        private readonly AliasSoftwareProvider _aliasSoftwareProvider;

        public RiepilogoPraticaService(RiepilogoDomandaDaVisuraService riepilogoDomandaService, SigeproSecurityProxy securityProxy, IVisuraService visuraService, AliasSoftwareProvider aliasSoftwareProvider)
        {
            this._riepilogoDomandaService = riepilogoDomandaService;
            this._securityProxy = securityProxy;
            this._visuraService = visuraService;
            this._aliasSoftwareProvider = aliasSoftwareProvider;
        }


        public BinaryFile GeneraRiepilogo(string tokenApplicativo, string uidPratica)
        {
            var checkTokenResponse = this._securityProxy.CheckToken(tokenApplicativo);

            //HttpContext.Current.Items["token"] = tokenApplicativo;
            this._aliasSoftwareProvider.AliasComune = checkTokenResponse.tokenInfo.alias;

            var pratica = this._visuraService.GetByUuid(uidPratica, false);

            this._aliasSoftwareProvider.Software = pratica.SOFTWARE;


            return this._riepilogoDomandaService.GeneraRiepilogoDomanda(uidPratica);
        }
    }
}
