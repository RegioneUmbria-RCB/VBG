using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ALBEROPROC_ONERI")]
	[Serializable]
	public class AlberoProcOneri : BaseDataClass
	{
		
		#region Key Fields

		string idcomune=null;
		[useSequence]
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string ao_id=null;
		[KeyField("AO_ID", Type=DbType.Decimal)]
		public string AO_ID
		{
			get { return ao_id; }
			set { ao_id = value; }
		}

		#endregion

		string ao_fk_scid=null;
		[DataField("AO_FK_SCID", Type=DbType.Decimal)]
		public string AO_FK_SCID
		{
			get { return ao_fk_scid; }
			set { ao_fk_scid = value; }
		}

		string ao_fk_coid=null;
		[DataField("AO_FK_COID", Type=DbType.Decimal)]
		public string AO_FK_COID
		{
			get { return ao_fk_coid; }
			set { ao_fk_coid = value; }
		}

        double? ao_importocausale = null;
		[DataField("AO_IMPORTOCAUSALE", Type=DbType.Decimal)]
        public double? AO_IMPORTOCAUSALE
		{
			get { return ao_importocausale; }
			set { ao_importocausale = value; }
		}

		double? ao_importoistruttoria=null;
		[DataField("AO_IMPORTOISTRUTTORIA", Type=DbType.Decimal)]
		public double? AO_IMPORTOISTRUTTORIA
		{
			get { return ao_importoistruttoria; }
			set { ao_importoistruttoria = value; }
		}

		string ao_serichiesto=null;
		[DataField("AO_SERICHIESTO", Type=DbType.Decimal)]
		public string AO_SERICHIESTO
		{
			get { return ao_serichiesto; }
			set { ao_serichiesto = value; }
		}


	}
}