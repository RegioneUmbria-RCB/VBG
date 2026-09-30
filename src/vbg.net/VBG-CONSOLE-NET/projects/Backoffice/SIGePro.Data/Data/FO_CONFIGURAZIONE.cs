using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("FO_CONFIGURAZIONE")]
	public class Fo_Configurazione : BaseDataClass
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

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string fk_idconfigurazionebase=null;
		[DataField("FK_IDCONFIGURAZIONEBASE", Type=DbType.Decimal)]
		public string FK_IDCONFIGURAZIONEBASE
		{
			get { return fk_idconfigurazionebase; }
			set { fk_idconfigurazionebase = value; }
		}

		string valore=null;
		[DataField("VALORE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string VALORE
		{
			get { return valore; }
			set { valore = value; }
		}

	}
}