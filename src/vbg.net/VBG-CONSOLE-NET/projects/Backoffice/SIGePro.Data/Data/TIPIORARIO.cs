using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIORARIO")]
	[Serializable]
	public class TipiOrario : BaseDataClass
	{

		#region Key Fields

		string to_id=null;
		[useSequence]
		[KeyField("TO_ID", Type=DbType.Decimal)]
		public string TO_ID
		{
			get { return to_id; }
			set { to_id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string to_descrizione=null;
		[DataField("TO_DESCRIZIONE",Size=80, Type=DbType.String, CaseSensitive=false)]
		public string TO_DESCRIZIONE
		{
			get { return to_descrizione; }
			set { to_descrizione = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string to_periododa=null;
		[DataField("TO_PERIODODA",Size=4, Type=DbType.String)]
		public string TO_PERIODODA
		{
			get { return to_periododa; }
			set { to_periododa = value; }
		}

		string to_periodoa=null;
		[DataField("TO_PERIODOA",Size=4, Type=DbType.String)]
		public string TO_PERIODOA
		{
			get { return to_periodoa; }
			set { to_periodoa = value; }
		}

	}
}