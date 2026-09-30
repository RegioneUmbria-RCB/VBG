using Init.Sigepro.FrontEnd.AppLogic.Common;
using VBG.Shared.Infrastructure.Caching;
using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.DTO.StradarioComune;
using log4net;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{

    internal class WsStradarioRepository : IStradarioRepository
    {
        private readonly StradarioServiceCreator _serviceCreator;
        private readonly IApplicationCache _applicationCache;
        private readonly IAliasSoftwareResolver _aliasResolver;
        private readonly ILog _log = LogManager.GetLogger(typeof(WsStradarioRepository));

        public WsStradarioRepository(IAliasSoftwareResolver aliasResolver, StradarioServiceCreator serviceCreator, IApplicationCache applicationCache)
        {
            if (serviceCreator == null)
                throw new ArgumentNullException(nameof(serviceCreator));
            //Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

            this._serviceCreator = serviceCreator;
            this._applicationCache = applicationCache;
            this._aliasResolver = aliasResolver;
        }

        public StradarioEstesoDto GetByCodiceStradario(int codiceStradario)
        {
            var cacheKey = $"WsStradarioRepository.{this._aliasResolver.AliasComune}.{codiceStradario}";

            return this._applicationCache.GetOrAdd(cacheKey, () => this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.GetByCodiceStradario(ws.Token, codiceStradario);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetByCodiceStradario: {0}", ex.ToString());
                    throw;
                }
                finally
                {
                    ws.Service.Abort();
                }
            }));
        }

        public StradarioEstesoDto GetByCodiceStradario(string aliasComune, int codiceStradario)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.GetByCodiceStradario(ws.Token, codiceStradario);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetByCodiceStradario: {0}", ex.ToString());
                    throw;
                }
                finally
                {
                    ws.Service.Abort();
                }
            });
        }

        public StradarioEstesoDto GetByIndirizzo(string aliasComune, string codiceComune, string indirizzo)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.GetByIndirizzo(ws.Token, codiceComune, indirizzo);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetByIndirizzo: {0}", ex.ToString());
                    throw;
                }
                finally
                {
                    ws.Service.Abort();
                }
            });
        }

        public async ValueTask<List<StradarioDto>> GetByMatchParzialeAsyncIncludiDisabilitateAsync(string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            return await this._serviceCreator.CallAsync(async ws =>
            {
                try
                {
                    var matches = await ws.Service.GetByMatchParzialeIncludiDisabilitateAsync(ws.Token, codiceComune, comuneLocalizzazione, indirizzo);
                    return new List<StradarioDto>(matches);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetByMatchParzialeIncludiDisabilitate: {0}", ex.ToString());
                    throw;
                }
            });
        }

        public async ValueTask<List<StradarioDto>> GetByMatchParzialeAsync(string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            return await this._serviceCreator.CallAsync(async ws =>
            {
                try
                {
                    var matches = await ws.Service.GetByMatchParzialeAsync(ws.Token, codiceComune, comuneLocalizzazione, indirizzo);
                    return new List<StradarioDto>(matches);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetByMatchParziale: {0}", ex.ToString());
                    throw;
                }
            });
        }

        public List<StradarioDto> GetByMatchParziale(string aliasComune, string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return new List<StradarioDto>(ws.Service.GetByMatchParziale(ws.Token, codiceComune, comuneLocalizzazione, indirizzo));
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetByMatchParziale: {0}", ex.ToString());
                    throw;
                }
            });
        }

        public IEnumerable<ColoreStradarioDto> GetListaColori(string aliasComune = "")
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.GetListaColori(ws.Token);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetListaColori: {0}", ex.ToString());
                    throw;
                }
            });
        }


        public StradarioDto GetByCodViario(string alias, string codViario)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.GetStradarioByCodViario(ws.Token, codViario);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetByCodViario: {0}", ex.ToString());
                    throw;
                }
            });
        }


        public IEnumerable<DatiComuneCompatto> GetComuniStradario(string codiceComune)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    return ws.Service.GetComuniLocalizzazioni(ws.Token, codiceComune);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore in GetByCodViario: {0}", ex.ToString());
                    throw;
                }
                finally
                {
                    ws.Service.Abort();
                }
            });
        }
    }
}
