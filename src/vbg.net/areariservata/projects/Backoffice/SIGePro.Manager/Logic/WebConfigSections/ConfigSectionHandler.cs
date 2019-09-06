using System;
using System.Collections.Specialized;
using System.Configuration;
using System.Diagnostics;
using System.Reflection;
using System.Xml;
using System.Xml.XPath;

namespace Init.SIGePro.Manager.Logic.WebConfigSections
{
	/// <summary>
	/// Descrizione di riepilogo per ConfigSectionHandler.
	/// </summary>
	public class ConfigSectionHandler : IConfigurationSectionHandler
	{
		StringDictionary m_searchedAssemblies = new StringDictionary();

		public ConfigSectionHandler()
		{
		}

		public object Create(object parent, object configContext, XmlNode section)
		{
			XPathNavigator nav = section.CreateNavigator();
			string typename = (string)nav.Evaluate("string(@type)");

			if ((typename == null) || (typename == ""))
			{
				throw new ConfigurationException("Non sono state trovate informazioni sui tipi nel file di configurazione.  Specificare un tipo nell'elemento config section.");
			}
			else
				Debug.WriteLine(String.Format("Trovato il typename= <{0}>\n", typename));

			// Tentativo 1: Provo a caricare il tipo in base al fully-qualified typename, o dall'assembly correntemente in esecuzione
			Type t = Type.GetType(typename, false);

			// Tentativo 2: Carico il tipo dall'assembly chiamante (Probabilmente è sbagliato)
			if (t == null)
			{
				Debug.WriteLine(String.Format("  GetCallingAssembly(): ({0})", Assembly.GetCallingAssembly().FullName));
				t = Type.GetType(typename + "," + Assembly.GetCallingAssembly().FullName, false);  // true== throwOnError
			}

			// Tentativo 3: se sono in una pagina we provo a caricarlo dagli assembly referenziati
			if (t == null)
			{
				// Funziona solo se è una pagina asp.net
				// TODO:  supportare anche gli web services (l'handler non sarà System.Web.UI.Page)
				if (System.Web.HttpContext.Current.Handler is System.Web.UI.Page)
				{
					System.Web.UI.Page p = (System.Web.UI.Page)System.Web.HttpContext.Current.Handler;
					Assembly a = p.GetType().Assembly;
					Debug.WriteLine(String.Format("Assembly della pagina:...({0})", (a != null) ? a.FullName : "??"));
					t = a.GetType(typename, false);  // true== throwOnError

					// Tentativo 4: cerco negli assembly referenziati dall'assembly che contiene la pagina
					if (t == null)
					{
						try
						{
							Debug.WriteLine(String.Format("Cerco negli assembly referenziati:"));
							t = SearchTypeInReferencedAssemblies(typename, a);
						}
						catch (System.Exception ex1)
						{
							Debug.WriteLine(String.Format("Errore nella richiesta GetReferencedAssemblies(): {0}", ex1));
						}
					}
				}
			}

			if (t != null)
			{
				object o = null;
				try
				{
					System.Xml.Serialization.XmlSerializer s = new System.Xml.Serialization.XmlSerializer(t);
					XmlNodeReader rd = new System.Xml.XmlNodeReader(section);
					o = s.Deserialize(rd);
				}
				catch (System.Exception ex2)
				{
					string errMsg = "Init.SIGePro.WebConfigSections.ConfigSectionHandler->Impossibile deserializzare il tipo " + t.ToString() + " dal web.config.\r\n\r\n Dettagli dell'errore:\r\n" + ex2.ToString();

					if (System.Web.HttpContext.Current != null)
					{
						System.Web.HttpContext.Current.Response.Write(errMsg.Replace("\n", "<br>"));
						System.Web.HttpContext.Current.Response.End();
					}
					else
					{
						throw new ConfigurationException(errMsg);
					}

				}
				return o;
			}
			else
			{
				return null;
			}
		}

		protected Type SearchTypeInReferencedAssemblies(string typename, Assembly a)
		{
			if (m_searchedAssemblies.ContainsKey(a.FullName)) return null;

			m_searchedAssemblies.Add(a.FullName, "1");

			Debug.WriteLine(String.Format("Cerco negli assembly referenziati da :...({0})", a.FullName));
			AssemblyName[] names = a.GetReferencedAssemblies();
			Type t = null;

			if (names == null || names.Length == 0) return null;

			foreach (AssemblyName n in names)
			{
				Debug.WriteLine(String.Format("Cerco nell'assembly:...({0})", n));
				t = Type.GetType(typename + "," + n.FullName, false);

				if (t != null)
				{
					Debug.WriteLine(String.Format("  Tipo {0} caricato dall'assembly {1}", t.FullName, n));
					return t;
				}

				t = SearchTypeInReferencedAssemblies(typename, Assembly.Load(n));

				if (t != null) return t;
			}

			return null;
		}
	}

}
