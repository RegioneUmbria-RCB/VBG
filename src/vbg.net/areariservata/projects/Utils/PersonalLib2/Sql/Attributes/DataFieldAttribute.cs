using System;
using System.Data;

namespace PersonalLib2.Sql.Attributes
{
	[AttributeUsage(AttributeTargets.Property), Serializable]
	public class DataFieldAttribute : BaseFieldAttribute
	{
		public DataFieldAttribute(string columnName) : this(columnName, DbType.String)
		{
		}

		public DataFieldAttribute(string columnName, DbType dbType) : this(columnName, dbType, 0)
		{
		}

		public DataFieldAttribute(string columnName, DbType dbType, int size) : this(columnName, dbType, size, "=")
		{
		}

		public DataFieldAttribute(string columnName, DbType dbType, int size, string compare) : this(columnName, dbType, size, compare, true)
		{
		}

		public DataFieldAttribute(string columnName, DbType dbType, int size, string compare, bool caseSensitive) : this(columnName, dbType, size, compare, caseSensitive, null)
		{
		}

		public DataFieldAttribute(string columnName, DbType dbType, int size, string compare, bool caseSensitive, string format) : this(columnName, dbType, size, compare, caseSensitive, format, BaseFieldScope.Select + BaseFieldScope.Update + BaseFieldScope.Insert + BaseFieldScope.Where)
		{
		}

		public DataFieldAttribute(string columnName, DbType dbType, int size, string compare, bool caseSensitive, string format, int dbScope) : this(columnName, dbType, size, compare, caseSensitive, format, dbScope, SetType.Always)
		{
		}

		public DataFieldAttribute(string columnName, DbType dbType, int size, string compare, bool caseSensitive, string format, int dbScope, SetType setMode) : base(columnName, dbType, size, compare, caseSensitive, format, dbScope, setMode)
		{
		}
	} ;
}