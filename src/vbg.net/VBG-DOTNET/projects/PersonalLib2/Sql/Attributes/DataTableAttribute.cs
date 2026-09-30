using System;

namespace PersonalLib2.Sql.Attributes
{
	/// <summary>
	/// Descrive la tabella del DataBase
	/// </summary>
	[AttributeUsage(AttributeTargets.Class | AttributeTargets.Struct), Serializable]
	public class DataTableAttribute : Attribute
	{
		private string tableName;

		public DataTableAttribute(string tableName)
		{
			this.tableName = tableName;
		}

		public string TableName
		{
			get { return tableName; }
			set { tableName = value; }
		}
	}
}