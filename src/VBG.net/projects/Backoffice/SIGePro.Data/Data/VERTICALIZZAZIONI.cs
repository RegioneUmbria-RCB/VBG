using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("VERTICALIZZAZIONI")]
	public class Verticalizzazioni : BaseDataClass
	{
        [KeyField("IDCOMUNE", Size = 10, Type = DbType.String, CaseSensitive = true)]
        public string IdComune { get; set; }

        [KeyField("SOFTWARE", Size = 2, Type = DbType.String, CaseSensitive = true)]
        public string Software { get; set; }

        [KeyField("MODULO", Size = 30, Type = DbType.String, CaseSensitive = true)]
        public string Modulo { get; set; }

        [DataField("ATTIVO", Type = DbType.Decimal)]
        public string Attivo { get; set; }

        [KeyField("CODICECOMUNE", Size = 5, Type = DbType.String, CaseSensitive = true)]
        public string CodiceComune { get; set; }
	}
}