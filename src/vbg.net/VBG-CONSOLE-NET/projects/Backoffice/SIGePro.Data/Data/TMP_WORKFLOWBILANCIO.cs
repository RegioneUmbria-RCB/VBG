using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_WORKFLOWBILANCIO")]
	public class Tmp_WorkFlowBilancio : BaseDataClass
	{
		string amministrazione=null;
		[DataField("AMMINISTRAZIONE",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string AMMINISTRAZIONE
		{
			get { return amministrazione; }
			set { amministrazione = value; }
		}

		string nonrisp=null;
		[DataField("NONRISP", Type=DbType.Decimal)]
		public string NONRISP
		{
			get { return nonrisp; }
			set { nonrisp = value; }
		}

		string risp=null;
		[DataField("RISP", Type=DbType.Decimal)]
		public string RISP
		{
			get { return risp; }
			set { risp = value; }
		}

		string temporispgg=null;
		[DataField("TEMPORISPGG", Type=DbType.Decimal)]
		public string TEMPORISPGG
		{
			get { return temporispgg; }
			set { temporispgg = value; }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

        DateTime? datastampa = null;
		[DataField("DATASTAMPA", Type=DbType.DateTime)]
		public DateTime? DATASTAMPA
		{
			get { return datastampa; }
            set { datastampa = VerificaDataLocale(value); }
		}

		string procedimento=null;
		[DataField("PROCEDIMENTO",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PROCEDIMENTO
		{
			get { return procedimento; }
			set { procedimento = value; }
		}

	}
}