using System;
using System.Data;
using PersonalLib2.Sql.Attributes;
namespace Init.SIGePro.Data
{
	[DataTable("COMUNISECURITY_USERS")]
	[Serializable]
	public class ComuniSecurity_Users : BaseDataClass
	{
		string cs_user=null;
		[KeyField("CS_USER",Size=25, Type=DbType.String, CaseSensitive=false)]
		public string CS_USER
		{
			get { return cs_user; }
			set { cs_user = value; }
		}

		string cs_password=null;
		[DataField("CS_PASSWORD",Size=25, Type=DbType.String, CaseSensitive=false)]
		public string CS_PASSWORD
		{
			get { return cs_password; }
			set { cs_password = value; }
		}
	}
}
