using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DYN_DATI")]
	[Serializable]
	public class DynDati : BaseDataClass
	{
		
		#region Key Fields

		string codicerecordcorrelato=null;
		[KeyField("CODICERECORDCORRELATO", Type=DbType.Decimal)]
		public string CODICERECORDCORRELATO
		{
			get { return codicerecordcorrelato; }
			set { codicerecordcorrelato = value; }
		}

		string codicecampo=null;
		[KeyField("CODICECAMPO", Type=DbType.Decimal)]
		public string CODICECAMPO
		{
			get { return codicecampo; }
			set { codicecampo = value; }
		}

		string fkidtestata=null;
		[KeyField("FKIDTESTATA", Type=DbType.Decimal)]
		public string FKIDTESTATA
		{
			get { return fkidtestata; }
			set { fkidtestata = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string valore=null;
		[DataField("VALORE",Size=250, Type=DbType.String, CaseSensitive=false)]
		public string VALORE
		{
			get { return valore; }
			set { valore = value; }
		}

		#region Arraylist per gli inserimenti nelle tabelle collegate
		
		Oggetti _Oggetto = new Oggetti();
		public Oggetti Oggetto
		{
			get { return _Oggetto; }
			set { _Oggetto = value; }
		}
		
		#endregion
	}
}