using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
	public partial class OCCBaseDestinazioni
	{

		[KeyField("ID", Type = DbType.String,  Size = 1)]
		[useSequence]
		public string Id
		{
			get { return m_id; }
			set { m_id = value; }
		}

		public override string ToString()
		{
			return this.Destinazione;
		}
	}
}
