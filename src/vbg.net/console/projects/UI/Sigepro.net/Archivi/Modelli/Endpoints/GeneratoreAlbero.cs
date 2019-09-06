using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Collections.Generic;
using System.Reflection;
using System.Collections;
using PersonalLib2.Sql;
using System.Diagnostics;

namespace Sigepro.net.Archivi.Modelli.Endpoints
{
	public partial class GeneratoreAlbero
	{
		const string CLICK_HANDLER_INSERT_VALUE_OF = "InsertValueOf";
		const string CLICK_HANDLER_INSERT_FOR_EACH = "InsertForEach";
		const string CLICK_HANDLER_EXPAND_NODE = "ExpandNode";
		const string SEPARATORE_VALORI = "/";

		public class Nodo
		{
			private string m_valore;

			public string Valore
			{
				get { return m_valore; }
				set { m_valore = value; }
			}

			private string m_descrizione;

			public string Descrizione
			{
				get { return m_descrizione; }
				set { m_descrizione = value; }
			}

			private string m_nomeClickHandler;

			public string NomeClickHandler
			{
				get { return m_nomeClickHandler; }
				set { m_nomeClickHandler = value; }
			}

			private List<Nodo> m_nodiFiglio = new List<Nodo>();

			public List<Nodo> NodiFiglio
			{
				get { return m_nodiFiglio; }
				set { m_nodiFiglio = value; }
			}

			public Nodo(string descrizione , string valore , string clickHandler)
			{
				m_descrizione = descrizione;
				m_valore = valore;
				m_nomeClickHandler = clickHandler;
			}

			public void OrdinaRicorsivo()
			{
				m_nodiFiglio.Sort(delegate(Nodo n1, Nodo n2)
									{
										if (n1.m_nodiFiglio.Count > 0 && n2.m_nodiFiglio.Count == 0)
											return -1;

										if (n1.m_nodiFiglio.Count == 0 && n2.m_nodiFiglio.Count > 0)
											return 1;

										return n1.Descrizione.CompareTo(n2.Descrizione);
									});

				m_nodiFiglio.ForEach(delegate(Nodo n) { n.OrdinaRicorsivo(); });
			}

			public void PropagaValore()
			{
				RiceviValoreDaPadre(String.Empty);
			}

			protected void RiceviValoreDaPadre(string valorePadre)
			{
				if (valorePadre != String.Empty)
					this.Valore = valorePadre + SEPARATORE_VALORI + this.Valore;

				for (int i = 0; i < m_nodiFiglio.Count; i++)
				{
					m_nodiFiglio[i].RiceviValoreDaPadre(this.Valore);
				}
			}
		}


		Type m_classType;

		public GeneratoreAlbero(Type classType)
		{
			m_classType = classType;
		}


		public Nodo AnalizzaAlberoClasse()
		{
			Nodo root = new Nodo(m_classType.Name, m_classType.Name , EstraiTipoClickHandler( m_classType ) );

			AnalizzaRicorsivo(m_classType, root);

			root.OrdinaRicorsivo();
			root.PropagaValore();

			return root;
		}

		private string EstraiTipoClickHandler(Type t)
		{
			if (TypeIsDataClass(t))
				return CLICK_HANDLER_EXPAND_NODE;

			if (TypeIsCollection( t ))
				return CLICK_HANDLER_EXPAND_NODE;

			return CLICK_HANDLER_INSERT_VALUE_OF;
		}

		private bool TypeIsCollection(Type t)
		{
			return typeof(IList).IsAssignableFrom(t);
		}

		private bool TypeIsDataClass(Type t)
		{
			return t.IsSubclassOf(typeof(DataClass));
		}

		private void AnalizzaRicorsivo(Type t, Nodo nodo)
		{
			foreach (PropertyInfo proprietaElemento in t.GetProperties())
			{
				Type tipoProprieta = proprietaElemento.PropertyType;

				string nomeNodo = proprietaElemento.Name;

				if (nomeNodo == "OrderBy" ||
						nomeNodo == "OthersJoinClause" ||
						nomeNodo == "OthersSelectColumns" ||
						nomeNodo == "OthersTables" ||
						nomeNodo == "OthersWhereClause" ||
						nomeNodo == "DataTableName" ||
						nomeNodo == "UseForeign" ||
						nomeNodo == "SelectColumns") continue;

				string valoreNodo = proprietaElemento.Name;

				Nodo nuovoNodo = new Nodo(nomeNodo, valoreNodo, EstraiTipoClickHandler( tipoProprieta ));
				nodo.NodiFiglio.Add(nuovoNodo);

				if ( TypeIsCollection(tipoProprieta) )
				{
					// è una lista generica? tipo List<T>
					if (tipoProprieta.IsGenericType)
					{
						Type[] genericArgs = tipoProprieta.GetGenericArguments();

						Type tipoFiglio = genericArgs[0];

						// Per ora gestisco solo generics con un solo tipo generico
						string nomeNodoFiglio = tipoFiglio.Name;
						string valoreNodoFiglio = tipoFiglio.Name;

						Nodo nodoFiglio = new Nodo(nomeNodoFiglio, valoreNodoFiglio, CLICK_HANDLER_INSERT_FOR_EACH);
						nuovoNodo.NodiFiglio.Add(nodoFiglio);
						AnalizzaRicorsivo(tipoFiglio, nodoFiglio);
					}
					else  // è una lista non generica?
					{
						PropertyInfo proprietaFiglio = tipoProprieta.GetProperty("Item", new Type[] { typeof(int) });

						if (proprietaFiglio != null)
						{
							string nomeNodoFiglio = proprietaFiglio.PropertyType.Name;
							string valoreNodoFiglio = proprietaFiglio.PropertyType.Name;
							Nodo nodoFiglio = new Nodo(nomeNodoFiglio, valoreNodoFiglio, CLICK_HANDLER_INSERT_FOR_EACH);

							nuovoNodo.NodiFiglio.Add(nodoFiglio);

							AnalizzaRicorsivo(proprietaFiglio.PropertyType, nodoFiglio);
						}
					}
				}
				else if ( TypeIsDataClass( tipoProprieta) )
				{
					AnalizzaRicorsivo(tipoProprieta, nuovoNodo);
				}
			}
		}



		public static void Test()
		{
			GeneratoreAlbero ga = new GeneratoreAlbero(typeof(Init.SIGePro.Data.Istanze));
			Nodo n = ga.AnalizzaAlberoClasse();

			OutputValoriTest(0, n);


		}

		private static void OutputValoriTest(int livello, Nodo nodo)
		{
			for (int i = 0; i < livello; i++)
				Debug.Write("\t");

			Debug.WriteLine(nodo.Descrizione);

			foreach (Nodo child in nodo.NodiFiglio)
			{
				OutputValoriTest(livello + 1, child);
			}
		}
	}
}
