using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager
{
	public partial class CCICalcoloDContributoMgr
	{
		public CCICalcoloDContributo GetByIdTContributo(string idComune, int idTContributo)
		{
			CCICalcoloDContributo filtro = new CCICalcoloDContributo();
			filtro.Idcomune = idComune;
			filtro.FkCcictcId = idTContributo;

			return (CCICalcoloDContributo)db.GetClass(filtro);
		}
	}
}
