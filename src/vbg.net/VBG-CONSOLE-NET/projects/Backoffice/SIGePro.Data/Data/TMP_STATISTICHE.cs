using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_STATISTICHE")]
	public class Tmp_Statistiche : BaseDataClass
	{
		string tipo=null;
		[DataField("TIPO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TIPO
		{
			get { return tipo; }
			set { tipo = value; }
		}

		string testo=null;
		[DataField("TESTO",Size=180, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TESTO
		{
			get { return testo; }
			set { testo = value; }
		}

		string num=null;
		[DataField("NUM", Type=DbType.Decimal)]
		public string NUM
		{
			get { return num; }
			set { num = value; }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string ordinetipo=null;
		[DataField("ORDINETIPO", Type=DbType.Decimal)]
		public string ORDINETIPO
		{
			get { return ordinetipo; }
			set { ordinetipo = value; }
		}

	}
}