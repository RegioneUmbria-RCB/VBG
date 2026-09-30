using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("GRADUATORIECRITERI")]
	public class GraduatorieCriteri : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string tabelle=null;
		[DataField("TABELLE",Size=500, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TABELLE
		{
			get { return tabelle; }
			set { tabelle = value; }
		}

		string istanzejoin=null;
		[DataField("ISTANZEJOIN",Size=500, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ISTANZEJOIN
		{
			get { return istanzejoin; }
			set { istanzejoin = value; }
		}

		string orderby=null;
		[DataField("ORDERBY",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ORDERBY
		{
			get { return orderby; }
			set { orderby = value; }
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