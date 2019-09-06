using System;
using System.Collections;
using System.Reflection;
using System.Xml.Serialization;
using PersonalLib2.Sql.Attributes;
using System.Collections.Generic;

namespace PersonalLib2.Sql
{
	public enum useForeignEnum {No, Yes , Recoursive};
	/// <summary>
	/// Descrizione di riepilogo per GenericData.
	/// </summary>
	[Serializable]
	public class DataClass : ICloneable
	{
		private string m_dataTableName = null;
		private ArrayList _othersTables = new ArrayList();
		private ArrayList _othersJoinClause = new ArrayList();
		private ArrayList _othersWhereClause = new ArrayList();
		private ArrayList _othersSelectColumns = new ArrayList();
		private string _selectColumns;
		private useForeignEnum _useForeign;
		private string _orderBy;

		public DataClass()
		{
			_useForeign = useForeignEnum.No;
		}

		#region Properties
		[XmlIgnore]
		public useForeignEnum UseForeign
		{
			get { return _useForeign; }
			set { _useForeign = value; }
		}

		[SoapIgnore]
		[XmlIgnore]
		public ArrayList OthersTables
		{
			get { return _othersTables; }
			set { _othersTables = value; }
		}

		[SoapIgnore]
		[XmlIgnore]
		public ArrayList OthersJoinClause
		{
			get { return _othersJoinClause; }
			set { _othersJoinClause = value; }
		}

		[SoapIgnore]
		[XmlIgnore]
		public ArrayList OthersWhereClause
		{
			get { return _othersWhereClause; }
			set { _othersWhereClause = value; }
		}

		[SoapIgnore]
		[XmlIgnore]
		public ArrayList OthersSelectColumns
		{
			get { return _othersSelectColumns; }
			set { _othersSelectColumns = value; }
		}

		[SoapIgnore]
		[XmlIgnore]
		public string SelectColumns
		{
			get { return _selectColumns; }
			set { _selectColumns = value; }
		}

		[SoapIgnore]
		[XmlIgnore]
		public string OrderBy
		{
			get { return _orderBy; }
			set { _orderBy = value; }
		}

		/// <summary>
		/// Ritorna il nome a cui mappa la classe
		/// </summary>
		[SoapIgnore]
		[XmlIgnore]
		public string DataTableName
		{
			get
			{
				if ( m_dataTableName == null )
				{

					DataTableAttribute[] dataTables = (DataTableAttribute[]) this.GetType().GetCustomAttributes(typeof (DataTableAttribute), true);

					// Nessuna DataTable
					if ( dataTables == null || dataTables.Length != 1 )
						m_dataTableName = String.Empty;
					else
						m_dataTableName = dataTables[0].TableName;
				}

				return m_dataTableName;
			}
		}

		#endregion

		#region Clone function

		/// <summary>
		/// Crea un oggetto dello stesso tipo di quello passato ed imposta 
		/// le proprietà del nuovo oggetto = a quelle di quello passato.
		/// </summary>
		/// <returns>-
		/// Un oggetto dello stesso tipo di quello passato.
		/// Con le proprietà impostate al valore dell'oggetto passato.</returns>
		public object Clone()
		{
			Type objType = this.GetType();
			object tClass = Activator.CreateInstance(objType);

			foreach (PropertyInfo property in objType.GetProperties())
			{
				if (property.GetSetMethod() != null)
				{
					if (property.PropertyType == typeof (ArrayList))
					{
						// TODO: sarebbe meglio invocare il metodo clone senza specificare
						//		 Personal.Collections.ArrayList sia sul confronto precedente
						//		 che nell'assegnazione successiva.
						ArrayList myarO = (ArrayList) property.GetValue(this, null);
						ArrayList myar = new ArrayList();
						for (int i = 0; i < myarO.Count; i++)
						{
							myar.Add((object) myarO[i]);
						}
						property.SetValue(tClass, myar, null);
					}
                    else if (property.PropertyType.IsGenericType && (property.PropertyType.GetGenericTypeDefinition() == typeof(List<>)))
                    {
                        if (property.PropertyType.GetGenericArguments()[0].IsSubclassOf(typeof(DataClass)))
                        {
                            //è una lista di dataclass
                            IList dc0 = (IList)property.GetValue(this, null);
                            IList newList = ((IList)Activator.CreateInstance(property.PropertyType));
                            for (int i = 0; i < dc0.Count; i++)
                            {
                                newList.Add(((DataClass)dc0[i]).Clone());

                            }
                            property.SetValue(tClass, newList, null);
                        }
                        else
                        {
                            property.SetValue(tClass, property.GetValue(this, null), null);
                        }
                    }
                    else
                    {
                        property.SetValue(tClass, property.GetValue(this, null), null);
                    }
				}
			}
			return tClass;
		}

		#endregion


		public List<PropertyInfo> GetKeyFields()
		{
			PropertyInfo[] props = GetType().GetProperties();

			List<PropertyInfo> ret = new List<PropertyInfo>();

			for (int i = 0; i < props.Length; i++)
			{
				object[] attr = props[i].GetCustomAttributes(typeof(KeyFieldAttribute),true);

				if (attr.Length > 0)
					ret.Add(props[i]);
			}

			return ret;
		}

		public List<KeyValuePair<DataFieldAttribute,PropertyInfo>> GetDataFields()
		{
			PropertyInfo[] props = GetType().GetProperties();

			List<KeyValuePair<DataFieldAttribute, PropertyInfo>> ret = new List<KeyValuePair<DataFieldAttribute, PropertyInfo>>();

			for (int i = 0; i < props.Length; i++)
			{
				object[] attr = props[i].GetCustomAttributes(typeof(DataFieldAttribute),true);

				if (attr.Length > 0)
				{
					ret.Add(new KeyValuePair<DataFieldAttribute, PropertyInfo>((attr[0] as DataFieldAttribute), props[i]));
				}
			}

			return ret;
		}
	}
}