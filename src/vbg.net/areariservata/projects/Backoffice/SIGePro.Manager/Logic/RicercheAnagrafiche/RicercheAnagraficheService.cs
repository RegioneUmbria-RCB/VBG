using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using log4net;
using Init.SIGePro.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Utils;
using System.Web;
using System.IO;
using System.Reflection;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche
{
	public class RicercheAnagraficheService
	{
		private static class Constants
		{
			public const string DefaultAnagrafeSearcherName = "DEFAULTANAGRAFESEARCHER";
			public const string ConcreteImplementorName = "AnagrafeSearcher";
		}



		DataBase _db;
		string _idComune;
        string _alias;
		ILog _log = LogManager.GetLogger(typeof(RicercheAnagraficheService));
		static Dictionary<string, Type> _typesDictionary = new Dictionary<string, Type>();
		ContestoRicercaAnagraficaEnum _contesto;

		public RicercheAnagraficheService(DataBase db, string idComune, string alias, ContestoRicercaAnagraficaEnum contesto)
		{
			this._db = db;
			this._idComune = idComune;
            this._alias = alias;
			this._contesto = contesto;
		}


		public Anagrafe GetByCodicefiscale(string codiceFiscale)
		{
			var searcher = GetSearcher();

			try
			{
				searcher.Init();

				return searcher.ByCodiceFiscaleImp( TipoPersona.PersonaFisica, codiceFiscale);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in ByCodiceFiscale:" + ex.ToString());

				throw;
			}
			finally
			{
				if (searcher != null)
					searcher.CleanUp();
			}
		}

        public IEnumerable<Anagrafe> GetVariazioniPersoneFisiche(DateTime from, DateTime to)
        {
            var searcher = GetSearcher();

            try
            {
                searcher.Init();

                return searcher.GetVariazioni(from, to);
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("Errore in GetVariazioniPersoneFisiche:" + ex.ToString());

                throw;
            }
            finally
            {
                if (searcher != null)
                    searcher.CleanUp();
            }
        }


        public Anagrafe GetByPartitaIva(string partitaivaOCodiceFiscale)
		{
			var searcher = GetSearcher();

			try
			{
				searcher.Init();

				return searcher.ByPartitaIvaImp(partitaivaOCodiceFiscale);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in GetByPartitaIva:" + ex.ToString());

				throw;
			}
			finally
			{
				if (searcher != null)
					searcher.CleanUp();
			}
		}


		private IAnagrafeSearcher GetSearcher()
		{
			var vert = new VerticalizzazioneWsanagrafe(this._alias, "TT");

			string assemblyName = vert.SearchComponent;

			_log.DebugFormat("Caricamento del componente di ricerca dall'assembly {0}", assemblyName);


			var defaultSigeproSearcher = new AnagrafeSearcher(Constants.DefaultAnagrafeSearcherName);
			defaultSigeproSearcher.InitParams(this._idComune, this._alias, this._db);

			if (!vert.Attiva || assemblyName == "" || assemblyName.ToUpper() == Constants.DefaultAnagrafeSearcherName)
				return defaultSigeproSearcher;

			try
			{
				var searcher = new SigeproWrappedAnagrafeSearcher(defaultSigeproSearcher, CreaIstanzaSearcher(vert));

				if (this._contesto == ContestoRicercaAnagraficaEnum.Backoffice)
					searcher.RestituisciAnagraficaSigeproSeNonTrovato = false;

                searcher.InitParams(this._idComune, this._alias, this._db);

				return searcher;
			}
			catch (Exception ex)
			{
				_log.Error(ex.ToString());

				return null;
			}
		}

		private AnagrafeSearcherBase CreaIstanzaSearcher(VerticalizzazioneWsanagrafe vert)
		{
			try
			{
                //if (vert.SearchComponent == "ADRIER")
                //{
                //    return new AnagrafeSearcherAdrierBase();
                //}

				var assemblyPath = vert.AssemblyLoadPath;
				var assemblyName = vert.SearchComponent;

				_log.DebugFormat("assemblyPath = {0}", assemblyPath);
				_log.DebugFormat("assemblyName = {0}", assemblyName);

				if (!_typesDictionary.ContainsKey(assemblyName))
				{
					_log.DebugFormat("Searcher non trovato in cache, verrà istanziato un nuovo assembly");

					if (String.IsNullOrEmpty(assemblyPath) && HttpContext.Current != null)
						assemblyPath = HttpContext.Current.Server.MapPath(@"~\bin\");

					var fullAssemblyPath = Path.Combine(assemblyPath, assemblyName + ".dll");

					_log.DebugFormat("fullAssemblyPath = {0}", fullAssemblyPath);

					var assembly = Assembly.LoadFrom(fullAssemblyPath);

					_log.DebugFormat("Assembly caricato correttmente, cerco di istanziare il searcher");

					var fullSearcherTypeName = assemblyName + "." + Constants.ConcreteImplementorName;

					_log.DebugFormat("fullSearcherTypeName = {0}-", fullSearcherTypeName);

					Type tp = assembly.GetType(fullSearcherTypeName);

					if (tp == null)
						throw new Exception("Impossibile istanziare il tipo " + Constants.ConcreteImplementorName + " dall'assembly " + assemblyName);

					_log.DebugFormat("Searcher istanziato correttamente");

					_typesDictionary.Add(assemblyName, tp);
				}

				return (AnagrafeSearcherBase)Activator.CreateInstance(_typesDictionary[assemblyName]);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("CreaIstanzaSearcher: {0}", ex.ToString());

				throw;
			}
		}
	}
}
