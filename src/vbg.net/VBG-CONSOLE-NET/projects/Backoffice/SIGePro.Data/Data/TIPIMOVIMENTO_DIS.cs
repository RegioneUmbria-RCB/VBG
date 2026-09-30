using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIMOVIMENTO_DIS")]
	public class TipiMovimento_Dis : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceistanza=null;
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string tipomovimento=null;
		[KeyField("TIPOMOVIMENTO",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOMOVIMENTO
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string codiceinventario=null;
		[KeyField("CODICEINVENTARIO", Type=DbType.Decimal)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string codiceamministrazione=null;
		[KeyField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

        DateTime? datascad = null;
		[KeyField("DATASCAD", Type=DbType.DateTime)]
		public DateTime? DATASCAD
		{
			get { return datascad; }
            set { datascad = VerificaDataLocale(value); }
		}

		string codammrichiedente=null;
		[DataField("CODAMMRICHIEDENTE", Type=DbType.Decimal)]
		public string CODAMMRICHIEDENTE
		{
			get { return codammrichiedente; }
			set { codammrichiedente = value; }
		}
	}
}