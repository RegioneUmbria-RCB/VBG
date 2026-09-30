using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("CLMENU")]
	public class ClMenu : BaseDataClass
	{
		string id=null;
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

		string pagina=null;
		[DataField("PAGINA",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PAGINA
		{
			get { return pagina; }
			set { pagina = value; }
		}

		string menulink=null;
		[DataField("MENULINK",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string MENULINK
		{
			get { return menulink; }
			set { menulink = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String,Compare="=" )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string jsp=null;
		[DataField("JSP",Size=63, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string JSP
		{
			get { return jsp; }
			set { jsp = value; }
		}

		string layouttesti=null;
		[DataField("LAYOUTTESTI", Type=DbType.Decimal)]
		public string LAYOUTTESTI
		{
			get { return layouttesti; }
			set { layouttesti = value; }
		}

		string verticalizzazione=null;
		[DataField("VERTICALIZZAZIONE",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string VERTICALIZZAZIONE
		{
			get { return verticalizzazione; }
			set { verticalizzazione = value; }
		}

	}
}