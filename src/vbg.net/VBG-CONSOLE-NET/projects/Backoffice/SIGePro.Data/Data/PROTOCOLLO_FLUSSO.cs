using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PROTOCOLLO_FLUSSO")]
	public class Protocollo_Flusso : BaseDataClass
	{
		string codice=null;
		[KeyField("CODICE",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

	}
}