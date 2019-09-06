using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("LETTERETIPO")]
	public class LettereTipo : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicelettera=null;
		[KeyField("CODICELETTERA", Type=DbType.Decimal)]
		public string CODICELETTERA
		{
			get { return codicelettera; }
			set { codicelettera = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=128, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string oggetto=null;
		[DataField("OGGETTO",Size=512, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OGGETTO
		{
			get { return oggetto; }
			set { oggetto = value; }
		}

		string corpo=null;
		[DataField("CORPO",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CORPO
		{
			get { return corpo; }
			set { corpo = value; }
		}

		string ogg_car_type=null;
		[DataField("OGG_CAR_TYPE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string OGG_CAR_TYPE
		{
			get { return ogg_car_type; }
			set { ogg_car_type = value; }
		}

		string ogg_car_style=null;
		[DataField("OGG_CAR_STYLE",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string OGG_CAR_STYLE
		{
			get { return ogg_car_style; }
			set { ogg_car_style = value; }
		}

		string ogg_car_dim=null;
		[DataField("OGG_CAR_DIM", Type=DbType.Decimal)]
		public string OGG_CAR_DIM
		{
			get { return ogg_car_dim; }
			set { ogg_car_dim = value; }
		}

		string ogg_par_int=null;
		[DataField("OGG_PAR_INT", Type=DbType.Decimal)]
		public string OGG_PAR_INT
		{
			get { return ogg_par_int; }
			set { ogg_par_int = value; }
		}

		string ogg_par_all=null;
		[DataField("OGG_PAR_ALL", Type=DbType.Decimal)]
		public string OGG_PAR_ALL
		{
			get { return ogg_par_all; }
			set { ogg_par_all = value; }
		}

		string cor_car_type=null;
		[DataField("COR_CAR_TYPE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string COR_CAR_TYPE
		{
			get { return cor_car_type; }
			set { cor_car_type = value; }
		}

		string cor_car_style=null;
		[DataField("COR_CAR_STYLE",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string COR_CAR_STYLE
		{
			get { return cor_car_style; }
			set { cor_car_style = value; }
		}

		string cor_car_dim=null;
		[DataField("COR_CAR_DIM", Type=DbType.Decimal)]
		public string COR_CAR_DIM
		{
			get { return cor_car_dim; }
			set { cor_car_dim = value; }
		}

		string cor_par_int=null;
		[DataField("COR_PAR_INT", Type=DbType.Decimal)]
		public string COR_PAR_INT
		{
			get { return cor_par_int; }
			set { cor_par_int = value; }
		}

		string cor_par_all=null;
		[DataField("COR_PAR_ALL", Type=DbType.Decimal)]
		public string COR_PAR_ALL
		{
			get { return cor_par_all; }
			set { cor_par_all = value; }
		}

		string tipolettera=null;
		[DataField("TIPOLETTERA", Type=DbType.Decimal)]
		public string TIPOLETTERA
		{
			get { return tipolettera; }
			set { tipolettera = value; }
		}

		string prefixdest=null;
		[DataField("PREFIXDEST",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PREFIXDEST
		{
			get { return prefixdest; }
			set { prefixdest = value; }
		}

		string pre_car_type=null;
		[DataField("PRE_CAR_TYPE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string PRE_CAR_TYPE
		{
			get { return pre_car_type; }
			set { pre_car_type = value; }
		}

		string pre_car_style=null;
		[DataField("PRE_CAR_STYLE",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string PRE_CAR_STYLE
		{
			get { return pre_car_style; }
			set { pre_car_style = value; }
		}

		string pre_car_dim=null;
		[DataField("PRE_CAR_DIM", Type=DbType.Decimal)]
		public string PRE_CAR_DIM
		{
			get { return pre_car_dim; }
			set { pre_car_dim = value; }
		}

		string pre_par_int=null;
		[DataField("PRE_PAR_INT", Type=DbType.Decimal)]
		public string PRE_PAR_INT
		{
			get { return pre_par_int; }
			set { pre_par_int = value; }
		}

		string pre_par_all=null;
		[DataField("PRE_PAR_ALL", Type=DbType.Decimal)]
		public string PRE_PAR_ALL
		{
			get { return pre_par_all; }
			set { pre_par_all = value; }
		}

		string nomefile=null;
		[DataField("NOMEFILE",Size=125, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMEFILE
		{
			get { return nomefile; }
			set { nomefile = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

	}
}