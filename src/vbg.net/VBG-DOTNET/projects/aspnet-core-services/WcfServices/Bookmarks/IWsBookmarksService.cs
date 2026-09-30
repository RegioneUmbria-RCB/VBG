using Init.SIGePro.Manager.DTO.Bookmarks;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Bookmarks
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsBookmarksService" in both code and config file together.
    [ServiceContract]
    public interface IWsBookmarksService
    {
        [OperationContract]
        BookmarkInterventoDto GetBookmark(string token, string nomeLink);
    }
}
