using Init.SIGePro.Manager.Logic.GestioneEntiTerzi;
using System.Linq;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.EntiTerzi
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsEntiTerziService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsEntiTerziService.svc or WsEntiTerziService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsEntiTerziService : WcfServiceBase, IWsEntiTerziService
    {
        public ETDatiAmministrazione GetDatiAmministrazione(string token, int codiceAnagrafe)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                return service.GetDatiAmministrazione(codiceAnagrafe);
            }
        }

        public bool PuoEffettuareMovimenti(string token, int codiceAnagrafe)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                return service.PuoEffettuareMovimenti(codiceAnagrafe);
            }
        }

        public ETPraticaEnteTerzo[] GetListaPratiche(string token, ETFiltriPraticheEntiTerzi filtri)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                return service.GetListaPratiche(filtri).ToArray();
            }
        }

        public ETSoftware[] GetListaSoftwareConPratiche(string token, int codiceAnagrafe)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                return service.GetListaSoftwareConPratiche(codiceAnagrafe).ToArray();
            }
        }

        public void MarcaPraticaComeElaborata(string token, int codiceIstanza, int codiceAnagrafe)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                service.MarcaPraticaComeElaborata(codiceIstanza, codiceAnagrafe);
            }
        }

        public void MarcaPraticaComeNonElaborata(string token, int codiceIstanza, int codiceAnagrafe)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                service.MarcaPraticaComeNonElaborata(codiceIstanza, codiceAnagrafe);
            }
        }

        public bool PraticaElaborata(string token, int codiceIstanza, int codiceAnagrafe)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                return service.PraticaElaborata(codiceIstanza, codiceAnagrafe);
            }
        }
    }
}
