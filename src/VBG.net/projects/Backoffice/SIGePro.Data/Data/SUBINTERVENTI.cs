using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("SUBINTERVENTI")]
	public class SubInterventi : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceintervento=null;
		[KeyField("CODICEINTERVENTO", Type=DbType.Decimal)]
		public string CODICEINTERVENTO
		{
			get { return codiceintervento; }
			set { codiceintervento = value; }
		}

		string numerosubintervento=null;
		[DataField("NUMEROSUBINTERVENTO", Type=DbType.Decimal)]
		public string NUMEROSUBINTERVENTO
		{
			get { return numerosubintervento; }
			set { numerosubintervento = value; }
		}

		string subintervento=null;
		[DataField("SUBINTERVENTO",Size=2000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string SUBINTERVENTO
		{
			get { return subintervento; }
			set { subintervento = value; }
		}

		string note=null;
		[DataField("NOTE",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}
	}
}