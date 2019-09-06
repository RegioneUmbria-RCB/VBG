using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEONERI_DETTAGLIO")]
	public class IstanzeOneri_Dettaglio : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
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
	}
}