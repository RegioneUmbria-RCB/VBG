using Init.Sigepro.FrontEnd.AppLogic.WsEndoFrontoffice;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Frontoffice
{
    public class WsEndoprocedimentiFrontofficeRepository : IEndoprocedimentiFrontofficeRepository
    {
        private readonly EndoFrontofficeServiceCreator _serviceCreator;

        public WsEndoprocedimentiFrontofficeRepository(EndoFrontofficeServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }


        public FamigliaEndoFrontoffice[] GetListaFamiglieFrontoffice(string alias, string software)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.GetFamiglieEndoFrontoffice(ws.Token, software);
                }
                catch (Exception)
                {
                    ws.Service.Abort();
                    throw;
                }
            });
        }

        public CategoriaEndoFrontoffice[] GetListaCategorieFrontoffice(string alias, string software, int codiceFamiglia)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.GetCategorieEndoFrontoffice(ws.Token, software, codiceFamiglia);
                }
                catch (Exception)
                {
                    ws.Service.Abort();
                    throw;
                }
            });
        }

        public EndoBreveFrontoffice[] GetListaEndoFrontoffice(string alias, string software, int codiceCategoria)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.GetListaEndoFrontoffice(ws.Token, software, codiceCategoria);
                }
                catch (Exception)
                {
                    ws.Service.Abort();
                    throw;
                }
            });
        }

        public RisultatoCaricamentoGerarchiaEndo CaricaGerarchiaFrontoffice(string alias, int id, LivelloCaricamentoGerarchia livello)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.GetGerarchiaEndo(ws.Token, id, livello);
                }
                catch (Exception)
                {
                    ws.Service.Abort();
                    throw;
                }
            });
        }

        public RisultatoRicercaTestualeEndo RicercaTestualeEndoFrontoffice(string alias, string software, string partial, TipoRicercaEnum tipoRicerca)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.RicercaTestualeEndo(ws.Token, software, partial, tipoRicerca);
                }
                catch (Exception)
                {
                    ws.Service.Abort();

                    throw;
                }
            });
        }
    }
}
