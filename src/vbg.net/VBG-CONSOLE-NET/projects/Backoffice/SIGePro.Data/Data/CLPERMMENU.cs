using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("CLPERMMENU")]
	public class ClPermMenu : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string fkidmenu=null;
		[DataField("FKIDMENU", Type=DbType.Decimal)]
		public string FKIDMENU
		{
			get { return fkidmenu; }
			set { fkidmenu = value; }
		}

		string codiceresponsabile=null;
		[DataField("CODICERESPONSABILE", Type=DbType.Decimal)]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
		}

        string software = null;
        [DataField("SOFTWARE", Size = 2, Type = DbType.String)]
        public string SOFTWARE
        {
            get { return software; }
            set { software = value; }
        }
	}
}