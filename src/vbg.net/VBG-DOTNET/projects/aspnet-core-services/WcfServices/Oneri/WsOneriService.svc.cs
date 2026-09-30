using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Oneri;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Oneri
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsOneriService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsOneriService.svc or WsOneriService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsOneriService : WcfServiceBase, IWsOneriService
    {
        public WsOneriService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public List<OnereDto> GetListaOneriDaIdInterventoECodiciEndo(string token, int codiceIntervento, List<int> listaIdEndo)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var endoMgr = new InventarioProcedimentiMgr(db);
                var intervMgr = new AlberoProcMgr(db);

                var rVal = new List<OnereDto>(intervMgr.GetListaOneriDaIdIntervento(ai.IdComune, codiceIntervento));

                for (int i = 0; i < listaIdEndo.Count; i++)
                {
                    var oneriEndo = endoMgr.GetOneriDaCodiceEndo(ai.IdComune, listaIdEndo[i]);

                    rVal.AddRange(oneriEndo);
                }

                return rVal;

            }
        }

        public string GetCodiceCausaleOnereTraslazione(string token, int idCausale)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var mgr = new TipiCausaliOneriMgr(db);
                var causale = mgr.GetById(ai.IdComune, idCausale);
                if (causale == null)
                {
                    return "";
                }

                return causale.MappaturaNodoPagamenti;
            }
        }


    }
}
