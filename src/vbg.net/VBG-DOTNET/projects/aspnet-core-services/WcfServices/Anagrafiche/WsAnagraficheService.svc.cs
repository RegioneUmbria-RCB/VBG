using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.DTO.Anagrafiche;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Anagrafiche
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsAnagraficheService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsAnagraficheService.svc or WsAnagraficheService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsAnagraficheService : WcfServiceBase, IWsAnagraficheService
    {
        public WsAnagraficheService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public Anagrafe GetAnagrafeByUserId(string token, string userId, TipoPersona tipoPersona)
        {
            var authInfo = this.CheckToken(token);


            using (var db = authInfo.CreateDatabase())
            {
                return new AnagrafeMgr(db).GetByUserId(authInfo.IdComune, userId, tipoPersona == TipoPersona.PersonaFisica ? AnagrafeMgr.TipoPersona.Fisica : AnagrafeMgr.TipoPersona.Giuridica);
            }
        }

        public AnagraficaCompattaDto GetAnagrafeCompattaByUserId(string token, string userId, TipoPersona tipoPersona)
        {
            var authInfo = this.CheckToken(token);


            using (var db = authInfo.CreateDatabase())
            {
                return new AnagrafeMgr(db).GetAnagraficaCompattaByUserId(authInfo.IdComune, userId, tipoPersona == TipoPersona.PersonaFisica ? AnagrafeMgr.TipoPersona.Fisica : AnagrafeMgr.TipoPersona.Giuridica);
            }
        }
    }

}
