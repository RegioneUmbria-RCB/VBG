using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Configurazione;
using Init.SIGePro.Manager.Logic.GestioneConfigurazione;
using log4net;
using System.ServiceModel.Activation;
using SIGePro.Manager.VerticalizzazioniBase;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Configurazione
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsCommissioni" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsCommissioni.svc or WsCommissioni.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsConfigurazioneAreaRiservata : WcfServiceBase, IWsConfigurazioneAreaRiservata
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsConfigurazioneAreaRiservata));
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public WsConfigurazioneAreaRiservata(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver, IVerticalizzazioniFactory verticalizzazioniFactory) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public ConfigurazioneAreaRiservataDto LeggiConfigurazioneFrontoffice(string token, string software)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var svc = new ConfigurazioneAreaRiservataService(db, authInfo.IdComune, this._verticalizzazioniFactory);
                return svc.GetConfigurazioneAreaRiservata(authInfo.Alias, software);
            }
        }

        public Init.SIGePro.Data.Configurazione LeggiConfigurazioneComune(string token, string software)
        {
            var ai = this.CheckToken(token);

            try
            {
                return new ConfigurazioneMgr(ai.CreateDatabase()).GetByIdComuneESoftwareSovrascrivendoTT(ai.IdComune, software);
            }
            catch (Exception ex)
            {
                this._log.Error($"ConfigurazioneComune.LeggiConfigurazioneComune: {ex}");
                throw;
            }
        }
    }
}
