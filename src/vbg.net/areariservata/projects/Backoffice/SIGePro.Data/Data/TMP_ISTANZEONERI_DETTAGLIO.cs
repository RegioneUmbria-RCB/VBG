using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_ISTANZEONERI_DETTAGLIO")]
	public class Tmp_IstanzeOneri_Dettaglio : BaseDataClass
	{
		string id=null;
		[DataField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string codiceistanza=null;
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string codiceinventario=null;
		[DataField("CODICEINVENTARIO", Type=DbType.Decimal)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string importo=null;
		[DataField("IMPORTO", Type=DbType.Decimal)]
		public string IMPORTO
		{
			get { return importo; }
			set { importo = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

	}
}