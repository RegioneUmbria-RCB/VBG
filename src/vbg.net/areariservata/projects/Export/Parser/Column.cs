// Static Model

namespace Parser
{

	public class Column
	{
		private string sName;
		private string sValue;

		public Column( string pName, string pValue )
		{
			this.sName = pName;
			this.sValue = pValue;
		}

		public string Name
		{
			get
			{
				return sName;
			}
			set
			{
				sName = value;
			}
		}

		public string Value
		{
			get
			{
				return sValue;
			}
			set
			{
				sValue = value;
			}
		}

	}// END STRUCTURE DEFINITION Columns

} // ManagerSIGeProExport