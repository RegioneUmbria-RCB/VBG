using System;
using System.Collections.Specialized;
using Parser.Collections;

namespace Parser
{
	public enum queryType { Invalid, Select, Insert, Update, Delete };
	public class Query
	{
		public queryType Type;

		private StringCollection aTables = new StringCollection();
		private ColumnCollection aColumns = new ColumnCollection();
		private string sWhere = String.Empty;

		public StringCollection Tables
		{
			get
			{
				return aTables;
			}
			set
			{
				aTables = value;
			}
		}
		public ColumnCollection Columns
		{
			get
			{
				return aColumns;
			}
			set
			{
				aColumns = value;
			}
		}
		public string Where
		{
			get
			{
				return sWhere;
			}
			set
			{
				sWhere = value;
			}
		}

	}// END STRUCTURE DEFINITION QueryStruct

} // ManagerSIGeProExport

