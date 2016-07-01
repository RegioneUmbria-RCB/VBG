
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
using PersonalLib2.Sql.Collections;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CittadinanzaMgr
    {
		public Cittadinanza GetByClass(Cittadinanza cls)
		{
			return (Cittadinanza)db.GetClass(cls);
		}
	}
}
				