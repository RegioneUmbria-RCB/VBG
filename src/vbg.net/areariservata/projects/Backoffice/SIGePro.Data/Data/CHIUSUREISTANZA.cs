using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("CHIUSUREISTANZA")]
	[Serializable]
	public class ChiusureIstanza : BaseDataClass
	{
		
		#region Key Fields

		string codiceistanza=null;
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string istanza=null;
		[DataField("ISTANZA", Type=DbType.Decimal)]
		public string ISTANZA
		{
			get { return istanza; }
			set { istanza = value; }
		}

		string endo=null;
		[DataField("ENDO", Type=DbType.Decimal)]
		public string ENDO
		{
			get { return endo; }
			set { endo = value; }
		}

		string analqual=null;
		[DataField("ANALQUAL", Type=DbType.Decimal)]
		public string ANALQUAL
		{
			get { return analqual; }
			set { analqual = value; }
		}

		string cds=null;
		[DataField("CDS", Type=DbType.Decimal)]
		public string CDS
		{
			get { return cds; }
			set { cds = value; }
		}



	}
}