using System;
using PersonalLib2.Exceptions;
using PersonalLib2.Sql.Collections;
using System.Collections;
using System.Collections.Generic;
using System.Reflection;

namespace PersonalLib2.Sql.Attributes
{
	/// <summary>
	/// Attributo associabile ad una proprietà di una classe di tipo DataClass, viene utilizzato per specificare
	/// che la proprietà contiene una tabella correlata.
	/// </summary>
	[AttributeUsage(AttributeTargets.Property, AllowMultiple=false), Serializable]
	public class ForeignKeyAttribute : Attribute
	{
		internal static DataClass InstantiateDataClass(PropertyInfo property)
		{
			Type propType = property.PropertyType;

			if (propType.IsSubclassOf(typeof(DataClass)))	// La proprietà è una dataclass, la istanzio direttamente
				return (DataClass)Activator.CreateInstance(propType);


			if (propType.IsGenericType && propType.GetGenericTypeDefinition() == typeof(List<>))	// La proprietà è una lista generica, istanzio il tipo dell'item contenuto
			{
				propType = propType.GetGenericArguments()[0];

				if (propType.IsSubclassOf(typeof(DataClass)))
					return (DataClass)Activator.CreateInstance(propType);
			}

			string errMsg = "Il tipo {0} non è supportato dall'attributo ForeignKeyAttribute, la proprietà decorata dall'attributo deve essere di tipo DataClass o List<DataClass> ";

			throw new NotSupportedException( String.Format( errMsg , propType.Name.ToString() ) );
		}

		internal static bool PropertyIsList(PropertyInfo property)
		{
			return property.PropertyType.IsGenericType && property.PropertyType.GetGenericTypeDefinition() == typeof(List<>);
		}

		//private Type	_foreignDataClassType;
		private string	_foreignPropertiesName;
		private string	_classPropertiesName;

		public ForeignKeyAttribute(/*Type foreignDataClassType,*/ string classPropertiesName, string foreignPropertiesName)
		{
			//ForeignDataClassType = foreignDataClassType;
			ClassPropertiesName = classPropertiesName;
			ForeignPropertiesName = foreignPropertiesName;
		}
/*
		/// <summary>
		/// E' la classe che descrive la tabella che va in foreign.
		/// </summary>
		public Type ForeignDataClassType
		{
			get { return _foreignDataClassType; }
			set
			{
				bool isDataClass = value.IsSubclassOf(typeof(DataClass));
				bool isTemplateCollection = value.IsSubclassOf( typeof( ICollection<> ) );

				if ( isDataClass || isTemplateCollection )
				{
					_foreignDataClassType = value;
					return;
				}

				throw new DataClassException("Il parametro ForeignDataClassType dell'attributo ForeignKeyAttribute deve essere di tipo DaaClass o di tipo ICollection<T> invece che di tipo " + value.ToString());
			}
		}
*/
		/// <summary>
		/// E' la lista dei nomi delle proprietà della classe ForeignDataClass che sono legate in join
		/// con le proprietà della classe a cui appartiene la proprietà associata all'attributo ForeignKeyAttribute.
		/// Generalmente la lista è composta da un solo elemento.
		/// Più nomi sono separati da un ";".
		/// </summary>
		public string ForeignPropertiesName
		{
			get { return _foreignPropertiesName; }
			set { _foreignPropertiesName = value; }
		}

		/// <summary>
		/// E' la lista dei nomi delle proprietà che si legano in join con ForeignPropertiesName.
		/// </summary>
		public string ClassPropertiesName
		{
			get { return _classPropertiesName; }
			set { _classPropertiesName = value; }
		}
	}

	
}