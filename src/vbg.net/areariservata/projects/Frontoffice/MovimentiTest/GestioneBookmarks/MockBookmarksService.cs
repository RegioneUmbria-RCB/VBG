using Init.Sigepro.FrontEnd.AppLogic.BookmarksWebService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneBookmarks
{
    public class MockBookmarksService : IBookmarksService
    {
        public int CodiceInterventoDaNomeBookmark(string nomeBookmark)
        {
            throw new NotImplementedException();
        }

        public AppLogic.BookmarksWebService.BookmarkInterventoDto GetDatiBookmark(string nomeBookmark)
        {
            throw new NotImplementedException();
        }

        public void InizializzaIstanzaDaBookmark(int idDomanda, string codiceComune, BookmarkInterventoDto datiBookmark)
        {
            throw new NotImplementedException();
        }
    }
}
