using Init.SIGePro.Authentication;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Reflection;

namespace Init.SIGePro.Manager.Logic.Ricerche
{
    public class RicerchePlusEventArgs : EventArgs
    {
        private readonly DataClass m_searchedClass;

        public DataClass SearchedClass
        {
            get { return this.m_searchedClass; }
        }

        private readonly DataClass m_compareClass;

        public DataClass CompareClass
        {
            get { return this.m_compareClass; }
        }

        private readonly Dictionary<string, string> m_initParams;

        public Dictionary<string, string> InitParams
        {
            get { return this.m_initParams; }
        }

        internal RicerchePlusEventArgs(DataClass searchClass, DataClass compareClass, Dictionary<string, string> initParams)
        {
            this.m_searchedClass = searchClass;
            this.m_compareClass = compareClass;
            this.m_initParams = initParams;
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
            private readonly PropertyDescriptor m_descriptor;

            internal PropertyDescriptor Descriptor
            {
                get { return this.m_descriptor; }
            }

            private readonly bool m_isKey;

            internal bool IsKey
            {
                get { return this.m_isKey; }
            }

            internal SearchedProperty(PropertyDescriptor descriptor, bool key)
            {
                this.m_descriptor = descriptor;
                this.m_isKey = key;
            }
        }

        /// <summary>
        /// Costruisce una lista mantenendo l'univocità in base ad una delle proprietà 
        /// della classe degli elementi da aggiungere
        /// </summary>
        internal class ResultListBuilder
        {
            private readonly Dictionary<string, object> m_foundItemsDictionary = new Dictionary<string, object>();
            private readonly List<KeyValuePair<string, string>> m_result = new List<KeyValuePair<string, string>>();
            private readonly PropertyDescriptor m_keyProp;  // Proprietà da utilizzare per la verifica dell'univocità

            /// <summary>
            /// Costruisce una lista mantenendo l'univocità in base ad una delle proprietà 
            /// della classe degli elementi da aggiungere
            /// </summary>
            /// <param name="keyProp">Proprietà da utilizzare per la verifica dell'univocità</param>
            public ResultListBuilder(PropertyDescriptor keyProp)
            {
                this.m_keyProp = keyProp;
            }

            /// <summary>
            /// Aggiunge gli elementi contenuti nella lista passata come parametro 
            /// alla lista interna mentenendo l'univocità
            /// </summary>
            /// <param name="coll">Lista contenente gli elementi da aggiungere</param>
            public void AddElements(IEnumerable<DataClass> coll)
            {
                foreach (DataClass dc in coll)
                {
                    string key = this.m_keyProp.GetValue(dc).ToString();
                    string value = dc.ToString();

                    if (!this.m_foundItemsDictionary.ContainsKey(key))
                    {
                        this.m_result.Add(new KeyValuePair<string, string>(key, value));
                        this.m_foundItemsDictionary.Add(key, key);
                    }
                }
            }

            public List<KeyValuePair<string, string>> GetList()
            {
                return this.m_result;
            }

            public int Count
            {
                get { return this.m_result.Count; }
            }

            public void Sort()
            {
                ListSortManager<KeyValuePair<string, string>>.Sort(this.m_result, "Value asc");
            }
        }
        #endregion

        private string m_idComune = String.Empty;
        private readonly string m_token = String.Empty;
        private DataBase m_database;
        private readonly DataClass m_dataClass;
        private readonly List<SearchedProperty> m_searchedProperties = new List<SearchedProperty>();
        private readonly string m_searchedValue;
        private readonly string m_searchedType;

        private readonly string m_software = "";
        private readonly bool m_ricercaSoftwareTT = false;

        private readonly Dictionary<string, string> m_initParams;



        public RicerchePlusSearchComponent(string token, string dataClassType,
                                                  string targetPropertyName,
                                                  string descriptionPropertyNames,
                                                  string prefixText,
                                                  int count,
                                                  string software,
                                                  bool ricercaSoftwareTT,
                                                  Dictionary<string, string> initParams)
        {
            this.m_token = token;
            this.m_searchedType = dataClassType;
            this.m_searchedValue = prefixText;
            this.m_initParams = initParams;

            this.m_dataClass = this.CreateTypeInstance();

            this.m_software = software;
            this.m_ricercaSoftwareTT = ricercaSoftwareTT;

            this.VerifyProperties(targetPropertyName, descriptionPropertyNames.Split(','));
        }





