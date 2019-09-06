using System;

namespace Init.SIGePro.Attributes
{
	
	#region isRequiredAttribute
	[AttributeUsage(AttributeTargets.Property),Serializable]
	public class isRequiredAttribute : Attribute
	{
		string _msg;
		string _software;
		string _columnName;

		public isRequiredAttribute()
		{
		}
		
		public string MSG
		{
			get { return _msg; }
			set { _msg = value; }
		}

		public string SOFTWARE
		{
			get { return _software; }
			set { _software = value; }
		}

		public string COLUMNNAME
		{
			get { return _columnName;  }
			set { _columnName = value; }
		}
	}
	#endregion

	#region useSequenceAttribute
	[AttributeUsage(AttributeTargets.Property),Serializable]
	public class useSequenceAttribute : Attribute
	{
		string _columnName;

		public useSequenceAttribute()
		{
		}

		public string COLUMNNAME
		{
			get { return _columnName;  }
			set { _columnName = value; }
		}
	}
	#endregion
}
