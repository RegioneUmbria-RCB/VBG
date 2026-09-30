using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public class DettagliAnagraficaControl : System.Web.UI.UserControl
    {
        protected void SetVs(string key, object val)
        {
            this.ViewState[key] = val;
        }

        protected bool GetVs(string key, bool defaultVal)
        {
            object o = this.ViewState[key];

            return o == null ? defaultVal : (bool)o;
        }

        protected string GetVs(string key, string defaultVal)
        {
            object o = this.ViewState[key];

            return o == null ? defaultVal : o.ToString();
        }

        protected string Label(string etichetta, bool obbligatorio = false)
        {
            return "<label>" + etichetta + (obbligatorio ? "<sup> *</sup>" : "") + "</label>";
        }

        protected string ClasseCampo(bool obbligatorio)
        {
            return obbligatorio ? "form-show" : "form-hide";
        }
    }
}