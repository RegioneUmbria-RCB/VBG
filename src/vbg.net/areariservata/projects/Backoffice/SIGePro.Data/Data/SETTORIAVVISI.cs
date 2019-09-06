using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("SETTORIAVVISI")]
	[Serializable]
	public class SettoriAvvisi : BaseDataClass
	{
		
		#region Key Fields

		string idavviso=null;
		[KeyField("IDAVVISO", Type=DbType.Int32)]
		public string IDAVVISO
		{
			get { return idavviso; }
			set { idavviso = value; }
		}

		string codicesettore=null;
		[KeyField("CODICESETTORE",Size=6, Type=DbType.String)]
		public string CODICESETTORE
		{
			get { return codicesettore; }
			set { codicesettore = value; }
		}
		
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string software=null;
		[isRequired]
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string codiceoggetto=null;
		[isRequired]
		[DataField("CODICEOGGETTO",Type=DbType.Int16)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string rangeda=null;
		[DataField("RANGEDA",Type=DbType.Int16)]
		public string RANGEDA
		{
			get { return rangeda; }
			set { rangeda = value; }
		}

		string rangea=null;
		[DataField("RANGEA",Type=DbType.Int16)]
		public string RANGEA
		{
			get { return rangea; }
			set { rangea = value; }
		}

		
		#region Arraylist per gli inserimenti nelle tabelle collegate
		
		Oggetti _Oggetto = null;
		public Oggetti Oggetto
		{
			get { return _Oggetto; }
			set { _Oggetto = value; }
		}

		#endregion
	}
}