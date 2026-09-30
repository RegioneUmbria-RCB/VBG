using System;
using System.Data;

namespace PersonalLib2.Sql.Attributes
{
    /// <summary>
    /// Attibuto per definire una colonna del database.
    /// </summary>
    [AttributeUsage(AttributeTargets.Property), Serializable]
    public class BaseFieldAttribute : BehaviorAttribute
    {
        private string _columnName;
        private int _size = 0;
        private DbType _dbType = DbType.String;

        public BaseFieldAttribute(string columnName) : this(columnName, DbType.String)
        {
        }

        public BaseFieldAttribute(string columnName, DbType dbType) : this(columnName, dbType, 0)
        {
        }

        public BaseFieldAttribute(string columnName, DbType dbType, int size) : this(columnName, dbType, size, "=")
        {
        }

        public BaseFieldAttribute(string columnName, DbType dbType, int size, string compare) : this(columnName, dbType, size, compare, false)
        {
        }

        public BaseFieldAttribute(string columnName, DbType dbType, int size, string compare, bool caseSensitive) : this(columnName, dbType, size, compare, caseSensitive, null)
        {
        }

        public BaseFieldAttribute(string columnName, DbType dbType, int size, string compare, bool caseSensitive, string format) : this(columnName, dbType, size, compare, caseSensitive, format, BaseFieldScope.Select + BaseFieldScope.Update + BaseFieldScope.Insert + BaseFieldScope.Where)
        {
        }

        public BaseFieldAttribute(string columnName, DbType dbType, int size, string compare, bool caseSensitive, string format, int dbScope)
        {
            this._columnName = columnName;
            this._dbType = dbType;
            this._size = size;
            this.Compare = compare;
            this.CaseSensitive = caseSensitive;
            this.DateFormat = format;
            this.DbScope = dbScope;
        }

        /// <summary>
        /// E' il nome della colonna del database che è rappresentata dalla proprietà a cui è associato
        /// l'attributo BaseFieldAttribute.
        /// </summary>
        public string ColumnName
        {
            get { return this._columnName; }
            set { this._columnName = value; }
        }

        /// <summary>
        /// E' la dimenzione della colonna del database che è rappresentata dalla proprietà a cui è associato
        /// l'attributo BaseFieldAttribute.
        /// </summary>
        public int Size
        {
            get { return this._size; }
            set { this._size = value; }
        }

        /// <summary>
        /// E' il tipo della colonna del database che è rappresentata dalla proprietà a cui è associato
        /// l'attributo BaseFieldAttribute.
        /// </summary>
        public DbType Type
        {
            get { return this._dbType; }
            set { this._dbType = value; }
        }
    }
}

#region BaseFieldAttribute (KeyFieldAttribute+DataFieldAttribute)

#endregion