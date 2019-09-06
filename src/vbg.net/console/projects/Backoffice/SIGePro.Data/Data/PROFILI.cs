using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PROFILI")]
	public class Profili : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string nickname=null;
		[KeyField("NICKNAME",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NICKNAME
		{
			get { return nickname; }
			set { nickname = value; }
		}

		string password=null;
		[DataField("PASSWORD",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PASSWORD
		{
			get { return password; }
			set { password = value; }
		}

		string e_mail=null;
		[DataField("E_MAIL",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string E_MAIL
		{
			get { return e_mail; }
			set { e_mail = value; }
		}

		string homepage=null;
		[DataField("HOMEPAGE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string HOMEPAGE
		{
			get { return homepage; }
			set { homepage = value; }
		}
	}
}