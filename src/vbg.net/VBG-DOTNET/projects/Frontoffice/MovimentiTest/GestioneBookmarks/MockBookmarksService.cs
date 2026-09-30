using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using Init.SIGePro.Manager.DTO.Bookmarks;
using System;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneBookmarks
{
    public class MockBookmarksService : IBookmarksService
    {
        public int CodiceInterventoDaNomeBookmark(string nomeBookmark)
        {
            throw new NotImplementedException();
        }

        public BookmarkInterventoDto GetDatiBookmark(string nomeBookmark)
        {
            throw new NotImplementedException();
        }

        public void InizializzaIstanzaDaBookmark(int idDomanda, string codiceComune, BookmarkInterventoDto datiBookmark)
        {
            throw new NotImplementedException();
        }
    }
}
