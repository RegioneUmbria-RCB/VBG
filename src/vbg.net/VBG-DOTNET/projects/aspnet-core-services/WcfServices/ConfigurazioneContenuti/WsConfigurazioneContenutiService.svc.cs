using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Configurazione;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.ConfigurazioneContenuti
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsConfigurazioneContenutiService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsConfigurazioneContenutiService.svc or WsConfigurazioneContenutiService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsConfigurazioneContenutiService : WcfServiceBase, IWsConfigurazioneContenutiService
    {
        public WsConfigurazioneContenutiService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public ConfigurazioneContenutiDto GetConfigurazioneContenutiFrontoffice(string token, string software)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                return new ConfigurazioneMgr(db)
                            .GetConfigurazioneContenutiFrontoffice(ai.IdComune, ai.Alias, software);
            }
        }

        public int GetCodiceOggettoRisorsaFrontoffice(string token, string idRisorsaOggetto)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                StiliFrontOfficeMgr sfoMgr = new StiliFrontOfficeMgr(db);
                int? codiceOggetto = sfoMgr.GetCodiceOggettoRisorsaFrontoffice(authInfo.IdComune, idRisorsaOggetto);

                if (!codiceOggetto.HasValue)
                    throw new ArgumentException("Id risorsa " + idRisorsaOggetto + " non trovato");

                return codiceOggetto.Value;
            }
        }
    }
}
