using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("SIT_CARTECH")]
	public class Sit_Cartech : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceistanza=null;
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string edificio=null;
		[DataField("EDIFICIO", Type=DbType.Decimal)]
		public string EDIFICIO
		{
			get { return edificio; }
			set { edificio = value; }
		}

		string poligono=null;
		[DataField("POLIGONO",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string POLIGONO
		{
			get { return poligono; }
			set { poligono = value; }
		}

		string punto=null;
		[DataField("PUNTO",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string PUNTO
		{
			get { return punto; }
			set { punto = value; }
		}

	}
}