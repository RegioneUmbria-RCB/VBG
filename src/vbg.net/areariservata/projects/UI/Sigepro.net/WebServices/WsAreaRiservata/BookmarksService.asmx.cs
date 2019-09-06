using Init.SIGePro.Manager.DTO.Bookmarks;
using Init.SIGePro.Manager.Manager;
using Sigepro.net.WebServices.WsSIGePro;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
    /// <summary>
    /// Summary description for LinkParlantiService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class BookmarksService :SigeproWebService
    {

        [WebMethod]
        public BookmarkInterventoDto GetBookmark(string token, string nomeLink)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new FoArjServiziMgr(db, authInfo.IdComune);

                return mgr.GetBookmarkByName(nomeLink);
            }
        }
    }
}
