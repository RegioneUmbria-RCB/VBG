using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_SELEZIONESTAMPA")]
	public class Tmp_SelezioneStampa : BaseDataClass
	{
		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string idcomune=null;
		[DataField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[DataField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string campo=null;
		[DataField("CAMPO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CAMPO
		{
			get { return campo; }
			set { campo = value; }
		}

		string indice=null;
		[DataField("INDICE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INDICE
		{
			get { return indice; }
			set { indice = value; }
		}

		string etichetta=null;
		[DataField("ETICHETTA",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ETICHETTA
		{
			get { return etichetta; }
			set { etichetta = value; }
		}

		string valore=null;
		[DataField("VALORE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string VALORE
		{
			get { return valore; }
			set { valore = value; }
		}

	}
}