using Init.SIGePro.Manager.DTO.Bookmarks;
using Init.SIGePro.Manager.Manager;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Bookmarks
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsBookmarksService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsBookmarksService.svc or WsBookmarksService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsBookmarksService : WcfServiceBase, IWsBookmarksService
    {
        public BookmarkInterventoDto GetBookmark(string token, string nomeLink)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new FoArjServiziMgr(db, authInfo.IdComune);

                return mgr.GetBookmarkByName(nomeLink);
            }
        }
    }
}
