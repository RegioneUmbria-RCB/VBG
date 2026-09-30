using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("CATEGORIAAFFISSIONI")]
	public class CategoriaAffisioni : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicecategoria=null;
		[KeyField("CODICECATEGORIA", Type=DbType.Decimal)]
		public string CODICECATEGORIA
		{
			get { return codicecategoria; }
			set { codicecategoria = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string categoria=null;
		[DataField("CATEGORIA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CATEGORIA
		{
			get { return categoria; }
			set { categoria = value; }
		}

	}
}