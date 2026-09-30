using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIMOVIMENTO2")]
	[Serializable]
	public class TipiMovimento2 : BaseDataClass
	{

		#region Key Fields

		string codicetipo=null;
		[KeyField("CODICETIPO", Type=DbType.Decimal)]
		public string CODICETIPO
		{
			get { return codicetipo; }
			set { codicetipo = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string tipo=null;
		[DataField("TIPO",Size=120, Type=DbType.String, CaseSensitive=false)]
		public string TIPO
		{
			get { return tipo; }
			set { tipo = value; }
		}

	}
}