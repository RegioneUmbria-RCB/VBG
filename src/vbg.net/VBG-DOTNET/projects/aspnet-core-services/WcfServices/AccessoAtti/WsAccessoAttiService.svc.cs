using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.GestioneAccessoAtti;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.AccessoAtti
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsScadenzarioService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsScadenzarioService.svc or WsScadenzarioService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsAccessoAttiService : WcfServiceBase, IWsAccessoAttiService
    {
        public WsAccessoAttiService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public string GetNomeFileZipPerDownloadDocumenti(string token, int idAccessoAtti, string uuidPratica)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                return new AccessoAttiService(db, ai.IdComune).GetNomeFileZipPerDownloadDocumenti(idAccessoAtti, uuidPratica);
            }
        }

        public PraticaAccessoAtti[] GetListaAtti(string token, int codiceAnagrafe, string software)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                return new AccessoAttiService(db, ai.IdComune).GetListaAtti(codiceAnagrafe, software).ToArray();
            }
        }

        public void LogAccessoAtti(string token, int idAccessoAtti, int codiceAnagrafe, string uuidIstanza)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var codiceIstanza = new IstanzeMgr(db).GetCodiceIstanzaDaUuid(ai.IdComune, uuidIstanza);
                new AccessoAttiService(db, ai.IdComune).LogAccessoPratica(idAccessoAtti, codiceAnagrafe, codiceIstanza.CodiceIstanza);
            }
        }

        public int GetLivelloAccessoDocumenti(string token, int idAccessoAtti, string uuidIstanza)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var codiceIstanza = new IstanzeMgr(db).GetCodiceIstanzaDaUuid(ai.IdComune, uuidIstanza);
                return new AccessoAttiService(db, ai.IdComune).GetLivelloAccessoDocumenti(idAccessoAtti, codiceIstanza.CodiceIstanza);
            }
        }
    }
}