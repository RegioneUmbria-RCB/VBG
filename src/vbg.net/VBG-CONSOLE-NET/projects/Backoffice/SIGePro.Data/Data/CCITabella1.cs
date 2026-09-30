using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Data;

namespace Init.SIGePro.Data
{
	public partial class CCITabella1
	{
		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}

		CCClassiSuperfici m_classiSuperfici;
		[ForeignKey(/*typeof(CCClassiSuperfici),*/ "Idcomune,FkCccsId", "Idcomune,Id")]
		public CCClassiSuperfici ClassiSuperfici
		{
			get { return m_classiSuperfici; }
			set { m_classiSuperfici = value; }
		}

	}
}
