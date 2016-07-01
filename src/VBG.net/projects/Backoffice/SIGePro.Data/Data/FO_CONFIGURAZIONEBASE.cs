using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("FO_CONFIGURAZIONEBASE")]
	public class Fo_ConfigurazioneBase : BaseDataClass
	{
		string codice=null;
		[KeyField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string etichetta=null;
		[DataField("ETICHETTA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ETICHETTA
		{
			get { return etichetta; }
			set { etichetta = value; }
		}

		string chiave=null;
		[DataField("CHIAVE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CHIAVE
		{
			get { return chiave; }
			set { chiave = value; }
		}

		string fk_contesto=null;
		[DataField("FK_CONTESTO",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string FK_CONTESTO
		{
			get { return fk_contesto; }
			set { fk_contesto = value; }
		}

	}
}