using Init.Sigepro.FrontEnd.AppLogic.BookmarksWebService;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System;
namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks
{
    public interface IBookmarksService
    {
        int CodiceInterventoDaNomeBookmark(string nomeBookmark);
        BookmarkInterventoDto GetDatiBookmark(string nomeBookmark);
        void InizializzaIstanzaDaBookmark(int idDomanda, string codiceComune, BookmarkInterventoDto datiBookmark);
    }
}
