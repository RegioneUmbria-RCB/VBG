using Init.SIGePro.Manager.Logic.GestioneAccessoAtti.Siena;
using Sigepro.net.WebServices.WsSIGePro;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Sigepro.net.WebServices.accesso_atti
{
    /// <summary>
    /// Summary description for ws_accesso_atti
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class ws_accesso_atti : SigeproWebService
    {

        [WebMethod]
        public PraticaAccessoAtti[] GetListaAtti(string token, int codiceAnagrafe, string software)
        {
            var ai = CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                return new SienaAccessoAttiService(db, ai.IdComune).GetListaAtti(codiceAnagrafe, software).ToArray();
            }
        }

        [WebMethod]
        public void LogAccessoAtti(string token, int idAccessoAtti, int codiceAnagrafe, int codiceIstanza)
        {
            var ai = CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                new SienaAccessoAttiService(db, ai.IdComune).LogAccessoPratica(idAccessoAtti, codiceAnagrafe, codiceIstanza);
            }
        }
    }
}