        public List<KeyValuePair<string, string>> Find(bool stopIfKeyIsFound)
        {
            SearchedProperty searchedKeyProp = this.m_searchedProperties.Find(delegate (SearchedProperty sp) { return sp.IsKey == true; });
            PropertyDescriptor keyProp = searchedKeyProp.Descriptor;

            ResultListBuilder risultato = new ResultListBuilder(keyProp);

            if (!this.Authenticate())
            {
                List<KeyValuePair<string, string>> ret = new List<KeyValuePair<string, string>>();

                ret.Add(new KeyValuePair<string, string>("Token non valido", "Token non valido"));

                return ret;
            }

            DataClass dataClass = this.CreateTypeInstance();

            risultato.AddElements(this.Find(dataClass, keyProp));

            if (!stopIfKeyIsFound || risultato.Count == 0)
            {
                foreach (SearchedProperty sp in this.m_searchedProperties)
                {
                    if (sp.IsKey) continue;

                    dataClass = this.CreateTypeInstance();

                    risultato.AddElements(this.Find(dataClass, sp.Descriptor));
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
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(this.m_token);

            if (authInfo == null) return false;

            this.m_idComune = authInfo.IdComune;
            this.m_database = authInfo.CreateDatabase();

            return true;
        }









        private IEnumerable<DataClass> Find(DataClass searchClass, PropertyDescriptor prop)
        {
            DataClass compareClass = null;
            string searchedValue = this.m_searchedValue;

            this.EnsureIdComune(searchClass);
            this.EnsureSoftware(searchClass);

            if (prop.PropertyType == typeof(string))
            {
                compareClass = this.CreateTypeInstance();
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
                return Enumerable.Empty<DataClass>();
            }

            if (this.Searching != null)
                this.Searching(this, new RicerchePlusEventArgs(searchClass, compareClass, this.m_initParams));
            /*
			using (var cmd = compareClass == null ? m_database.CreateCommand(searchClass) : m_database.CreateCommand(searchClass, compareClass))
			{
				using (IDataReader dr = cmd.ExecuteReader())
				{
				}
			}
			*/
            // TODO: Ottimizzare la ricerca senza leggere la lista di tutte le classi
            return compareClass == null ? this.m_database.GetClassList(searchClass) : this.m_database.GetClassList(searchClass, compareClass, false);
        }

        private void EnsureSoftware(DataClass dataClass)
        {
            PropertyDescriptor pd = TypeDescriptor.GetProperties(dataClass).Find("software", true);

            if (pd != null)
            {
                if (this.m_ricercaSoftwareTT)
                {
                    dataClass.OthersWhereClause.Add("(software='" + this.m_software + "' or software='TT')");
                }
                else
                {
                    pd.SetValue(dataClass, this.m_software);
                }
            }
        }




        private void EnsureIdComune(DataClass dataClass)
        {
            PropertyDescriptor pd = TypeDescriptor.GetProperties(dataClass).Find("idcomune", true);

            if (pd != null)
                pd.SetValue(dataClass, this.m_idComune);
        }




        private void VerifyProperties(string keyProperty, string[] descriptionProperties)
        {
            PropertyDescriptorCollection pdc = TypeDescriptor.GetProperties(this.m_dataClass);

            PropertyDescriptor pd = pdc.Find(keyProperty, false);

            if (pd != null)
                this.m_searchedProperties.Add(new SearchedProperty(pd, true));

            for (int i = 0; i < descriptionProperties.Length; i++)
            {
                pd = pdc.Find(descriptionProperties[i], false);

                if (pd != null)
                    this.m_searchedProperties.Add(new SearchedProperty(pd, false));
            }
        }




        private DataClass CreateTypeInstance()
        {
            string typeName = this.m_searchedType;
            Type tipoCercato = null;


            if (this.m_dataClass != null)
            {
                tipoCercato = this.m_dataClass.GetType();
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
