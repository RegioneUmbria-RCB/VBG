using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("BACHECALAVOROCONFIGURAZIONE")]
	public class BachecaLavoroConfigurazione : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string offrolavoroindicazioni=null;
		[DataField("OFFROLAVOROINDICAZIONI",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OFFROLAVOROINDICAZIONI
		{
			get { return offrolavoroindicazioni; }
			set { offrolavoroindicazioni = value; }
		}

		string offrolavorooggetto=null;
		[DataField("OFFROLAVOROOGGETTO", Type=DbType.Decimal)]
		public string OFFROLAVOROOGGETTO
		{
			get { return offrolavorooggetto; }
			set { offrolavorooggetto = value; }
		}

		string offrolavoroemail=null;
		[DataField("OFFROLAVOROEMAIL",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OFFROLAVOROEMAIL
		{
			get { return offrolavoroemail; }
			set { offrolavoroemail = value; }
		}

		string cercolavoroindicazioni=null;
		[DataField("CERCOLAVOROINDICAZIONI",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CERCOLAVOROINDICAZIONI
		{
			get { return cercolavoroindicazioni; }
			set { cercolavoroindicazioni = value; }
		}

		string cercolavorooggetto=null;
		[DataField("CERCOLAVOROOGGETTO", Type=DbType.Decimal)]
		public string CERCOLAVOROOGGETTO
		{
			get { return cercolavorooggetto; }
			set { cercolavorooggetto = value; }
		}

		string cercolavoroemail=null;
		[DataField("CERCOLAVOROEMAIL",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CERCOLAVOROEMAIL
		{
			get { return cercolavoroemail; }
			set { cercolavoroemail = value; }
		}

	}
}