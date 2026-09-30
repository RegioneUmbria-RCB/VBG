using System;
using System.Configuration;
using System.Diagnostics;
using System.Reflection;
using System.Xml;
using System.Xml.XPath;

namespace SIGePro.Net.Configuration
{
	/// <summary>
	/// Descrizione di riepilogo per ConfigSectionHandler.
	/// </summary>
	public class ConfigSectionHandler : IConfigurationSectionHandler
	{
		public ConfigSectionHandler()
		{
		}

		public object Create(object parent, object configContext, XmlNode section)
		{
			XPathNavigator nav = section.CreateNavigator (); 
			string typename = ( string ) nav.Evaluate ("string(@type)");

			if ((typename== null)||(typename=="")) 
			{
				throw new ConfigurationErrorsException("Non sono state trovate informazioni sui tipi nel file di configurazione.  Specificare un tipo nell'elemento config section.");
			}
			else
				Debug.WriteLine(String.Format("Trovato il typename= <{0}>\n", typename));

			// Tentativo 1: Provo a caricare il tipo in base al fully-qualified typename, o dall'assembly correntemente in esecuzione
//Following line has been commented out and new code added replacing 'Type.GetType' with 'BuildManager.GetType' 
//			Type t= Type.GetType ( typename , false );  
			Type t= System.Web.Compilation.BuildManager.GetType ( typename , false );  

			// Tentativo 2: Carico il tipo dall'assembly chiamante (Probabilmente è sbagliato)
			if (t==null) 
			{
				Debug.WriteLine(String.Format("  GetCallingAssembly(): ({0})", Assembly.GetCallingAssembly().FullName));
				t= Type.GetType ( typename + "," +Assembly.GetCallingAssembly().FullName , false );  // true== throwOnError
			}

			// Tentativo 3: se sono in una pagina we provo a caricarlo dagli assembly referenziati
			if (t==null) 
			{
				// Funziona solo se è una pagina asp.net
				// TODO:  supportare anche gli web services (l'handler non sarà System.Web.UI.Page)
				if (System.Web.HttpContext.Current.Handler is System.Web.UI.Page) 
				{
					System.Web.UI.Page p= (System.Web.UI.Page) System.Web.HttpContext.Current.Handler;
					Assembly a=  p.GetType().Assembly;
					Debug.WriteLine(String.Format("Assembly della pagina:...({0})", (a!=null) ? a.FullName : "??"));
					t= a.GetType ( typename , false );  // true== throwOnError

					// Tentativo 4: cerco negli assembly referenziati dall'assembly che contiene la pagina
					if (t==null) 
					{
						Debug.WriteLine(String.Format("Cerco gli assembly referenziati:"));
						try 
						{
							AssemblyName[] names= a.GetReferencedAssemblies();
							if (names!=null) 
							{
								foreach (AssemblyName n in names) 
								{
									if (t==null) 
									{
										Debug.WriteLine(String.Format("Cerco nell'assembly:...({0})", n));
										t= Type.GetType ( typename + "," + n.FullName , false );  // true== throwOnError
										if (t!=null) 
											Debug.WriteLine(String.Format("  Tipo {0} caricato", t.FullName));
									}
								}
							}
						}
						catch (System.Exception ex1) 
						{
							Debug.WriteLine(String.Format("Errore nella richiesta GetReferencedAssemblies(): {0}", ex1));
						}
					}
				}
			}

			if (t!= null) 
			{
				object o= null;
				try 
				{
					System.Xml.Serialization.XmlSerializer s = new System.Xml.Serialization.XmlSerializer (t); 
					o= s.Deserialize ( new System.Xml.XmlNodeReader (section)); 
				}
				catch (System.Exception ex2) 
				{
					Debug.WriteLine(String.Format("Impossibile deserializzare: {0}", ex2));
				}
				return o;
			}
			else 
			{
				return null;
			}
		}
	}
}
