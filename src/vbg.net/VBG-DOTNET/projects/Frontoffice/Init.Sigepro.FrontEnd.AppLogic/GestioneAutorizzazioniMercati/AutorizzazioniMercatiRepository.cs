using Init.Sigepro.FrontEnd.AppLogic.SigeproAutorizzazioniService;
using log4net;
using System;
using System.Collections.Generic;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAutorizzazioniMercati
{
    internal class AutorizzazioniMercatiRepository
    {
        private static class Constants
        {
            public const string ApplicationKey = "AutorizzazioniMercatiRepository.ListaEnti";
        }

        private readonly ILog _log = LogManager.GetLogger("AutorizzazioniMercatiRepository");
        private readonly AutorizzazioniMercatiServiceCreator _serviceCreator;
        private readonly IApplicationCache _webCache;

        public AutorizzazioniMercatiRepository(AutorizzazioniMercatiServiceCreator serviceCreator, IApplicationCache webCache)
        {
            this._serviceCreator = serviceCreator;
            this._webCache = webCache;
        }

        public IEnumerable<ListaAutorizzazioniItem> GetListaAutorizzazioni(int codiceAnagrafe, string[] registri, string stringaFormattazione, int codiceIntervento)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetAutorizzazioniConCodiceIntervento(ws.Token, registri, codiceAnagrafe, stringaFormattazione, codiceIntervento);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("errore durante l'invocazione di GetAutorizzazioniConCodiceIntervento: {0}", ex.ToString());

                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public IEnumerable<EnteAutorizzazione> GetListaEnti()
        {
            return this._webCache.GetOrAdd(Constants.ApplicationKey, () => this.GetListaEntiInternal());
        }

        private IEnumerable<EnteAutorizzazione> GetListaEntiInternal()
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetEnti(ws.Token);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("errore durante l'invocazione di GetEnti: {0}", ex.ToString());

                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public DettagliAutorizzazione GetDettagliAutorizzazione(int idAutorizzazione, int codiceIntervento)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetAutorizzazioneConCodiceIntervento(ws.Token, idAutorizzazione, codiceIntervento);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("errore durante l'invocazione di GetAutorizzazioneConCodiceIntervento: {0}", ex.ToString());

                    ws.Service.Abort();

                    throw;
                }
            }
        }
    }
}
