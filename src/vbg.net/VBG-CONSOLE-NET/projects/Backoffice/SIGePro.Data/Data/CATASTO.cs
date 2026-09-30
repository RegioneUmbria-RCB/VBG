using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("CATASTO")]
	[Serializable]
	public class Catasto : BaseDataClass
	{
		
		#region Key Fields

		string codice=null;
		[KeyField("CODICE", Size=1, Type=DbType.String )]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		#endregion

		string descrizione=null;
		[isRequired]
		[DataField("DESCRIZIONE",Size=30, Type=DbType.String, CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}
	}
}