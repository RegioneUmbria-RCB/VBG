using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
	public partial class CCICalcoloTot
	{
		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}

		CCICalcoloTContributo m_statoDiProgetto = null;

		public CCICalcoloTContributo StatoDiProgetto
		{
			get { return m_statoDiProgetto; }
			set { m_statoDiProgetto = value; }
		}

		CCICalcoloTContributo m_statoAttuale = null;

		public CCICalcoloTContributo StatoAttuale
		{
			get { return m_statoAttuale; }
			set { m_statoAttuale = value; }
		}
	}
}
