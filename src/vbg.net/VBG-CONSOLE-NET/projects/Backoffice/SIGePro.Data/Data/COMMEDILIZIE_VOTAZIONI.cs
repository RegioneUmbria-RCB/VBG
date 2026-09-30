using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("COMMEDILIZIE_VOTAZIONI")]
	public class CommEdilizie_Votazioni : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicecommissione=null;
		[KeyField("CODICECOMMISSIONE", Type=DbType.Decimal)]
		public string CODICECOMMISSIONE
		{
			get { return codicecommissione; }
			set { codicecommissione = value; }
		}

		string codiceresponsabile=null;
		[KeyField("CODICERESPONSABILE", Type=DbType.Decimal)]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
		}

		string codiceriga=null;
		[KeyField("CODICERIGA", Type=DbType.Decimal)]
		public string CODICERIGA
		{
			get { return codiceriga; }
			set { codiceriga = value; }
		}

		string presente=null;
		[DataField("PRESENTE", Type=DbType.Decimal)]
		public string PRESENTE
		{
			get { return presente; }
			set { presente = value; }
		}

		string voto=null;
		[DataField("VOTO", Type=DbType.Decimal)]
		public string VOTO
		{
			get { return voto; }
			set { voto = value; }
		}

	}
}