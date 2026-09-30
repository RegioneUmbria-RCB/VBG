using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_STAT_03")]
	public class Tmp_Stat_03 : BaseDataClass
	{
		string sessionid=null;
		[KeyField("SESSIONID",Size=60, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string idpratica=null;
		[KeyField("IDPRATICA", Type=DbType.Decimal)]
		public string IDPRATICA
		{
			get { return idpratica; }
			set { idpratica = value; }
		}

		string attiva=null;
		[DataField("ATTIVA", Type=DbType.Decimal)]
		public string ATTIVA
		{
			get { return attiva; }
			set { attiva = value; }
		}

		string operante=null;
		[DataField("OPERANTE", Type=DbType.Decimal)]
		public string OPERANTE
		{
			get { return operante; }
			set { operante = value; }
		}

	}
}