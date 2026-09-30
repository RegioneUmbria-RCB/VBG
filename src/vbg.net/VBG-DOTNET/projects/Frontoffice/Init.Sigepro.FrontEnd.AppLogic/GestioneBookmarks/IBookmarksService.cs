using Init.SIGePro.Manager.DTO.Bookmarks;
namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks
{
    public interface IBookmarksService
    {
        int CodiceInterventoDaNomeBookmark(string nomeBookmark);
        BookmarkInterventoDto GetDatiBookmark(string nomeBookmark);
        void InizializzaIstanzaDaBookmark(int idDomanda, string codiceComune, BookmarkInterventoDto datiBookmark);
    }
}
