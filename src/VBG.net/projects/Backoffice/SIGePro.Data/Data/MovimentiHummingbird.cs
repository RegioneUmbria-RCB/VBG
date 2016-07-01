using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("MOVIMENTIHUMMINGBIRD")]
	[Serializable]
	public class MovimentiHummingbird : BaseDataClass
	{
		
		public MovimentiHummingbird()
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
		
		string codiceMovimento=null;
		[isRequired]
		[KeyField("CODICEMOVIMENTO", Type=DbType.Decimal )]
		public string CODICEMOVIMENTO
		{
			get { return codiceMovimento; }
			set { codiceMovimento = value; }
		}

		#endregion

		#region Campi database
		string codiceistanza=null;
		[isRequired]
		[DataField("CODICEISTANZA", Type=DbType.Decimal )]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
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