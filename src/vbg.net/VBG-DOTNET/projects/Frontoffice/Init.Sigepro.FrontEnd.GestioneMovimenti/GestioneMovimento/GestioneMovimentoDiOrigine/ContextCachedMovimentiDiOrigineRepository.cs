using VBG.Shared.Infrastructure.Caching;
using System;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine
{
    public class ContextCachedMovimentiDiOrigineRepository : IMovimentiDiOrigineRepository
    {
        private static class Constants
        {
            public const string CacheKey = "ContextCachedMovimentoDiOrigineRepository.CacheKey.{0}";
        }

        private readonly MovimentiDiOrigineRepository _repo;
        private readonly IContextCache _contextCache;

        public ContextCachedMovimentiDiOrigineRepository(MovimentiDiOrigineRepository repo, IContextCache contextCache)
        {
            this._repo = repo;
            this._contextCache = contextCache;
        }

        private MovimentoDiOrigine GetFromCache(GestioneMovimentoDaEffettuare.MovimentoDaEffettuare movimentoDaeffettuare)
        {
            var cacheKey = this.GetCacheKey(movimentoDaeffettuare.IdMovimentoDiOrigine);

            return this._contextCache.GetOrAdd(cacheKey, () =>
                this._repo.GetById(movimentoDaeffettuare)
            );
        }

        private string GetCacheKey(int id)
        {
            return String.Format(Constants.CacheKey, id);
        }


        public MovimentoDiOrigine GetById(GestioneMovimentoDaEffettuare.MovimentoDaEffettuare movimentoDaeffettuare)
        {
            return this.GetFromCache(movimentoDaeffettuare);
        }


        public MovimentoDiOrigine GetByIdHackUsaSoloPerCreazioneMovimento(int id)
        {
            var movimento = this._repo.GetByIdHackUsaSoloPerCreazioneMovimento(id);

            var cacheKey = this.GetCacheKey(movimento.IdMovimento);

            this._contextCache.Set(cacheKey, movimento);

            return movimento;
        }
    }
}
