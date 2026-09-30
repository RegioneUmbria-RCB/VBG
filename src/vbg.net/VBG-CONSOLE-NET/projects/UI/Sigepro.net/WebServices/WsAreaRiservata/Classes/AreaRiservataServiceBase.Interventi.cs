using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione;
using System.Web.Services;


namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
    public partial class AreaRiservataServiceBase
    {
        [WebMethod]
        public RisultatoVerificaAccessoIntervento VerificaAccessoIntervento(string token, LivelloAutenticazioneBOEnum livelloAutenticazione, int codiceIntervento, string codiceComune)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var attivazioneInterventoService = new AttivazioneInterventoService(db, ai.IdComune);

                return attivazioneInterventoService.VerificaAccessoIntervento(AttivazioneInterventoService.TipoPubblicazione.AreaRiservata, livelloAutenticazione, codiceIntervento, codiceComune);
            }
        }

        [WebMethod]
        public int GetLivelloDiAccessoIntervento(string token, int codiceIntervento)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var attivazioneInterventoService = new AttivazioneInterventoService(db, ai.IdComune);

                return attivazioneInterventoService.GetLivelloDiAutenticazioneRichiesto(codiceIntervento);
            }
        }
    }
}
