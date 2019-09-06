using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ORARIAPERTURATESTATA")]
	[Serializable]
	public class OrariAperturaTestata : BaseDataClass
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

		string periododa=null;
		[DataField("PERIODODA",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string PERIODODA
		{
			get { return periododa; }
			set { periododa = value; }
		}

		string periodoa=null;
		[DataField("PERIODOA",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string PERIODOA
		{
			get { return periodoa; }
			set { periodoa = value; }
		}

		string codiceistanza=null;
		[isRequired]
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string fktoid=null;
		[isRequired]
		[DataField("FKTOID", Type=DbType.Decimal)]
		public string FKTOID
		{
			get { return fktoid; }
			set { fktoid = value; }
		}

		#region Arraylist per gli inserimenti nelle tabelle collegate

		OrariAperturaCollection _OrariApertura = new OrariAperturaCollection();
		public OrariAperturaCollection OrariApertura
		{
			get { return _OrariApertura; }
			set { _OrariApertura = value; }
		}
		
		#endregion

	}
}