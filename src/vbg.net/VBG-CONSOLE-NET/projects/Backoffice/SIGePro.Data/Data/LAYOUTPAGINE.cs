using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("LAYOUTPAGINE")]
	public class LayoutPagine : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string software=null;
		[KeyField("SOFTWARE",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string lp_oggetto=null;
		[KeyField("LP_OGGETTO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LP_OGGETTO
		{
			get { return lp_oggetto; }
			set { lp_oggetto = value; }
		}

		string lp_pagina=null;
		[KeyField("LP_PAGINA",Size=200, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LP_PAGINA
		{
			get { return lp_pagina; }
			set { lp_pagina = value; }
		}

		string lp_disabilita=null;
		[DataField("LP_DISABILITA",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string LP_DISABILITA
		{
			get { return lp_disabilita; }
			set { lp_disabilita = value; }
		}

	}
}