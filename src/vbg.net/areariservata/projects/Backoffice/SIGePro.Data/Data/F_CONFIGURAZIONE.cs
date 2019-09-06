using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_CONFIGURAZIONE")]
	public class F_Configurazione : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string fc_fieradefault=null;
		[DataField("FC_FIERADEFAULT", Type=DbType.Decimal)]
		public string FC_FIERADEFAULT
		{
			get { return fc_fieradefault; }
			set { fc_fieradefault = value; }
		}

		string fc_listadyndati=null;
		[DataField("FC_LISTADYNDATI",Size=2000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FC_LISTADYNDATI
		{
			get { return fc_listadyndati; }
			set { fc_listadyndati = value; }
		}

		string fc_campo_autnum=null;
		[DataField("FC_CAMPO_AUTNUM", Type=DbType.Decimal)]
		public string FC_CAMPO_AUTNUM
		{
			get { return fc_campo_autnum; }
			set { fc_campo_autnum = value; }
		}

		string fc_campo_autcom=null;
		[DataField("FC_CAMPO_AUTCOM", Type=DbType.Decimal)]
		public string FC_CAMPO_AUTCOM
		{
			get { return fc_campo_autcom; }
			set { fc_campo_autcom = value; }
		}

		string fc_campo_autdata=null;
		[DataField("FC_CAMPO_AUTDATA", Type=DbType.Decimal)]
		public string FC_CAMPO_AUTDATA
		{
			get { return fc_campo_autdata; }
			set { fc_campo_autdata = value; }
		}

		string fc_campo_presenze=null;
		[DataField("FC_CAMPO_PRESENZE", Type=DbType.Decimal)]
		public string FC_CAMPO_PRESENZE
		{
			get { return fc_campo_presenze; }
			set { fc_campo_presenze = value; }
		}

	}
}