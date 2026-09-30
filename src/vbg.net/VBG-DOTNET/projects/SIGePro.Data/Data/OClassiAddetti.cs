using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
	public partial class OClassiAddetti
	{
		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}
	}
}
