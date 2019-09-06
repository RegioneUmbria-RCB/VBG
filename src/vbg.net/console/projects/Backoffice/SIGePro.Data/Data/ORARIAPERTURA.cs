using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ORARIAPERTURA")]
	[Serializable]
	public class OrariApertura : BaseDataClass
	{

		#region Key Fields

		string oa_id=null;
		[useSequence]
		[KeyField("OA_ID", Type=DbType.Decimal)]
		public string OA_ID
		{
			get { return oa_id; }
			set { oa_id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string oa_fkidorario=null;
		[DataField("OA_FKIDORARIO", Type=DbType.Decimal)]
		public string OA_FKIDORARIO
		{
			get { return oa_fkidorario; }
			set { oa_fkidorario = value; }
		}

		string oa_fkidgiorno=null;
		[isRequired]
		[DataField("OA_FKIDGIORNO", Type=DbType.Decimal)]
		public string OA_FKIDGIORNO
		{
			get { return oa_fkidgiorno; }
			set { oa_fkidgiorno = value; }
		}

		string oa_dalleore=null;
		[DataField("OA_DALLEORE",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string OA_DALLEORE
		{
			get { return oa_dalleore; }
			set { oa_dalleore = value; }
		}

		string oa_alleore=null;
		[DataField("OA_ALLEORE",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string OA_ALLEORE
		{
			get { return oa_alleore; }
			set { oa_alleore = value; }
		}

		string oa_dalleorepom=null;
		[DataField("OA_DALLEOREPOM",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string OA_DALLEOREPOM
		{
			get { return oa_dalleorepom; }
			set { oa_dalleorepom = value; }
		}

		string oa_alleorepom=null;
		[DataField("OA_ALLEOREPOM",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string OA_ALLEOREPOM
		{
			get { return oa_alleorepom; }
			set { oa_alleorepom = value; }
		}

		string oa_datainizio=null;
		[DataField("OA_DATAINIZIO",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string OA_DATAINIZIO
		{
			get { return oa_datainizio; }
			set { oa_datainizio = value; }
		}

		string oa_datafine=null;
		[DataField("OA_DATAFINE",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string OA_DATAFINE
		{
			get { return oa_datafine; }
			set { oa_datafine = value; }
		}

		string oa_fkidistanza=null;
		[DataField("OA_FKIDISTANZA", Type=DbType.Decimal)]
		public string OA_FKIDISTANZA
		{
			get { return oa_fkidistanza; }
			set { oa_fkidistanza = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string oa_fkidtestata=null;
		[isRequired]
		[DataField("OA_FKIDTESTATA", Type=DbType.Decimal)]
		public string OA_FKIDTESTATA
		{
			get { return oa_fkidtestata; }
			set { oa_fkidtestata = value; }
		}

	}
}