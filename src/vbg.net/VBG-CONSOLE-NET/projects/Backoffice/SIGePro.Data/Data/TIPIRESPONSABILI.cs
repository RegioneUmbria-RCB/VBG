using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIRESPONSABILI")]
	public class TipiResponsabili : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string tr_id=null;
		[KeyField("TR_ID", Type=DbType.Decimal)]
		public string TR_ID
		{
			get { return tr_id; }
			set { tr_id = value; }
		}

		string tr_descrizione=null;
		[DataField("TR_DESCRIZIONE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TR_DESCRIZIONE
		{
			get { return tr_descrizione; }
			set { tr_descrizione = value; }
		}

		string tr_flagresponsabile=null;
		[DataField("TR_FLAGRESPONSABILE", Type=DbType.Decimal)]
		public string TR_FLAGRESPONSABILE
		{
			get { return tr_flagresponsabile; }
			set { tr_flagresponsabile = value; }
		}

		string tr_flagistruttore=null;
		[DataField("TR_FLAGISTRUTTORE", Type=DbType.Decimal)]
		public string TR_FLAGISTRUTTORE
		{
			get { return tr_flagistruttore; }
			set { tr_flagistruttore = value; }
		}
	}
}