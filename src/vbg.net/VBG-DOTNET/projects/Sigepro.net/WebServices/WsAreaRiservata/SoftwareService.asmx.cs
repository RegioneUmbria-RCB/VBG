using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Sigepro.net.WebServices.WsSIGePro;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
    /// <summary>
    /// Summary description for SoftwareService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class SoftwareService : SigeproWebService
    {

        [WebMethod]
        public Software GetDatiSoftware(string token, string codice)
        {
            var authInfo = this.CheckToken(token);


            using (var db = authInfo.CreateDatabase())
            {
                return new SoftwareMgr(db).GetById(codice);
            }
        }
    }
}
