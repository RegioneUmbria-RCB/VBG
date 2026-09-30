using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIORARIODETTAGLIO")]
	[Serializable]
	public class TipiOrarioDettaglio : BaseDataClass
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
		[DataField("OA_FKIDORARIO", Type=DbType.Int32)]
		public string OA_FKIDORARIO
		{
			get { return oa_fkidorario; }
			set { oa_fkidorario = value; }
		}

		string oa_fkidgiorno=null;
		[DataField("OA_FKIDGIORNO", Type=DbType.Int32)]
		public string OA_FKIDGIORNO
		{
			get { return oa_fkidgiorno; }
			set { oa_fkidgiorno = value; }
		}

		string oa_dalleore=null;
		[DataField("OA_DALLEORE",Size=5, Type=DbType.String)]
		public string OA_DALLEORE
		{
			get { return oa_dalleore; }
			set { oa_dalleore = value; }
		}

		string oa_alleore=null;
		[DataField("OA_ALLEORE",Size=5, Type=DbType.String)]
		public string OA_ALLEORE
		{
			get { return oa_alleore; }
			set { oa_alleore = value; }
		}

		string oa_dalleorepom=null;
		[DataField("OA_DALLEOREPOM",Size=5, Type=DbType.String)]
		public string OA_DALLEOREPOM
		{
			get { return oa_dalleorepom; }
			set { oa_dalleorepom = value; }
		}

		string oa_alleorepom=null;
		[DataField("OA_ALLEOREPOM",Size=5, Type=DbType.String)]
		public string OA_ALLEOREPOM
		{
			get { return oa_alleorepom; }
			set { oa_alleorepom = value; }
		}

		string oa_datainizio=null;
		[DataField("OA_DATAINIZIO",Size=8, Type=DbType.String )]
		public string OA_DATAINIZIO
		{
			get { return oa_datainizio; }
			set { oa_datainizio = value; }
		}

		string oa_datafine=null;
		[DataField("OA_DATAFINE",Size=8, Type=DbType.String )]
		public string OA_DATAFINE
		{
			get { return oa_datafine; }
			set { oa_datafine = value; }
		}

		string oa_fkidtiporario=null;
		[DataField("OA_FKIDTIPORARIO", Type=DbType.Int32)]
		public string OA_FKIDTIPORARIO
		{
			get { return oa_fkidtiporario; }
			set { oa_fkidtiporario = value; }
		}

	}
}