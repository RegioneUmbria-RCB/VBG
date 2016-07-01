
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class IAttivitaDyn2ModelliTStoricoMgr
    {
		public int ContaRigheStorico(string idComune, int idModello, int CodiceAttivita)
		{
			return GetList(new IAttivitaDyn2ModelliTStorico
			{
				Idcomune = idComune,
				FkIaId = CodiceAttivita,
				FkD2mtId = idModello
			}).Count;
		}
	}
}
				