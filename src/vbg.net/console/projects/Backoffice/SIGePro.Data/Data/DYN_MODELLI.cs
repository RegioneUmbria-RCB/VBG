using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DYN_MODELLI")]
	[Serializable]
	public class DynModelli : BaseDataClass
	{

		#region Key Fields

		string codice=null;
		[useSequence]
		[KeyField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string tipo=null;
		[DataField("TIPO",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string TIPO
		{
			get { return tipo; }
			set { tipo = value; }
		}

		string etichetta=null;
		[DataField("ETICHETTA",Size=150, Type=DbType.String, CaseSensitive=false)]
		public string ETICHETTA
		{
			get { return etichetta; }
			set { etichetta = value; }
		}

		string fkidcodice1=null;
		[DataField("FKIDCODICE1", Type=DbType.Decimal)]
		public string FKIDCODICE1
		{
			get { return fkidcodice1; }
			set { fkidcodice1 = value; }
		}

		string fkidcodice2=null;
		[DataField("FKIDCODICE2", Type=DbType.Decimal)]
		public string FKIDCODICE2
		{
			get { return fkidcodice2; }
			set { fkidcodice2 = value; }
		}

		string fkidcodice3=null;
		[DataField("FKIDCODICE3", Type=DbType.Decimal)]
		public string FKIDCODICE3
		{
			get { return fkidcodice3; }
			set { fkidcodice3 = value; }
		}

		string fkidtestata=null;
		[DataField("FKIDTESTATA", Type=DbType.Decimal)]
		public string FKIDTESTATA
		{
			get { return fkidtestata; }
			set { fkidtestata = value; }
		}

		string testoesteso=null;
		[DataField("TESTOESTESO",Size=4000, Type=DbType.String, CaseSensitive=false)]
		public string TESTOESTESO
		{
			get { return testoesteso; }
			set { testoesteso = value; }
		}

		string ordine=null;
		[DataField("ORDINE", Type=DbType.Decimal)]
		public string ORDINE
		{
			get { return ordine; }
			set { ordine = value; }
		}

	}
}