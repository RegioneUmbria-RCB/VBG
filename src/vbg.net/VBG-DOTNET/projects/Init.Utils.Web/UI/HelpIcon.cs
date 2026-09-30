using System;
using System.Web.UI;
using System.Web.UI.WebControls;

[assembly: System.Web.UI.WebResource("Init.Utils.Web.UI.HelpIconBehavior.js", "text/javascript")]

namespace Init.Utils.Web.UI
{
    [ToolboxData("<{0}:HelpIcon runat=\"server\"/>")]
    public partial class HelpIcon : Image
    {
        [IDReferenceProperty(typeof(HelpDiv))]
        public string HelpControl
        {
            get { object o = this.ViewState["HelpControl"]; return o == null ? String.Empty : (string)o; }
            set
            {
                this.ViewState["HelpControl"] = value;
                this.Visible = !String.IsNullOrEmpty(this.HelpControl);
            }
        }

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            if (!this.Page.ClientScript.IsClientScriptIncludeRegistered(this.GetType(), "HelpIconBehavior"))
            {
                this.Page.ClientScript.RegisterClientScriptInclude(
                   this.GetType(), "HelpIconBehavior",
                   this.Page.ClientScript.GetWebResourceUrl(this.GetType(),
                   "Init.Utils.Web.UI.HelpIconBehavior.js"));
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            if (this.Visible)
            {
                HelpDiv hd = null;

                if (this.NamingContainer != null)
                    hd = (HelpDiv)this.NamingContainer.FindControl(this.HelpControl);

                if (hd == null && this.NamingContainer.NamingContainer != null)
                    hd = (HelpDiv)this.NamingContainer.NamingContainer.FindControl(this.HelpControl);

                if (hd == null)
                    hd = (HelpDiv)this.Page.FindControl(this.HelpControl);

                if (hd == null)
                {
                    this.Visible = false;
                }
                else
                {
                    string fmtScript = "var {0} = new HelpIconBehavior(document.getElementById('{0}'),document.getElementById('{1}'));{0}.Initialize();";

                    string script = String.Format(fmtScript, this.ClientID, hd.ClientID);

                    var sm = ScriptManager.GetCurrent(this.Page);

                    if (sm == null)
                        this.Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID, script, true);
                    else
                        ScriptManager.RegisterStartupScript(this, this.GetType(), this.ClientID, script, true);

                    this.ImageUrl = hd.HelpIcon;
                }

                base.OnPreRender(e);
            }
        }

    }
}
