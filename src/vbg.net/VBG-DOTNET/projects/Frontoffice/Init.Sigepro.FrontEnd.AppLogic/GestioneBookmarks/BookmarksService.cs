using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.SIGePro.Manager.DTO.Bookmarks;
using log4net;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks
{
    public class BookmarksService : IBookmarksService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(BookmarksService));

        private readonly BookmarksServiceClientCreator _clientCreator;
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;
        private readonly IResolveDescrizioneIntervento _resolveDescrizioneIntervento;
        private readonly IWorkflowService _workflowService;
        private readonly IComuniService _comuniService;

        public BookmarksService(BookmarksServiceClientCreator clientCreator, ISalvataggioDomandaStrategy salvataggioStrategy,
                                IResolveDescrizioneIntervento resolveDescrizioneIntervento, IWorkflowService workflowService,
                                IComuniService comuniService)
        {
            this._clientCreator = clientCreator;
            this._salvataggioStrategy = salvataggioStrategy;
            this._resolveDescrizioneIntervento = resolveDescrizioneIntervento;
            this._workflowService = workflowService;
            this._comuniService = comuniService;
        }

        public int CodiceInterventoDaNomeBookmark(string nomeBookmark)
        {
            return this._clientCreator.Call(ws =>
            {
                try
                {
                    var datiBookmark = ws.Service.GetBookmark(ws.Token, nomeBookmark);


                    return datiBookmark.IdIntervento;

                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore durante la chiamata a GetBookmark: {0} durante la lettura del bookmark con id {1}", ex.ToString(), nomeBookmark);

                    throw;
                }
            });
        }

        public BookmarkInterventoDto GetDatiBookmark(string nomeBookmark)
        {
            return this._clientCreator.Call(ws =>
            {
                try
                {
                    var datiBookmark = ws.Service.GetBookmark(ws.Token, nomeBookmark);

                    return datiBookmark;
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore durante la chiamata a GetDatiBookmark: {0} durante la lettura del bookmark con id {1}", ex.ToString(), nomeBookmark);

                    throw;
                }
            });
        }

        public void InizializzaIstanzaDaBookmark(int idDomanda, string codiceComune, BookmarkInterventoDto datiBookmark)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var comune = this._comuniService.GetByCodiceComune(codiceComune);

            domanda.WriteInterface.AltriDati.ImpostaCodiceComune(codiceComune, comune?.CodiceISTAT ?? "");
            domanda.WriteInterface.AltriDati.ImpostaIntervento(datiBookmark.IdIntervento, null, this._resolveDescrizioneIntervento);
            domanda.WriteInterface.Bookmarks.Bookmark = datiBookmark.Url;

            this._workflowService.ClearCacheDomanda(idDomanda);

            this._salvataggioStrategy.Salva(domanda);
        }
    }
}
