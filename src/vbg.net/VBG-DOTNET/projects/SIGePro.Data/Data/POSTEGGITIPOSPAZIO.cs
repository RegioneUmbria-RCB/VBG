using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("POSTEGGITIPOSPAZIO")]
	public class PosteggiTipoSpazio : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codice=null;
		[useSequence]
		[KeyField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string tipospazio=null;
		[DataField("TIPOSPAZIO",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TIPOSPAZIO
		{
			get { return tipospazio; }
			set { tipospazio = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}
	}
}