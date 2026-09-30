using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ALBEROPROC_LIMITI")]
	[Serializable]
	public class AlberoProcLimiti : BaseDataClass
	{
	
		#region Key Fields

		string fkidalbero=null;
		[KeyField("FKIDALBERO", Type=DbType.Decimal)]
		public string FKIDALBERO
		{
			get { return fkidalbero; }
			set { fkidalbero = value; }
		}

		string fkidzona=null;
		[KeyField("FKIDZONA", Type=DbType.Decimal)]
		public string FKIDZONA
		{
			get { return fkidzona; }
			set { fkidzona = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string nrmaxistanze=null;
		[DataField("NRMAXISTANZE", Type=DbType.Decimal)]
		public string NRMAXISTANZE
		{
			get { return nrmaxistanze; }
			set { nrmaxistanze = value; }
		}

		string mqmaxistanze=null;
		[DataField("MQMAXISTANZE", Type=DbType.Decimal)]
		public string MQMAXISTANZE
		{
			get { return mqmaxistanze; }
			set { mqmaxistanze = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

	}
}