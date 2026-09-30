using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_CATEGORIE")]
	public class F_Categorie : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string ct_id=null;
		[KeyField("CT_ID", Type=DbType.Decimal)]
		public string CT_ID
		{
			get { return ct_id; }
			set { ct_id = value; }
		}

		string ct_descrizione=null;
		[DataField("CT_DESCRIZIONE",Size=200, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CT_DESCRIZIONE
		{
			get { return ct_descrizione; }
			set { ct_descrizione = value; }
		}

		string ct_modello=null;
		[DataField("CT_MODELLO", Type=DbType.Decimal)]
		public string CT_MODELLO
		{
			get { return ct_modello; }
			set { ct_modello = value; }
		}
	}
}