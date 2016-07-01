using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("BATCH_SCAD_ISTANZE")]
	public class Batch_Scad_Istanze : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceistanza=null;
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string durataistanza=null;
		[DataField("DURATAISTANZA", Type=DbType.Decimal)]
		public string DURATAISTANZA
		{
			get { return durataistanza; }
			set { durataistanza = value; }
		}

        DateTime? datainiziosospensione = null;
		[DataField("DATAINIZIOSOSPENSIONE", Type=DbType.DateTime)]
		public DateTime? DATAINIZIOSOSPENSIONE
		{
			get { return datainiziosospensione; }
            set { datainiziosospensione = VerificaDataLocale(value); }
		}

        DateTime? datafinesospensione = null;
		[DataField("DATAFINESOSPENSIONE", Type=DbType.DateTime)]
		public DateTime? DATAFINESOSPENSIONE
		{
			get { return datafinesospensione; }
            set { datafinesospensione = VerificaDataLocale(value); }
		}

        DateTime? datainiziointerruzione = null;
		[DataField("DATAINIZIOINTERRUZIONE", Type=DbType.DateTime)]
		public DateTime? DATAINIZIOINTERRUZIONE
		{
			get { return datainiziointerruzione; }
            set { datainiziointerruzione = VerificaDataLocale(value); }
		}

        DateTime? datafineinterruzione = null;
		[DataField("DATAFINEINTERRUZIONE", Type=DbType.DateTime)]
		public DateTime? DATAFINEINTERRUZIONE
		{
			get { return datafineinterruzione; }
            set { datafineinterruzione = VerificaDataLocale(value); }
		}

		string statoelaborazione=null;
		[DataField("STATOELABORAZIONE",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string STATOELABORAZIONE
		{
			get { return statoelaborazione; }
			set { statoelaborazione = value; }
		}

	}
}