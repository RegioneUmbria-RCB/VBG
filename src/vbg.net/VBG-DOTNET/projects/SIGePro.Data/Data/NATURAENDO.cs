using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("NATURAENDO")]
	public class NaturaEndo : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicenatura=null;
		[KeyField("CODICENATURA", Type=DbType.Decimal)]
		public string CODICENATURA
		{
			get { return codicenatura; }
			set { codicenatura = value; }
		}

		string natura=null;
		[DataField("NATURA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NATURA
		{
			get { return natura; }
			set { natura = value; }
		}

		string binariodipendenze=null;
		[DataField("BINARIODIPENDENZE", Type=DbType.Decimal)]
		public string BINARIODIPENDENZE
		{
			get { return binariodipendenze; }
			set { binariodipendenze = value; }
		}

		string noneseguecontromovobblig=null;
		[DataField("NONESEGUECONTROMOVOBBLIG", Type=DbType.Decimal)]
		public string NONESEGUECONTROMOVOBBLIG
		{
			get { return noneseguecontromovobblig; }
			set { noneseguecontromovobblig = value; }
		}
	}
}