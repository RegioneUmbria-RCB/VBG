
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
    public partial class LayoutTestiBaseMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public List<LayoutTestiBase> Find(string testo, string software)
        {
            LayoutTestiBase tb = new LayoutTestiBase();

            tb.Software = software;

            if (!String.IsNullOrEmpty(testo))
            {
                tb.OthersWhereClause.Add("(upper(CODICETESTO) like '%" + testo.ToUpper() + "%' or upper(TESTO) like '%" + testo.ToUpper() + "%')");
            }

            tb.OrderBy = "CODICETESTO,TESTO";

            return GetList(tb);
        }

        public LayoutTestiBase GetByCodice(string codice, string software)
        {
            LayoutTestiBase l = GetById(codice, software);
            if (l == null && software != "TT")
                l = GetById(codice, "TT");

            return l;
        }

        public string GetValoreTesto(string codice, string idComune, string software)
        {
            LayoutTestiMgr mgr = new LayoutTestiMgr(db);
            LayoutTesti l = mgr.GetByCodice(codice, idComune, software);

            if (l != null)
                return l.Nuovotesto;
            else
            {
                LayoutTestiBase lb = GetByCodice(codice, software);

                if (lb != null)
                    return lb.Testo;
            }

            return String.Empty;
        }
	}
}
				