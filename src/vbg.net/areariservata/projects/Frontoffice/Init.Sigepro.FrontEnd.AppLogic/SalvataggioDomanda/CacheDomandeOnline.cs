// -----------------------------------------------------------------------
// <copyright file="CacheDomandeOnline.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Caching;
	using System.Configuration;
	using log4net;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

	internal interface ICacheCleaningStrategy
	{
		void ApplyTo(Dictionary<string, CacheDomandaOnlineItem> cacheDictionary);
	}

	internal class CacheDomandaOnlineItem
	{
		public DomandaOnline Domanda { get; set; }
		public DateTime DataUltimoAccesso { get; set; }

		public override bool Equals(object obj)
		{
			if (obj == null)
				return false;

			var typedObj = obj as CacheDomandaOnlineItem;

			if (typedObj == null)
				return false;

			return Domanda.ReadInterface.AltriDati.AliasComune.Equals(typedObj.Domanda.ReadInterface.AltriDati.AliasComune) &&
					Domanda.ReadInterface.AltriDati.IdPresentazione.Equals(typedObj.Domanda.ReadInterface.AltriDati.IdPresentazione);
		}

		public static bool operator !=(CacheDomandaOnlineItem a, CacheDomandaOnlineItem b)
		{
			return !(a == b);
		}

		public static bool operator ==(CacheDomandaOnlineItem a, CacheDomandaOnlineItem b)
		{
			// If both are null, or both are same instance, return true.
			if (System.Object.ReferenceEquals(a, b))
			{
				return true;
			}


			if (((object)a) == null || ((object)b) == null)
				return false;

			return a.Equals(b);
		}

		public override int GetHashCode()
		{
			return Domanda.ReadInterface.AltriDati.AliasComune.GetHashCode() ^ Domanda.ReadInterface.AltriDati.IdentificativoDomanda.GetHashCode();
		}
	}

	internal class CacheDomandeOnline
	{
		ILog _log = LogManager.GetLogger(typeof(CacheDomandeOnline));

		ICacheCleaningStrategy _cleaningStrategy;
		static Dictionary<string, CacheDomandaOnlineItem> _domandeDictionary = new Dictionary<string, CacheDomandaOnlineItem>();

		internal CacheDomandeOnline(ICacheCleaningStrategy cleaningStrategy)
		{
			_cleaningStrategy = cleaningStrategy;
		}

		internal DomandaOnline Get(string aliasComune, int idDomanda)
		{
			var chiave = GetKey(aliasComune, idDomanda);

			if (!_domandeDictionary.ContainsKey(chiave))
				return null;

			var it = _domandeDictionary[ chiave ];

			it.DataUltimoAccesso = DateTime.Now;

			_log.DebugFormat("Domanda {0} letta dalla cache", chiave);

			return it.Domanda;
		}

		internal void Add(DomandaOnline domanda)
		{
			_cleaningStrategy.ApplyTo(_domandeDictionary);

			var alias	= domanda.ReadInterface.AltriDati.AliasComune;
			var id		= domanda.ReadInterface.AltriDati.IdPresentazione;

			var chiave = GetKey( alias, id );

			var cacheItem = new CacheDomandaOnlineItem
			{
				Domanda = domanda,
				DataUltimoAccesso = DateTime.Now
			};

			_domandeDictionary[ chiave ] = cacheItem;

			_log.DebugFormat("Domanda {0} aggiunta dalla cache", chiave);
		}

		internal void Remove(string aliasComune, int idDomanda)
		{
			var chiave = GetKey(aliasComune, idDomanda);

			_domandeDictionary.Remove(chiave);

			_log.DebugFormat("Domanda {0} rimossa dalla cache", chiave);
		}

		private string GetKey(string idComune, int idDomanda)
		{
			return String.Format("{0}_{1}", idComune, idDomanda);
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
			ILog _log = LogManager.GetLogger(typeof(Configurazione));

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

				_log.DebugFormat("Configurazine di PuliziaBasataSuNumeroEtaStrategy inizializzata con i parametri: " +
									"LimiteElementi={0}, NumeroMassimoElementi={1}, CrescitaLimite={2}, VitaMassimaElemento={3}",
									LimiteElementi, NumeroMassimoElementi, CrescitaLimite, VitaMassimaElemento);
			}

			public void IncrementaLimiteElementi()
			{
				LimiteElementi = LimiteElementi + CrescitaLimite;

				if (LimiteElementi > NumeroMassimoElementi)
				{
					LimiteElementi = NumeroMassimoElementi;

					_log.Error("Limite elementi in cache raggiunto, modificare il parametro PuliziaCacheDomande.NumeroMassimoElementiConsentiti nel file web.config per innalzare il limite");
				}
			}

			internal static Configurazione Load()
			{
				var limiteElementiCfg = ConfigurationManager.AppSettings[ Constants.NomeParametroLimiteElementi ];
				var numeroMassimoElementiConsentitiCfg = ConfigurationManager.AppSettings[ Constants.NomeParametroNumeroMassimoElementiConsentiti ];
				var crescitaLimiteCfg = ConfigurationManager.AppSettings[ Constants.NomeParametroCrescitaLimite ];
				var vitaMassimaElementoInMinutiCfg = ConfigurationManager.AppSettings[ Constants.NomeParametroVitaMassimaElementoInMinuti ];

				var limiteElementi = String.IsNullOrEmpty(limiteElementiCfg) ? Defaults.LimiteElementiDefault : Convert.ToInt32(limiteElementiCfg);
				var numeroMassimoElementiConsentiti = String.IsNullOrEmpty(numeroMassimoElementiConsentitiCfg) ? Defaults.NumeroMassimoElementiConsentitiDefault : Convert.ToInt32(numeroMassimoElementiConsentitiCfg);
				var crescitaLimite = String.IsNullOrEmpty(crescitaLimiteCfg) ? Defaults.CrescitaLimiteDefault : Convert.ToInt32(crescitaLimiteCfg);
				var vitaMassimaElementoInMinuti = String.IsNullOrEmpty(vitaMassimaElementoInMinutiCfg) ? Defaults.VitaMassimaElementoInMinutiDefault : Convert.ToInt32(vitaMassimaElementoInMinutiCfg);

				return new Configurazione(limiteElementi, numeroMassimoElementiConsentiti, crescitaLimite, vitaMassimaElementoInMinuti);
			}
		}

		ILog _log = LogManager.GetLogger(typeof(PuliziaBasataSuNumeroEtaStrategy));

		Configurazione _configurazione;

		public PuliziaBasataSuNumeroEtaStrategy()
		{
			_configurazione = CaricaConfigurazione();
		}

		private Configurazione CaricaConfigurazione()
		{
			if (CacheHelper.KeyExists(Constants.CacheKeyName))
				return CacheHelper.GetEntry<Configurazione>(Constants.CacheKeyName);

			return CacheHelper.AddEntry(Constants.CacheKeyName, Configurazione.Load());
		}


		#region ICacheCleaningStrategy Members

		public void ApplyTo(Dictionary<string, CacheDomandaOnlineItem> cacheDictionary)
		{
			if (cacheDictionary.Count > _configurazione.LimiteElementi)
			{
				_log.DebugFormat("Nella cache sono presenti {0} domande, procedo alla pulizia delle domande con più di {1} minuti di vita", cacheDictionary.Count, _configurazione.VitaMassimaElemento);

				var chiaviDaRimuovere = new List<string>();

				foreach (var key in cacheDictionary.Keys)
				{
					var value = cacheDictionary[ key ];

					if ((DateTime.Now - value.DataUltimoAccesso).Minutes > 5)
						chiaviDaRimuovere.Add(key);
				}

				if (chiaviDaRimuovere.Count == 0)
				{
					_configurazione.IncrementaLimiteElementi();

					_log.DebugFormat("Il limite di elementi nella cache è stato incrementato, il nuovo limite è {0}", _configurazione.LimiteElementi);

					return;
				}

				_log.DebugFormat("Sono state trovate {0} domande da rimuovere", chiaviDaRimuovere.Count);

				foreach (var chiave in chiaviDaRimuovere)
					cacheDictionary.Remove(chiave);

				_log.DebugFormat("Pulizia della cache completata, nella cache sono ora presenti {0} elementi", cacheDictionary.Count);
			}
		}

		#endregion
	}


}
