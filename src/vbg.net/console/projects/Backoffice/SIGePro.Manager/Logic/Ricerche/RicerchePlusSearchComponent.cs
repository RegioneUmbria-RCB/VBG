using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using PersonalLib2.Sql.Collections;
using System.Reflection;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using System.Data;

namespace Init.SIGePro.Manager.Logic.Ricerche
{
	public class RicerchePlusEventArgs : EventArgs
	{
		private DataClass m_searchedClass;

		public DataClass SearchedClass
		{
			get { return m_searchedClass; }
		}

		private DataClass m_compareClass;

		public DataClass CompareClass
		{
			get { return m_compareClass; }
		}

		private Dictionary<string, string> m_initParams;

		public Dictionary<string, string> InitParams
		{
			get { return m_initParams; }
		}

		internal RicerchePlusEventArgs(DataClass searchClass, DataClass compareClass, Dictionary<string, string> initParams)
		{
			m_searchedClass = searchClass;
			m_compareClass = compareClass;
			m_initParams = initParams;
		}

	}

	public class RicerchePlusSearchComponent
	{
		#region eventi e delegates
		public delegate void SearchingDelegate(object sender, RicerchePlusEventArgs e);
		public event SearchingDelegate Searching;
		#endregion

		#region classi usate internamente



		/// <summary>
		/// Lista delle proprietà ricercate dal controllo
		/// </summary>
		internal class SearchedProperty
		{
			private PropertyDescriptor m_descriptor;

			internal PropertyDescriptor Descriptor
			{
				get { return m_descriptor; }
			}

			private bool m_isKey;

			internal bool IsKey
			{
				get { return m_isKey; }
			}

			internal SearchedProperty(PropertyDescriptor descriptor, bool key)
			{
				m_descriptor = descriptor;
				m_isKey = key;
			}
		}

		/// <summary>
		/// Costruisce una lista mantenendo l'univocità in base ad una delle proprietà 
		/// della classe degli elementi da aggiungere
		/// </summary>
		internal class ResultListBuilder
		{
			Dictionary<string, object> m_foundItemsDictionary = new Dictionary<string, object>();
			List<KeyValuePair<string, string>> m_result = new List<KeyValuePair<string, string>>();
			PropertyDescriptor m_keyProp;	// Proprietà da utilizzare per la verifica dell'univocità

			/// <summary>
			/// Costruisce una lista mantenendo l'univocità in base ad una delle proprietà 
			/// della classe degli elementi da aggiungere
			/// </summary>
			/// <param name="keyProp">Proprietà da utilizzare per la verifica dell'univocità</param>
			public ResultListBuilder(PropertyDescriptor keyProp)
			{
				m_keyProp = keyProp;
			}

			/// <summary>
			/// Aggiunge gli elementi contenuti nella lista passata come parametro 
			/// alla lista interna mentenendo l'univocità
			/// </summary>
			/// <param name="coll">Lista contenente gli elementi da aggiungere</param>
			public void AddElements(DataClassCollection coll)
			{
				foreach (DataClass dc in coll)
				{
					string key = m_keyProp.GetValue(dc).ToString();
					string value = dc.ToString();

					if (!m_foundItemsDictionary.ContainsKey(key))
					{
						m_result.Add(new KeyValuePair<string, string>(key, value));
						m_foundItemsDictionary.Add(key, key);
					}
				}
			}

			public List<KeyValuePair<string, string>> GetList()
			{
				return m_result;
			}

			public int Count
			{
				get { return m_result.Count; }
			}

			public void Sort()
			{
				ListSortManager<KeyValuePair<string, string>>.Sort(m_result, "Value asc");
			}
		}
		#endregion

		string m_idComune = String.Empty;
		string m_token = String.Empty;
		DataBase m_database;
		DataClass m_dataClass;
		List<SearchedProperty> m_searchedProperties = new List<SearchedProperty>();
		string m_searchedValue;
		string m_searchedType;

		string m_software = "";
		bool m_ricercaSoftwareTT = false;

		Dictionary<string, string> m_initParams;



		public RicerchePlusSearchComponent(string token, string dataClassType,
												  string targetPropertyName,
												  string descriptionPropertyNames,
												  string prefixText,
												  int count,
												  string software,
												  bool ricercaSoftwareTT,
												  Dictionary<string, string> initParams)
		{
			m_token = token;
			m_searchedType = dataClassType;
			m_searchedValue = prefixText;
			m_initParams = initParams;

			m_dataClass = CreateTypeInstance();

			m_software = software;
			m_ricercaSoftwareTT = ricercaSoftwareTT;

			VerifyProperties(targetPropertyName, descriptionPropertyNames.Split(','));
		}





