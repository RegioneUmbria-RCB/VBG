
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class DomandeFrontEndo
    {
		InventarioProcedimenti m_endoprocedimento;
		[ForeignKey("Idcomune, Codiceinventario", "Idcomune, Codiceinventario")]
		public InventarioProcedimenti Endoprocedimento
		{
			get { return m_endoprocedimento; }
			set { m_endoprocedimento = value; }
		}

	}
}
				