using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIMOVIMENTOONERI")]
	[Serializable]
	public class TipiMovimentoOneri : BaseDataClass
	{

		#region Key Fields

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string tipomovimento=null;
		[KeyField("TIPOMOVIMENTO",Size=6, Type=DbType.String)]
		public string TIPOMOVIMENTO
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string fk_coid=null;
		[KeyField("FK_COID", Type=DbType.Decimal)]
		public string FK_COID
		{
			get { return fk_coid; }
			set { fk_coid = value; }
		}

		string codicecomportamento=null;
		[KeyField("CODICECOMPORTAMENTO", Type=DbType.Decimal)]
		public string CODICECOMPORTAMENTO
		{
			get { return codicecomportamento; }
			set { codicecomportamento = value; }
		}

		#endregion

		string ggscadenza=null;
		[DataField("GGSCADENZA", Type=DbType.Decimal)]
		public string GGSCADENZA
		{
			get { return ggscadenza; }
			set { ggscadenza = value; }
		}



	}
}