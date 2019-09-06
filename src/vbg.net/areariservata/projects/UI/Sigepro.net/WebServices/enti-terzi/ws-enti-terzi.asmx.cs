using Init.SIGePro.Manager.Logic.GestioneEntiTerzi;
using Sigepro.net.WebServices.WsSIGePro;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Sigepro.net.WebServices.enti_terzi
{
    /// <summary>
    /// Summary description for ws_enti_terzi
    /// </summary>
    [WebService(Namespace = "http://sigepro.it/scrivania-enti-terzi")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class ws_enti_terzi : SigeproWebService
    {

        [WebMethod]
        public ETDatiAmministrazione GetDatiAmministrazione(string token, int codiceAnagrafe)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                return service.GetDatiAmministrazione(codiceAnagrafe);
            }
        }

        [WebMethod]
        public bool PuoEffettuareMovimenti(string token, int codiceAnagrafe)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                return service.PuoEffettuareMovimenti(codiceAnagrafe);
            }
        }

        [WebMethod]
        public ETPraticaEnteTerzo[] GetListaPratiche(string token, ETFiltriPraticheEntiTerzi filtri)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                return service.GetListaPratiche(filtri).ToArray();
            }
        }

        [WebMethod]
        public ETSoftware[] GetListaSoftwareConPratiche(string token, int codiceAnagrafe)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                return service.GetListaSoftwareConPratiche(codiceAnagrafe).ToArray();
            }
        }

        [WebMethod]
        public void MarcaPraticaComeElaborata(string token, int codiceIstanza, int codiceAnagrafe)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                service.MarcaPraticaComeElaborata(codiceIstanza, codiceAnagrafe);
            }
        }

        [WebMethod]
        public void MarcaPraticaComeNonElaborata(string token, int codiceIstanza, int codiceAnagrafe)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                service.MarcaPraticaComeNonElaborata(codiceIstanza, codiceAnagrafe);
            }
        }

        [WebMethod]
        public bool PraticaElaborata(string token, int codiceIstanza, int codiceAnagrafe)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ScrivaniaEntiTerziService(db, authInfo.IdComune);

                return service.PraticaElaborata(codiceIstanza, codiceAnagrafe);
            }
        }
    }
}
