using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("BATCH_SCADENZARIO")]
	public class Batch_Scadenzario : BaseDataClass
	{
		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

        DateTime? dataelaborazione = null;
		[DataField("DATAELABORAZIONE", Type=DbType.DateTime)]
		public DateTime? DATAELABORAZIONE
		{
			get { return dataelaborazione; }
            set { dataelaborazione = VerificaDataLocale(value); }
		}

		string idcomune=null;
		[DataField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string codiceistanza=null;
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string codicemovimento=null;
		[DataField("CODICEMOVIMENTO", Type=DbType.Decimal)]
		public string CODICEMOVIMENTO
		{
			get { return codicemovimento; }
			set { codicemovimento = value; }
		}

		string descrmovimento=null;
		[DataField("DESCRMOVIMENTO",Size=128, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRMOVIMENTO
		{
			get { return descrmovimento; }
			set { descrmovimento = value; }
		}

		string tipomovimentodafare=null;
		[DataField("TIPOMOVIMENTODAFARE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOMOVIMENTODAFARE
		{
			get { return tipomovimentodafare; }
			set { tipomovimentodafare = value; }
		}

		string descrmovimentodafare=null;
		[DataField("DESCRMOVIMENTODAFARE",Size=128, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRMOVIMENTODAFARE
		{
			get { return descrmovimentodafare; }
			set { descrmovimentodafare = value; }
		}

		string codiceinventario=null;
		[DataField("CODICEINVENTARIO", Type=DbType.Decimal)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string codiceamministrazione=null;
		[DataField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

        DateTime? datascadenza = null;
		[DataField("DATASCADENZA", Type=DbType.DateTime)]
		public DateTime? DATASCADENZA
		{
			get { return datascadenza; }
            set { datascadenza = VerificaDataLocale(value); }
		}

	}
}