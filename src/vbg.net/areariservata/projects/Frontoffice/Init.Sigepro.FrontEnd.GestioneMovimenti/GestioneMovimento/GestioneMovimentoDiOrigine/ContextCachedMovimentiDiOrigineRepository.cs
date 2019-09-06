using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine
{
    public class ContextCachedMovimentiDiOrigineRepository : IMovimentiDiOrigineRepository
    {
        private static class Constants
        {
            public const string CacheKey = "ContextCachedMovimentoDiOrigineRepository.CacheKey.{0}";
        }

        MovimentiDiOrigineRepository _repo;

        public ContextCachedMovimentiDiOrigineRepository(MovimentiDiOrigineRepository repo)
        {
            this._repo = repo;
        }

        private MovimentoDiOrigine GetFromCache(GestioneMovimentoDaEffettuare.MovimentoDaEffettuare movimentoDaeffettuare)
        {
            var cacheKey = GetCacheKey(movimentoDaeffettuare.IdMovimentoDiOrigine);

            if (ContextItemsHelper.KeyExists(cacheKey))
            {
                return ContextItemsHelper.GetEntry<MovimentoDiOrigine>(cacheKey);
            }

            var movimento = this._repo.GetById(movimentoDaeffettuare);

            PutInCache(movimento);

            return movimento;
        }

        private void PutInCache(MovimentoDiOrigine movimento)
        {
            var cacheKey = GetCacheKey(movimento.IdMovimento);

            ContextItemsHelper.AddEntry(cacheKey, movimento);
        }

        private string GetCacheKey(int id)
        {
            return String.Format(Constants.CacheKey, id);
        }


        public MovimentoDiOrigine GetById(GestioneMovimentoDaEffettuare.MovimentoDaEffettuare movimentoDaeffettuare)
        {
            return GetFromCache(movimentoDaeffettuare);
        }


        public MovimentoDiOrigine GetByIdHackUsaSoloPerCreazioneMovimento(int id)
        {
            var movimento = this._repo.GetByIdHackUsaSoloPerCreazioneMovimento(id);

            PutInCache(movimento);

            return movimento;
        }
    }
}
