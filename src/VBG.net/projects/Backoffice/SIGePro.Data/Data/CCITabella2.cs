using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
	public partial class CCITabella2
	{
		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}


		CCDettagliSuperficie m_dettagliSuperficie;
		[ForeignKey(/*typeof(CCDettagliSuperficie),*/ "Idcomune,FkCcdsId", "Idcomune, Id")]
		public CCDettagliSuperficie DettagliSuperficie
		{
			get { return m_dettagliSuperficie; }
			set { m_dettagliSuperficie = value; }
		}

	}
}
