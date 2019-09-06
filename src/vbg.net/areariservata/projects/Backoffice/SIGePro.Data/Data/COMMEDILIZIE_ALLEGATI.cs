using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("COMMEDILIZIE_ALLEGATI")]
	public class CommEdilizie_Allegati : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicecommissione=null;
		[KeyField("CODICECOMMISSIONE", Type=DbType.Decimal)]
		public string CODICECOMMISSIONE
		{
			get { return codicecommissione; }
			set { codicecommissione = value; }
		}

		string idallegato=null;
		[KeyField("IDALLEGATO", Type=DbType.Decimal)]
		public string IDALLEGATO
		{
			get { return idallegato; }
			set { idallegato = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string note=null;
		[DataField("NOTE",Size=500, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

        DateTime? dataregistrazione = null;
		[DataField("DATAREGISTRAZIONE", Type=DbType.DateTime)]
		public DateTime? DATAREGISTRAZIONE
		{
			get { return dataregistrazione; }
            set { dataregistrazione = VerificaDataLocale(value); }
		}

	}
}