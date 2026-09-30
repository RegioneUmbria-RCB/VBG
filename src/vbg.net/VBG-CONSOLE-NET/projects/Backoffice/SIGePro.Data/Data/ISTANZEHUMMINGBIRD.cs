using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEHUMMINGBIRD")]
	[Serializable]
	public class IstanzeHummingbird : BaseDataClass
	{
		
		public IstanzeHummingbird()
		{
		}

		#region KeyFields
		string idcomune=null;
		[isRequired]
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}
		
		
		string codiceistanza=null;
		[isRequired]
		[KeyField("CODICEISTANZA", Type=DbType.Decimal )]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}
		#endregion

		#region Campi database

		string id_pratica = null;
		[isRequired]
		[DataField("ID_PRATICA" , Size=50 , Type=DbType.String , CaseSensitive=false ) ]
		public string ID_PRATICA
		{
			get { return id_pratica; }
			set { id_pratica = value; }
		}

		string docnum = null;
		[isRequired]
		[DataField("DOCNUM" , Size=6 , Type=DbType.Decimal , CaseSensitive=false ) ]
		public string DOCNUM
		{
			get { return docnum; }
			set { docnum = value; }
		}

		#endregion
	}
}
