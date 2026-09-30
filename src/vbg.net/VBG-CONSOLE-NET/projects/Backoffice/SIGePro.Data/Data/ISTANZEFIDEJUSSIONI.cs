using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEFIDEJUSSIONI")]
	[Serializable]
	public class IstanzeFidejussioni : BaseDataClass
	{
		
		#region Key Fields

		string codiceistanza=null;
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string idfidejussione=null;
		[KeyField("IDFIDEJUSSIONE", Type=DbType.Decimal, KeyIdentity=true)]
		public string IDFIDEJUSSIONE
		{
			get { return idfidejussione; }
			set { idfidejussione = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

        double? importo = null;
		[isRequired]
		[DataField("IMPORTO", Type=DbType.Decimal)]
		public double? IMPORTO
		{
			get { return importo; }
			set { importo = value; }
		}

		string fk_rco_id=null;
		[DataField("FK_RCO_ID", Type=DbType.Int64)]
		public string FK_RCO_ID
		{
			get { return fk_rco_id; }
			set { fk_rco_id = value; }
		}

		string fk_codicestato=null;
		[DataField("FK_CODICESTATO", Type=DbType.Int64)]
		public string FK_CODICESTATO
		{
			get { return fk_codicestato; }
			set { fk_codicestato = value; }
		}

        DateTime? datasvincolo = null;
		[DataField("DATASVINCOLO", Type=DbType.DateTime)]
		public DateTime? DATASVINCOLO
		{
			get { return datasvincolo; }
            set { datasvincolo = VerificaDataLocale(value); }
		}


		string note=null;
		[DataField("NOTE",Size=250, Type=DbType.String, CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}
	}
}