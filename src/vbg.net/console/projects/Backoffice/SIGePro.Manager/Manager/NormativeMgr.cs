
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
    public partial class NormativeMgr
    {
		public Normative GetById(string idcomune, int codicenormativa)
		{
			Normative c = new Normative();


			c.Codicenormativa = codicenormativa;
			c.Idcomune = idcomune;

			return (Normative)db.GetClass(c);
		}
	}
}
				