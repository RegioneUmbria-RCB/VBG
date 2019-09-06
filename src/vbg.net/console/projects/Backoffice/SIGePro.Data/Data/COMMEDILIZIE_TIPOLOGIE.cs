using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("COMMEDILIZIE_TIPOLOGIE")]
	public class CommEdilizieTipologie : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codcommtipologia=null;
		[KeyField("CODCOMMTIPOLOGIA", Type=DbType.Decimal)]
		public string CODCOMMTIPOLOGIA
		{
			get { return codcommtipologia; }
			set { codcommtipologia = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string flag_disabilita=null;
		[DataField("FLAG_DISABILITA", Type=DbType.Decimal)]
		public string FLAG_DISABILITA
		{
			get { return flag_disabilita; }
			set { flag_disabilita = value; }
		}
	}
}