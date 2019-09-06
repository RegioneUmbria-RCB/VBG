using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIARCHIVIOISTANZE")]
	[Serializable]
	public class TipiArchivioIstanze : BaseDataClass
	{

		#region Key Fields

		string codicearchivio=null;
		[useSequence]
		[KeyField("CODICEARCHIVIO", Type=DbType.Decimal)]
		public string CODICEARCHIVIO
		{
			get { return codicearchivio; }
			set { codicearchivio = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string archivio=null;
		[DataField("ARCHIVIO",Size=80, Type=DbType.String, CaseSensitive=false)]
		public string ARCHIVIO
		{
			get { return archivio; }
			set { archivio = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

	}
}