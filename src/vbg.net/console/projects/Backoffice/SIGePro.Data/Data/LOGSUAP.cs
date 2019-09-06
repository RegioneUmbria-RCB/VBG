using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("LOGSUAP")]
	[Serializable]
	public class LogSuap : BaseDataClass
	{
		#region Key Fields

		string id=null;
		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string data=null;
		[DataField("DATA",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string DATA
		{
			get { return data; }
			set { data = value; }
		}

		string ora=null;
		[DataField("ORA",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string ORA
		{
			get { return ora; }
			set { ora = value; }
		}

		string ip=null;
		[DataField("IP",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string IP
		{
			get { return ip; }
			set { ip = value; }
		}

		string operatore=null;
		[DataField("OPERATORE",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OPERATORE
		{
			get { return operatore; }
			set { operatore = value; }
		}

		string messaggio=null;
		[DataField("MESSAGGIO",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MESSAGGIO
		{
			get { return messaggio; }
			set { messaggio = value; }
		}

		string codicerichiesta=null;
		[DataField("CODICERICHIESTA",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string CODICERICHIESTA
		{
			get { return codicerichiesta; }
			set { codicerichiesta = value; }
		}

		string codiceerrore=null;
		[DataField("CODICEERRORE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string CODICEERRORE
		{
			get { return codiceerrore; }
			set { codiceerrore = value; }
		}
	}
}