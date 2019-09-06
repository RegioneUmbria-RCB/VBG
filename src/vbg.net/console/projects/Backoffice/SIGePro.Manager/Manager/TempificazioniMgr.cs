
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
    public partial class TempificazioniMgr
    {
		public Tempificazioni GetById(string idcomune, int codicetempificazione)
		{
			Tempificazioni c = new Tempificazioni();


			c.Codicetempificazione = codicetempificazione;
			c.Idcomune = idcomune;

			return (Tempificazioni)db.GetClass(c);
		}
	}
}
				