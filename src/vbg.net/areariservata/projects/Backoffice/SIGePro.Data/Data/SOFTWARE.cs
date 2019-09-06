using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("SOFTWARE")]
	public class Software : BaseDataClass
	{
		string codice=null;
		[KeyField("CODICE",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=60, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string moduloopzionale=null;
		[DataField("MODULOOPZIONALE", Type=DbType.Decimal)]
		public string MODULOOPZIONALE
		{
			get { return moduloopzionale; }
			set { moduloopzionale = value; }
		}

		string descrizionelunga=null;
		[DataField("DESCRIZIONELUNGA",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONELUNGA
		{
			get { return descrizionelunga; }
			set { descrizionelunga = value; }
		}

		string accessorapido=null;
		[DataField("ACCESSORAPIDO", Type=DbType.Decimal)]
		public string ACCESSORAPIDO
		{
			get { return accessorapido; }
			set { accessorapido = value; }
		}

	}
}