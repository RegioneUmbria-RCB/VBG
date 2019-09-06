
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
    public partial class FoVisuraCampiMgr
    {
		public List<FoVisuraCampi> GetList(string idComune, string software, string contesto)
		{
			var filtro = new FoVisuraCampi
			{
				Idcomune = idComune,
				Software = software,
				Fkidcontesto = contesto,
				OrderBy = "posizione asc"
			};

			return GetList(filtro);
		}
	}
}
				