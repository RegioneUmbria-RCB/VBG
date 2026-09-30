using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("IMPIANTIPUBBLICITARI")]
	public class ImpiantiPubblicitari : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceimpianto=null;
		[KeyField("CODICEIMPIANTO", Type=DbType.Decimal)]
		public string CODICEIMPIANTO
		{
			get { return codiceimpianto; }
			set { codiceimpianto = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string impianto=null;
		[DataField("IMPIANTO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string IMPIANTO
		{
			get { return impianto; }
			set { impianto = value; }
		}

		string fk_natura=null;
		[DataField("FK_NATURA", Type=DbType.Decimal)]
		public string FK_NATURA
		{
			get { return fk_natura; }
			set { fk_natura = value; }
		}

		string fk_categoria=null;
		[DataField("FK_CATEGORIA", Type=DbType.Decimal)]
		public string FK_CATEGORIA
		{
			get { return fk_categoria; }
			set { fk_categoria = value; }
		}

	}
}