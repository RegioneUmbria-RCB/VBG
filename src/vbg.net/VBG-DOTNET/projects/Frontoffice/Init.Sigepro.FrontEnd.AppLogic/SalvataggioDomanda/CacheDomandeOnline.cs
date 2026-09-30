// -----------------------------------------------------------------------
// <copyright file="CacheDomandeOnline.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda
{
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using VBG.Shared.Infrastructure.Caching;
    using log4net;
    using System;
    using System.Collections.Concurrent;
    using System.Collections.Generic;
    using System.Threading;
    using System.Threading.Tasks;

    internal interface ICacheCleaningStrategy
    {
        void ApplyTo(ConcurrentDictionary<string, CacheDomandaOnlineItem> cacheDictionary);
    }

    internal class CacheDomandaOnlineItem : IEquatable<CacheDomandaOnlineItem>
    {
        public DomandaOnline Domanda { get; set; }
        public DateTime DataUltimoAccesso { get; set; }

        public override bool Equals(object obj)
        {
            return this.Equals(obj as CacheDomandaOnlineItem);
        }

        public bool Equals(CacheDomandaOnlineItem other)
        {
            return !(other is null) &&
                   EqualityComparer<DomandaOnline>.Default.Equals(this.Domanda, other.Domanda) &&
                   this.DataUltimoAccesso == other.DataUltimoAccesso;
        }

        public override int GetHashCode()
        {
            int hashCode = -1412196579;
            hashCode = hashCode * -1521134295 + EqualityComparer<DomandaOnline>.Default.GetHashCode(this.Domanda);
            hashCode = hashCode * -1521134295 + this.DataUltimoAccesso.GetHashCode();
            return hashCode;
        }

        public static bool operator ==(CacheDomandaOnlineItem left, CacheDomandaOnlineItem right)
        {
            return EqualityComparer<CacheDomandaOnlineItem>.Default.Equals(left, right);
        }

        public static bool operator !=(CacheDomandaOnlineItem left, CacheDomandaOnlineItem right)
        {
            return !(left == right);
        }
    }

    internal class CacheDomandeOnline
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(CacheDomandeOnline));
        private readonly ICacheCleaningStrategy _cleaningStrategy;
        private static readonly ConcurrentDictionary<string, CacheDomandaOnlineItem> _domandeDictionary = new ConcurrentDictionary<string, CacheDomandaOnlineItem>();
        private static readonly SemaphoreSlim semaphore = new SemaphoreSlim(1);
        internal CacheDomandeOnline(ICacheCleaningStrategy cleaningStrategy)
        {
            this._cleaningStrategy = cleaningStrategy;
        }

        [Obsolete]
        internal DomandaOnline Get(string aliasComune, int idDomanda)
        {
            var chiave = this.GetKey(aliasComune, idDomanda);

            if (_domandeDictionary.TryGetValue(chiave, out var it))
            {
                it.DataUltimoAccesso = DateTime.Now;

                this._log.DebugFormat("Domanda {0} letta dalla cache", chiave);

                return it.Domanda;
            }

            return null;
        }

        internal async Task<DomandaOnline> GetOrAddAsync(string aliasComune, int idDomanda, Func<Task<DomandaOnline>> addCallbackAsync)
        {
            var chiave = this.GetKey(aliasComune, idDomanda);

            if (_domandeDictionary.TryGetValue(chiave, out var item))
            {
                this._log.DebugFormat("Domanda {0} presente nella cache, non verrà aggiunta", chiave);

                return item.Domanda;
            }

            await semaphore.WaitAsync();

            try
            {
                if (_domandeDictionary.TryGetValue(chiave, out item))
                {
                    this._log.DebugFormat("Domanda {0} presente nella cache, non verrà aggiunta", chiave);

                    return item.Domanda;
                }

                this._log.DebugFormat("Domanda {0} non è presente nella cache, verrà aggiunta", chiave);

                var newItem = await addCallbackAsync();

                _domandeDictionary[chiave] = new CacheDomandaOnlineItem
                {
                    Domanda = newItem,
                    DataUltimoAccesso = DateTime.Now
                };

                return newItem;
            }
            finally
            {
                semaphore.Release();
            }

        }

        internal DomandaOnline GetOrAdd(string aliasComune, int idDomanda, Func<DomandaOnline> addCallback)
        {
            var chiave = this.GetKey(aliasComune, idDomanda);

            var item = _domandeDictionary.GetOrAdd(chiave, (s) =>
            {
                this._log.DebugFormat("Domanda {0} non è presente nella cache, verrà aggiunta", chiave);

                return new CacheDomandaOnlineItem
                {
                    Domanda = addCallback(),
                    DataUltimoAccesso = DateTime.Now
                };
            });



            return item.Domanda;
        }

        [Obsolete]
        internal void Add(DomandaOnline domanda)
        {
            this._cleaningStrategy.ApplyTo(_domandeDictionary);

            var alias = domanda.ReadInterface.AltriDati.AliasComune;
            var id = domanda.ReadInterface.AltriDati.IdPresentazione;

            var chiave = this.GetKey(alias, id);

            var cacheItem = new CacheDomandaOnlineItem
            {
                Domanda = domanda,
                DataUltimoAccesso = DateTime.Now
            };

            this._log.DebugFormat("Aggiunta della domanda {0} alla cache", chiave);

            _domandeDictionary.AddOrUpdate(chiave, cacheItem, (s, k) =>
            {
                this._log.DebugFormat("Domanda {0} già presente, aggiornata nella cache", chiave);

                return cacheItem;
            });
        }

        internal void Remove(string aliasComune, int idDomanda)
        {
            var chiave = this.GetKey(aliasComune, idDomanda);

            _domandeDictionary.TryRemove(chiave, out _);

            this._log.DebugFormat("Domanda {0} rimossa dalla cache", chiave);
        }

        private string GetKey(string idComune, int idDomanda)
        {
            return $"{idComune}_{idDomanda}";
        }
    }


    internal class PuliziaBasataSuNumeroEtaStrategy : ICacheCleaningStrategy
    {
        private static class Constants
        {
            public const string CacheKeyName = "PuliziaBasataSuNumeroEtaStrategy.Configurazione";
        }

        protected class Configurazione
        {
            private readonly ILog _log = LogManager.GetLogger(typeof(Configurazione));

            private static class Constants
            {
                public const string NomeParametroLimiteElementi = "PuliziaCacheDomande.LimiteElementi";
                public const string NomeParametroNumeroMassimoElementiConsentiti = "PuliziaCacheDomande.NumeroMassimoElementiConsentiti";
                public const string NomeParametroCrescitaLimite = "PuliziaCacheDomande.CrescitaLimite";
                public const string NomeParametroVitaMassimaElementoInMinuti = "PuliziaCacheDomande.VitaMassimaElementoInMinuti";
            }

            private static class Defaults
            {
                public const int LimiteElementiDefault = 50;
                public const int NumeroMassimoElementiConsentitiDefault = 100;
                public const int CrescitaLimiteDefault = 10;
                public const int VitaMassimaElementoInMinutiDefault = 5;
            }

            public int LimiteElementi { get; private set; }
            protected int NumeroMassimoElementi { get; private set; }
            protected int CrescitaLimite { get; private set; }
            public int VitaMassimaElemento { get; private set; }

            private Configurazione(int limiteElementi, int numeroMassimoElementi, int crescitaLimite, int vitaElemento)
            {
                this.LimiteElementi = limiteElementi;
                this.NumeroMassimoElementi = numeroMassimoElementi;
                this.CrescitaLimite = crescitaLimite;
                this.VitaMassimaElemento = vitaElemento;

                this._log.DebugFormat("Configurazine di PuliziaBasataSuNumeroEtaStrategy inizializzata con i parametri: " +
                                    "LimiteElementi={0}, NumeroMassimoElementi={1}, CrescitaLimite={2}, VitaMassimaElemento={3}",
                                    this.LimiteElementi, this.NumeroMassimoElementi, this.CrescitaLimite, this.VitaMassimaElemento);
            }

            public void IncrementaLimiteElementi()
            {
                this.LimiteElementi = this.LimiteElementi + this.CrescitaLimite;

                if (this.LimiteElementi > this.NumeroMassimoElementi)
                {
                    this.LimiteElementi = this.NumeroMassimoElementi;

                    this._log.Error("Limite elementi in cache raggiunto, modificare il parametro PuliziaCacheDomande.NumeroMassimoElementiConsentiti nel file web.config per innalzare il limite");
                }
            }

            internal static Configurazione Load(IAppConfigurationReader appConfigurationReader)
            {
                var limiteElementiCfg = appConfigurationReader.GetSetting(Constants.NomeParametroLimiteElementi);
                var numeroMassimoElementiConsentitiCfg = appConfigurationReader.GetSetting(Constants.NomeParametroNumeroMassimoElementiConsentiti);
                var crescitaLimiteCfg = appConfigurationReader.GetSetting(Constants.NomeParametroCrescitaLimite);
                var vitaMassimaElementoInMinutiCfg = appConfigurationReader.GetSetting(Constants.NomeParametroVitaMassimaElementoInMinuti);

                var limiteElementi = String.IsNullOrEmpty(limiteElementiCfg) ? Defaults.LimiteElementiDefault : Convert.ToInt32(limiteElementiCfg);
                var numeroMassimoElementiConsentiti = String.IsNullOrEmpty(numeroMassimoElementiConsentitiCfg) ? Defaults.NumeroMassimoElementiConsentitiDefault : Convert.ToInt32(numeroMassimoElementiConsentitiCfg);
                var crescitaLimite = String.IsNullOrEmpty(crescitaLimiteCfg) ? Defaults.CrescitaLimiteDefault : Convert.ToInt32(crescitaLimiteCfg);
                var vitaMassimaElementoInMinuti = String.IsNullOrEmpty(vitaMassimaElementoInMinutiCfg) ? Defaults.VitaMassimaElementoInMinutiDefault : Convert.ToInt32(vitaMassimaElementoInMinutiCfg);

                return new Configurazione(limiteElementi, numeroMassimoElementiConsentiti, crescitaLimite, vitaMassimaElementoInMinuti);
            }
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(PuliziaBasataSuNumeroEtaStrategy));
        private readonly Configurazione _configurazione;
        private readonly IApplicationCache _webCache;
        private readonly IAppConfigurationReader _appConfigurationReader;

        public PuliziaBasataSuNumeroEtaStrategy(IApplicationCache webCache, IAppConfigurationReader appConfigurationReader)
        {
            this._webCache = webCache;
            this._appConfigurationReader = appConfigurationReader;
            this._configurazione = this.CaricaConfigurazione();
        }

        private Configurazione CaricaConfigurazione()
        {
            return this._webCache.GetOrAdd(Constants.CacheKeyName, () => Configurazione.Load(this._appConfigurationReader));
        }


        #region ICacheCleaningStrategy Members

        public void ApplyTo(ConcurrentDictionary<string, CacheDomandaOnlineItem> cacheDictionary)
        {
            if (cacheDictionary.Count > this._configurazione.LimiteElementi)
            {
                this._log.DebugFormat("Nella cache sono presenti {0} domande, procedo alla pulizia delle domande con più di {1} minuti di vita", cacheDictionary.Count, this._configurazione.VitaMassimaElemento);

                var chiaviDaRimuovere = new List<string>();

                foreach (var key in cacheDictionary.Keys)
                {
                    var value = cacheDictionary[key];

                    if ((DateTime.Now - value.DataUltimoAccesso).Minutes > 5)
                        chiaviDaRimuovere.Add(key);
                }

                if (chiaviDaRimuovere.Count == 0)
                {
                    this._configurazione.IncrementaLimiteElementi();

                    this._log.DebugFormat("Il limite di elementi nella cache è stato incrementato, il nuovo limite è {0}", this._configurazione.LimiteElementi);

                    return;
                }

                this._log.DebugFormat("Sono state trovate {0} domande da rimuovere", chiaviDaRimuovere.Count);

                foreach (var chiave in chiaviDaRimuovere)
                    cacheDictionary.TryRemove(chiave, out _);

                this._log.DebugFormat("Pulizia della cache completata, nella cache sono ora presenti {0} elementi", cacheDictionary.Count);
            }
        }

        #endregion
    }


}
