using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PROTOCOLLO_RESPONSABILIFLUSSI")]
	public class Protocollo_ResponsabiliFlussi : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceresponsabile=null;
		[KeyField("CODICERESPONSABILE", Type=DbType.Decimal)]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
		}

		string codiceflusso=null;
		[KeyField("CODICEFLUSSO",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string CODICEFLUSSO
		{
			get { return codiceflusso; }
			set { codiceflusso = value; }
		}

	}
}