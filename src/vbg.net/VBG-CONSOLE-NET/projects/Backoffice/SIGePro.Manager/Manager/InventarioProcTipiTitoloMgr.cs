
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
    public partial class InventarioProcTipiTitoloMgr
    {
		public List<InventarioProcTipiTitolo> GetTipiTitoloDaCodiceInventario(string idComune , int codiceInventario)
		{
			var filtro = new InventarioProcTipiTitolo
			{
				Idcomune = idComune,
				Codiceinventario = codiceInventario,
				OrderBy = "TipoTitolo asc"
			};

			filtro.OthersWhereClause.Add("(flg_non_pubblicare = 0 or flg_non_pubblicare is null)");

			return GetList(filtro);
		}
	}
}
				