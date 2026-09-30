
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
    public partial class LayoutTestiMgr
    {
        public LayoutTesti GetByCodice(string codiceTesto, string idComune, string software)
        {
            LayoutTestiMgr mgr = new LayoutTestiMgr(db);
            LayoutTesti c = mgr.GetById(idComune, software, codiceTesto);

            if (c == null && software != "TT")
                c = mgr.GetById(idComune, "TT", codiceTesto);

            return c;
        }
 
	}
}
				