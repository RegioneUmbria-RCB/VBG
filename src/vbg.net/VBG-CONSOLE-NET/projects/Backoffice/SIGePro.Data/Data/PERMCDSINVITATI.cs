using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PERMCDSINVITATI")]
	public class PermCdSInvitati : BaseDataClass
	{
		 string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceinvitato=null;
		[KeyField("CODICEINVITATO", Type=DbType.Decimal)]
		public string CODICEINVITATO
		{
			get { return codiceinvitato; }
			set { codiceinvitato = value; }
		}

		string codicecds=null;
		[KeyField("CODICECDS", Type=DbType.Decimal)]
		public string CODICECDS
		{
			get { return codicecds; }
			set { codicecds = value; }
		}

		string codiceatto=null;
		[DataField("CODICEATTO", Type=DbType.Decimal)]
		public string CODICEATTO
		{
			get { return codiceatto; }
			set { codiceatto = value; }
		}

		string codiceanagrafe=null;
		[DataField("CODICEANAGRAFE", Type=DbType.Decimal)]
		public string CODICEANAGRAFE
		{
			get { return codiceanagrafe; }
			set { codiceanagrafe = value; }
		}
	}
}