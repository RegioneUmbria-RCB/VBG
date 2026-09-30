using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("LEGGI")]
	public class Leggi : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string le_id=null;
		[KeyField("LE_ID", Type=DbType.Decimal)]
		public string LE_ID
		{
			get { return le_id; }
			set { le_id = value; }
		}

		string le_fkltid=null;
		[DataField("LE_FKLTID", Type=DbType.Decimal)]
		public string LE_FKLTID
		{
			get { return le_fkltid; }
			set { le_fkltid = value; }
		}

		string le_descrizione=null;
		[DataField("LE_DESCRIZIONE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LE_DESCRIZIONE
		{
			get { return le_descrizione; }
			set { le_descrizione = value; }
		}

		string le_allegato=null;
		[DataField("LE_ALLEGATO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LE_ALLEGATO
		{
			get { return le_allegato; }
			set { le_allegato = value; }
		}

		string le_link=null;
		[DataField("LE_LINK",Size=200, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LE_LINK
		{
			get { return le_link; }
			set { le_link = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}
	}
}