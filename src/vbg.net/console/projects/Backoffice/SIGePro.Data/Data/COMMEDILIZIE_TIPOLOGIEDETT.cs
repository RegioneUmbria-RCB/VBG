using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("COMMEDILIZIE_TIPOLOGIEDETT")]
	public class CommEdilizie_TipologieDett : BaseDataClass
	{
		string codcommtipologia=null;
		[KeyField("CODCOMMTIPOLOGIA", Type=DbType.Decimal)]
		public string CODCOMMTIPOLOGIA
		{
			get { return codcommtipologia; }
			set { codcommtipologia = value; }
		}

		string tipomovimento=null;
		[KeyField("TIPOMOVIMENTO",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOMOVIMENTO
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

	}
}