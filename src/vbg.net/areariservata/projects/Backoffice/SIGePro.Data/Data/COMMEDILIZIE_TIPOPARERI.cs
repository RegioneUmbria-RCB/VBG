using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("COMMEDILIZIE_TIPOPARERI")]
	public class CommEdilizie_TipoPareri : BaseDataClass
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
		[DataField("DESCRIZIONE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string tipomovimento=null;
		[DataField("TIPOMOVIMENTO",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOMOVIMENTO
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string esito=null;
		[DataField("ESITO", Type=DbType.Decimal)]
		public string ESITO
		{
			get { return esito; }
			set { esito = value; }
		}

	}
}