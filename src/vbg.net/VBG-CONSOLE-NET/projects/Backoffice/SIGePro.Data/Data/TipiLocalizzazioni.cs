using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
	[DataTable("TIPI_LOCALIZZAZIONI")]
	[Serializable]
	public class TipiLocalizzazioni : BaseDataClass
	{
		[KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
		public string IdComune { get; set; }

		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		public int? Id { get; set; }

		[KeyField("IDCOMUNE", Type = DbType.String, Size = 50)]
		public string Descrizione { get; set; }
	}
}
