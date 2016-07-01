using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("COMMEDILIZIE_CARICA")]
	public class CommEdilizie_Carica : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codice=null;
		[KeyField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string dirittovoto=null;
		[DataField("DIRITTOVOTO", Type=DbType.Decimal)]
		public string DIRITTOVOTO
		{
			get { return dirittovoto; }
			set { dirittovoto = value; }
		}

	}
}