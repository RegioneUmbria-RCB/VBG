using System;

namespace PersonalLib2.Sql.Attributes
{
	/// <summary>
	/// Attributo per definire il comportamento di una colonna del database.
	/// </summary>
	[AttributeUsage(AttributeTargets.Property), Serializable]
	public class BehaviorAttribute : Attribute
	{
		private string _compare = "=";
		private bool _caseSensitive = true;
		private string _dateFormat = null;
		private int _dbscope = BaseFieldScope.Select + BaseFieldScope.Update + BaseFieldScope.Insert + BaseFieldScope.Where;
		private SetType _setType = SetType.Always;

		/// <summary>
		/// Definisce gli attributi di comportamento per una proprietà che descrive una colonna del database
		/// </summary>
		/// <param name="compare">Modalità di comparazione della colonna con il valore specificato nella proprietà che la descrive.
		///	Es. =,<=,like, etc...</param>
		/// <param name="caseSensitive">Modalità di comparazione della colonna con il valore specificato nella proprietà che la descrive.
		/// false = la comparazione avviene senza conversioni.
		/// true = la comparazione avviene dopo aver convertito la colonna ed il contenuto da comparare in maiuscole.
		/// E' valido solo per tipi di dato string.
		/// Es. se true Upper</param>
		/// <param name="format"></param>
		/// <param name="dbScope"></param>
		/// <param name="setMode"></param>
		public BehaviorAttribute(string compare, bool caseSensitive, string format, int dbScope, SetType setMode)
		{
			this.Compare = compare;
			this.CaseSensitive = caseSensitive;
			this.DateFormat = format;
			this.DbScope = dbScope;
			this.SetMode = setMode;
		}

		public BehaviorAttribute(string compare, bool caseSensitive, string format, int dbScope) : this(compare, caseSensitive, format, dbScope, SetType.Always)
		{
		}

		public BehaviorAttribute(string compare, bool caseSensitive, string format) : this(compare, caseSensitive, format, BaseFieldScope.Select + BaseFieldScope.Update + BaseFieldScope.Insert + BaseFieldScope.Where, SetType.Always)
		{
		}

		public BehaviorAttribute(string compare, bool caseSensitive) : this(compare, caseSensitive, null, BaseFieldScope.Select + BaseFieldScope.Update + BaseFieldScope.Insert + BaseFieldScope.Where, SetType.Always)
		{
		}

		public BehaviorAttribute(string compare) : this(compare, true, null, BaseFieldScope.Select + BaseFieldScope.Update + BaseFieldScope.Insert + BaseFieldScope.Where, SetType.Always)
		{
		}

		public BehaviorAttribute() : this("=", true, null, BaseFieldScope.Select + BaseFieldScope.Update + BaseFieldScope.Insert + BaseFieldScope.Where, SetType.Always)
		{
		}


		/// <summary>
		/// E' il tipo di confronto di default che viene utilizzato nelle condizioni
		/// Where tra la il valore della proprietà BaseFieldAttribute e la colonna del database
		/// che rappresenta.
		/// </summary>
		public string Compare
		{
			get { return _compare; }
			set { _compare = value; }
		}


		/// <summary>
		/// Se true allora la proprietà di tipo BaseFieldAttribute nelle operazioni di
		/// Where viene confrontata con la rispettiva colonna del database utilizzando la modalità MAIUSCOLE/minuscole.
		/// </summary>
		public bool CaseSensitive
		{
			get { return _caseSensitive; }
			set { _caseSensitive = value; }
		}

		/// <summary>
		/// Utilizzate per convertire una colonna del database, di tipo stringa, in un BaseFieldAttribute di tipo Date.
		/// Es. 
		///		una colonna ColumnDate varchar2(8) che contiene yyyyMMdd per essere convertita in un BaseFieldAttribute di tipo
		///		Date deve avere impostato l'attributo DateFormat="yyyyMMdd".
		/// </summary>
		public string DateFormat
		{
			get { return _dateFormat; }
			set { _dateFormat = value; }
		}

		/// <summary>
		/// Specifica che la proprietà di tipo BaseFieldAttribute deve essere utilizzata solo 
		/// per operazioni specificate in questo attributo.
		/// </summary>
		public int DbScope
		{
			get { return _dbscope; }
			set { _dbscope = value; }
		}

		/// <summary>
		/// Vedi PersonalLib2.Sql.Attributes.SetType.
		/// </summary>
		public SetType SetMode
		{
			get { return _setType; }
			set { _setType = value; }
		}
	}
}