using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_LT_CORPO")]
	public class Tmp_Lt_Corpo : BaseDataClass
	{
		string sesis=null;
		[DataField("SESIS",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESIS
		{
			get { return sesis; }
			set { sesis = value; }
		}

		string car_type=null;
		[DataField("CAR_TYPE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string CAR_TYPE
		{
			get { return car_type; }
			set { car_type = value; }
		}

		string car_style=null;
		[DataField("CAR_STYLE", Type=DbType.Decimal)]
		public string CAR_STYLE
		{
			get { return car_style; }
			set { car_style = value; }
		}

		string car_dim=null;
		[DataField("CAR_DIM", Type=DbType.Decimal)]
		public string CAR_DIM
		{
			get { return car_dim; }
			set { car_dim = value; }
		}

		string par_int=null;
		[DataField("PAR_INT", Type=DbType.Decimal)]
		public string PAR_INT
		{
			get { return par_int; }
			set { par_int = value; }
		}

		string par_all=null;
		[DataField("PAR_ALL", Type=DbType.Decimal)]
		public string PAR_ALL
		{
			get { return par_all; }
			set { par_all = value; }
		}

		string rowtext=null;
		[DataField("ROWTEXT",Size=2147483647, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ROWTEXT
		{
			get { return rowtext; }
			set { rowtext = value; }
		}

		string rowprog=null;
		[DataField("ROWPROG", Type=DbType.Decimal)]
		public string ROWPROG
		{
			get { return rowprog; }
			set { rowprog = value; }
		}

	}
}