		public List<KeyValuePair<string, string>> Find(bool stopIfKeyIsFound)
		{
			SearchedProperty searchedKeyProp = m_searchedProperties.Find(delegate(SearchedProperty sp) { return sp.IsKey == true; });
			PropertyDescriptor keyProp = searchedKeyProp.Descriptor;

			ResultListBuilder risultato = new ResultListBuilder(keyProp);

			if (!Authenticate())
			{
				List<KeyValuePair<string, string>> ret = new List<KeyValuePair<string, string>>();

				ret.Add(new KeyValuePair<string, string>("Token non valido", "Token non valido"));

				return ret;
			}

			DataClass dataClass = CreateTypeInstance();

			risultato.AddElements(Find(dataClass, keyProp));

			if (!stopIfKeyIsFound || risultato.Count == 0)
			{
				foreach (SearchedProperty sp in m_searchedProperties)
				{
					if (sp.IsKey) continue;

					dataClass = CreateTypeInstance();

					risultato.AddElements(Find(dataClass, sp.Descriptor));
				}
			}

			risultato.Sort();

			return risultato.GetList();
		}



		#region metodi privati
		/// <summary>
		/// Effettua l'autenticazione su sigepro
		/// </summary>
		/// <returns></returns>
		private bool Authenticate()
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(m_token);

			if (authInfo == null) return false;

			m_idComune = authInfo.IdComune;
			m_database = authInfo.CreateDatabase();

			return true;
		}









		private DataClassCollection Find(DataClass searchClass, PropertyDescriptor prop)
		{
			DataClass compareClass = null;
			string searchedValue = m_searchedValue;

			EnsureIdComune(searchClass);
			EnsureSoftware(searchClass);

			if (prop.PropertyType == typeof(string))
			{
				compareClass = CreateTypeInstance();
				prop.SetValue(compareClass, "LIKE");

				searchedValue = "%" + searchedValue + "%";
			}

			try
			{
				if (prop.PropertyType == typeof(int?))
				{
					prop.SetValue(searchClass, Convert.ToInt32(searchedValue));
				}
				else
				{
					prop.SetValue(searchClass, Convert.ChangeType(searchedValue, prop.PropertyType));
				}
			}
			catch (Exception)
			{
				return new DataClassCollection();
			}

			if (this.Searching != null)
				this.Searching(this, new RicerchePlusEventArgs(searchClass, compareClass, m_initParams));
			/*
			using (var cmd = compareClass == null ? m_database.CreateCommand(searchClass) : m_database.CreateCommand(searchClass, compareClass))
			{
				using (IDataReader dr = cmd.ExecuteReader())
				{
				}
			}
			*/
			// TODO: Ottimizzare la ricerca senza leggere la lista di tutte le classi
			return compareClass == null ? m_database.GetClassList(searchClass) : m_database.GetClassList(searchClass, compareClass, false, true);
		}

		private void EnsureSoftware(DataClass dataClass)
		{
			PropertyDescriptor pd = TypeDescriptor.GetProperties(dataClass).Find("software", true);

			if (pd != null)
			{
				if (m_ricercaSoftwareTT)
				{
					dataClass.OthersWhereClause.Add("(software='" + m_software + "' or software='TT')");
				}
				else
				{
					pd.SetValue(dataClass, m_software);
				}				
			}
		}




		private void EnsureIdComune(DataClass dataClass)
		{
			PropertyDescriptor pd = TypeDescriptor.GetProperties(dataClass).Find("idcomune", true);

			if (pd != null)
				pd.SetValue(dataClass, m_idComune);
		}




		private void VerifyProperties(string keyProperty, string[] descriptionProperties)
		{
			PropertyDescriptorCollection pdc = TypeDescriptor.GetProperties(m_dataClass);

			PropertyDescriptor pd = pdc.Find(keyProperty, false);

			if (pd != null)
				m_searchedProperties.Add(new SearchedProperty(pd, true));

			for (int i = 0; i < descriptionProperties.Length; i++)
			{
				pd = pdc.Find(descriptionProperties[i], false);

				if (pd != null)
					m_searchedProperties.Add(new SearchedProperty(pd, false));
			}
		}




		private DataClass CreateTypeInstance()
		{
			string typeName = m_searchedType;
			Type tipoCercato = null;


			if (m_dataClass != null)
			{
				tipoCercato = m_dataClass.GetType();
			}
			else
			{
				Assembly assembly = Assembly.GetCallingAssembly();

				List<string> loadedAssemblies = new List<string>();

				tipoCercato = RecoursiveFindType(assembly, typeName, loadedAssemblies);

				if (tipoCercato == null)
					throw new ArgumentException("Impossibile caricare il tipo " + typeName + " dagli assembly referenziati dall'applicazione");
			}

			DataClass cls = (DataClass)Activator.CreateInstance(tipoCercato);

			return cls;
		}




		private static Type RecoursiveFindType(Assembly assembly, string typeName, List<string> loadedAssemblies)
		{
			loadedAssemblies.Add(assembly.FullName);

			Type t = assembly.GetType(typeName);

			if (t != null) return t;

			AssemblyName[] refAsm = assembly.GetReferencedAssemblies();

			for (int i = 0; i < refAsm.Length; i++)
			{
				if (!loadedAssemblies.Contains(refAsm[i].FullName))
				{
					t = RecoursiveFindType(Assembly.Load(refAsm[i].FullName), typeName, loadedAssemblies);

					if (t != null) return t;
				}
			}

			return null;
		}
		#endregion
	}
}
