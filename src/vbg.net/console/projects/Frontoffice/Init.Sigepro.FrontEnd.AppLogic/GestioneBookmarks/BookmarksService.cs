using Init.Sigepro.FrontEnd.AppLogic.BookmarksWebService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBookmarks
{
    public class BookmarksService : IBookmarksService
    {
        BookmarksServiceClientCreator _clientCreator;
        IAliasResolver _aliasResolver;
        ILog _log = LogManager.GetLogger(typeof(BookmarksService));
        ISalvataggioDomandaStrategy _salvataggioStrategy;
        DatiDomandaService _datiDomandaService;
        IResolveDescrizioneIntervento _resolveDescrizioneIntervento;
        IWorkflowService _workflowService;

        public BookmarksService(IAliasResolver aliasResolver, BookmarksServiceClientCreator clientCreator, ISalvataggioDomandaStrategy salvataggioStrategy, DatiDomandaService datiDomandaService, IResolveDescrizioneIntervento resolveDescrizioneIntervento, IWorkflowService workflowService)
        {
            this._clientCreator = clientCreator;
            this._aliasResolver = aliasResolver;
            this._salvataggioStrategy = salvataggioStrategy;
            this._datiDomandaService = datiDomandaService;
            this._resolveDescrizioneIntervento = resolveDescrizioneIntervento;
            this._workflowService = workflowService;
        }

        public int CodiceInterventoDaNomeBookmark(string nomeBookmark)
        {
            using(var service = this._clientCreator.CreateClient(this._aliasResolver.AliasComune))
            {
                try
                {
                    var datiBookmark = service.Service.GetBookmark(service.Token, nomeBookmark);


                    return datiBookmark.IdIntervento;

                }
                catch (Exception ex)
                {
                    _log.ErrorFormat("Errore durante la chiamata a GetBookmark: {0} durante la lettura del bookmark con id {1}", ex.ToString(), nomeBookmark);

                    throw;
                }
            }
        }

        public BookmarkInterventoDto GetDatiBookmark(string nomeBookmark)
        {
            using (var service = this._clientCreator.CreateClient(this._aliasResolver.AliasComune))
            {
                try
                {
                    var datiBookmark = service.Service.GetBookmark(service.Token, nomeBookmark);

                    return datiBookmark;
                }
                catch (Exception ex)
                {
                    _log.ErrorFormat("Errore durante la chiamata a GetDatiBookmark: {0} durante la lettura del bookmark con id {1}", ex.ToString(), nomeBookmark);

                    throw;
                }
            }
        }

        public void InizializzaIstanzaDaBookmark(int idDomanda, string codiceComune, BookmarkInterventoDto datiBookmark)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.AltriDati.ImpostaCodiceComune(codiceComune);
            domanda.WriteInterface.AltriDati.ImpostaIntervento(datiBookmark.IdIntervento, (int?)null, this._workflowService, this._resolveDescrizioneIntervento);
            domanda.WriteInterface.Bookmarks.Bookmark = datiBookmark.Url;

            this._workflowService.ResetWorkflowDaBookmark(GetDatiBookmark(datiBookmark.Url));

            this._salvataggioStrategy.Salva(domanda);
        }
    }
}
