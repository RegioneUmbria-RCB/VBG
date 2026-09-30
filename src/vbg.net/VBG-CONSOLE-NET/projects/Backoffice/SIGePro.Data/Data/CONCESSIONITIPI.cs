using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("CONCESSIONITIPI")]
	public class ConcessioniTipi : BaseDataClass
	{
		string tipoconcessione=null;
		[KeyField("TIPOCONCESSIONE",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string TIPOCONCESSIONE
		{
			get { return tipoconcessione; }
			set { tipoconcessione = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string flag_stagionale=null;
		[DataField("FLAG_STAGIONALE", Type=DbType.Decimal)]
		public string FLAG_STAGIONALE
		{
			get { return flag_stagionale; }
			set { flag_stagionale = value; }
		}

	}
}