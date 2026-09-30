using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
    public class ContextAliasSoftwareSetter
    {
        public static void Set(HttpContext ctxt, string alias, string software = "")
        {
            ctxt.Items["IdComune"] = alias;

            ctxt.Items["Software"] = software ?? "";
        }
    }
}
