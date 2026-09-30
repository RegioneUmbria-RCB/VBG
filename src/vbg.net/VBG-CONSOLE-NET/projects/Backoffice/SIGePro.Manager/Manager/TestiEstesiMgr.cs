
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
    public partial class TestiEstesiMgr
    {
		public List<TestiEstesi> GetByCodiceInventario(string idComune, int codiceInventario)
		{
			var filtro = new TestiEstesi
			{
				Idcomune = idComune,
				Codiceinventario = codiceInventario,
				OrderBy = "normativa asc"

			};

			return GetList(filtro);
		}
	}
}
				